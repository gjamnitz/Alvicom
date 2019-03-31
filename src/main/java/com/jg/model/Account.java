package com.jg.model;

/**
 * Created by Gabor on 2019.03.30..
 */
public class Account {
    private String accountNo;
    private String currency;
    private double balance;

    public Account(String accountNo, String currency, double balance) {
        this.accountNo = accountNo;
        this.currency = currency;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account; No:" + accountNo +
               " currency:" + currency +
               " balance:" + balance;
    }
}
