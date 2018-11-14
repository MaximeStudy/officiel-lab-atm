package ca.ulaval.glo4002.atm.domain.accounts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ca.ulaval.glo4002.atm.domain.accounts.transactions.Receipt;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "accountNumber")
    private int accountNumber;

    @Column
    protected double balance;

    protected Account() {
        // for hibernate
    }

    public Account(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public abstract Receipt debit(double amount);

    public int getAccountNumber() {
        return accountNumber;
    }
}
