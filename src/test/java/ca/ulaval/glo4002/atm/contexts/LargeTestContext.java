package ca.ulaval.glo4002.atm.contexts;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ca.ulaval.glo4002.atm.application.ServiceLocator;
import ca.ulaval.glo4002.atm.application.banking.BankingService;
import ca.ulaval.glo4002.atm.application.banking.ReceiptAssembler;
import ca.ulaval.glo4002.atm.application.jpa.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.atm.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.atm.domain.accounts.AccountRepository;
import ca.ulaval.glo4002.atm.domain.accounts.StandardAccount;
import ca.ulaval.glo4002.atm.domain.dispensers.CashDispenser;
import ca.ulaval.glo4002.atm.infrastructure.dispensers.FakeCashDispenser;
import ca.ulaval.glo4002.atm.infrastructure.persistence.HibernateAccountRepository;

public class LargeTestContext implements Context {

    @Override
    public void apply() {
        ServiceLocator.registerSingleton(ReceiptAssembler.class, new ReceiptAssembler());
        ServiceLocator.registerSingleton(AccountRepository.class, new HibernateAccountRepository());
        ServiceLocator.registerSingleton(CashDispenser.class, new FakeCashDispenser());
        ServiceLocator.registerSingleton(BankingService.class, new BankingService());

        fillDatabase();
    }

    private void fillDatabase() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManagerProvider.setEntityManager(entityManager);

        AccountRepository accountRepository = new HibernateAccountRepository();

        entityManager.getTransaction().begin();
        accountRepository.persist(new StandardAccount(123, 1000));
        accountRepository.persist(new StandardAccount(456, 1));
        entityManager.getTransaction().commit();

        EntityManagerProvider.clearEntityManager();
        entityManager.close();
    }
}