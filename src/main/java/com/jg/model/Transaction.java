package com.jg.model;

/**
 * Created by Gabor on 2019.03.30..
 */
public class Transaction {
    private String accountNo;
    private String currency;
    private Double amount;
    private Double rate;

    public Transaction(String accountNo, String currency, Double amount, Double rate) {
        this.accountNo = accountNo;
        this.currency = currency;
        this.amount = amount;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getRate() {
        return rate;
    }

    public String getAccountNo() {
        return accountNo;
    }

    @Override
    public String toString() {
        return "Transaction; AccountNo:" + accountNo +
                " currency:" + currency +
                " amount:" + amount +
                " rate:" + rate;
    }
}
