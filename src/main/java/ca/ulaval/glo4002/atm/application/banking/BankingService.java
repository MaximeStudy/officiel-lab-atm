package ca.ulaval.glo4002.atm.application.banking;

import ca.ulaval.glo4002.atm.application.ServiceLocator;
import ca.ulaval.glo4002.atm.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.atm.domain.accounts.Account;
import ca.ulaval.glo4002.atm.domain.accounts.AccountRepository;
import ca.ulaval.glo4002.atm.domain.accounts.transactions.Receipt;
import ca.ulaval.glo4002.atm.domain.accounts.transactions.TransactionAbortedException;
import ca.ulaval.glo4002.atm.domain.dispensers.CashDispenser;

public class BankingService {

    private AccountRepository accountRepository;
    private CashDispenser cashDispenser;
    private ReceiptAssembler receiptAssembler;
    private EntityManagerProvider entityManagerProvider;

    public BankingService() {
        this.accountRepository = ServiceLocator.resolve(AccountRepository.class);
        this.cashDispenser = ServiceLocator.resolve(CashDispenser.class);
        this.receiptAssembler = ServiceLocator.resolve(ReceiptAssembler.class);
        this.entityManagerProvider = new EntityManagerProvider();
    }

    public BankingService(AccountRepository accountRepository, CashDispenser cashDispenser,
            ReceiptAssembler receiptAssembler, EntityManagerProvider provider) {
        this.accountRepository = accountRepository;
        this.cashDispenser = cashDispenser;
        this.receiptAssembler = receiptAssembler;
        this.entityManagerProvider = provider;
    }

    public ReceiptDto withdrawMoney(int accountNumber, WithdrawalRequest withdrawalRequest) {
        if (!cashDispenser.canAccomodateWithdrawal(withdrawalRequest.amount)) {
            throw new TransactionAbortedException();
        }

        Receipt receipt = executeTransaction(accountNumber, withdrawalRequest);

        if (receipt.isAccepted()) {
            cashDispenser.dispense(withdrawalRequest.amount);
        }

        return receiptAssembler.toDto(receipt);
    }

    private Receipt executeTransaction(int accountNumber, WithdrawalRequest withdrawalRequest) {
        Account account = accountRepository.findByNumber(accountNumber);
        Receipt receipt = account.debit(withdrawalRequest.amount);

        entityManagerProvider.executeInTransaction(() -> accountRepository.persist(account));

        return receipt;
    }
}
