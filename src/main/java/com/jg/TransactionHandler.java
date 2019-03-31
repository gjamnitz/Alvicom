package com.jg;

import com.jg.model.Account;
import com.jg.model.Transaction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gabor on 2019.03.30..
 */
public class TransactionHandler {

    private static Map<String, Account> accounts = new HashMap<>();
    private static Map<String, List<Transaction>> transactions = new HashMap<>();
    private static int REPORT_BY_TR = 10;

    // initially filling accounts
    static {
        accounts.put("11111111-22222222", new Account("11111111-22222222", "HUF", 150000));
        accounts.put("22222222-33333333", new Account("22222222-33333333", "USD", 1230));
    }

    // put transaction into transaction list for reports
    private static void recordTransaction(Transaction transaction) {
        if(!transactions.containsKey(transaction.getAccountNo())) {
            List<Transaction> trList = new ArrayList<>();
            trList.add(transaction);
            transactions.put(transaction.getAccountNo(), trList);
        }
        else {
            transactions.get(transaction.getAccountNo()).add(transaction);
        }
    }

    // applying transaction on account
    private static void applyTransaction(Transaction transaction) {
        if(!accounts.containsKey(transaction.getAccountNo())) {
            System.out.println("Warning: non-existing account");
        }
        else {
            Account account = accounts.get(transaction.getAccountNo());
            boolean sameCurrency = account.getCurrency().equals(transaction.getCurrency());
            if(transaction.getRate() == null && !sameCurrency) {
                System.out.println("Warning: rate not found!");
                return;
            }

            account.setBalance(account.getBalance() +
                    (sameCurrency ?
                            transaction.getAmount():
                            (transaction.getAmount() * transaction.getRate())));
        }
    }

    // handling transaction: applying and recording
    private static void handleTransaction(Transaction transaction) {
        if(accounts.containsKey(transaction.getAccountNo())) {
            applyTransaction(transaction);
            recordTransaction(transaction);
        }
    }

    // create Transaction object from CSV string
    private static Transaction createTransaction(String[] values) {
        String accountNo = values[0];
        String currency = values[1];
        Double amount = Double.valueOf(values[2]);
        Double rate = null;
        if(values.length == 4) {
            rate = Double.valueOf(values[3]);
        }
        return new Transaction(accountNo, currency, amount, rate);
    }

    // writes report
    private static void writeReport() {
        for(String accountNo: transactions.keySet()) {
            System.out.println(accounts.get(accountNo));

            for(Transaction transaction: transactions.get(accountNo)) {
                System.out.println(" * " + transaction);
            }
        }
        System.out.println("***************");
    }

    public static void main(String args[]) {
        int transactionNum = 0;

        if(args.length == 0) {
            System.out.println("Usage: TransactionHandler <path-to-file>");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Transaction transaction = createTransaction(values);
                handleTransaction(transaction);
                if(++transactionNum % REPORT_BY_TR == 0) {
                    writeReport();
                }
            }
        }
        catch(FileNotFoundException fne) {
            System.out.println("Error: File not found");
        }
        catch(IOException fne) {
            System.out.println("Error: IO Exception");
        }
    }
}
