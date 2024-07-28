package com.mf.acc;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicStampedReference;

class Account {
    final String id;
    final AtomicStampedReference<BigDecimal> balance;

    Account(String id, BigDecimal initialBalance) {
        this.id = id;
        this.balance = new AtomicStampedReference<>(initialBalance, 0);
    }

    BigDecimal getBalance(int[] stamp) {
        return balance.get(stamp);
    }

    boolean compareAndSet(BigDecimal expectedBalance, BigDecimal newBalance, int expectedStamp, int newStamp) {
        return balance.compareAndSet(expectedBalance, newBalance, expectedStamp, newStamp);
    }
}

class Transaction {
    private final Account from;
    private final Account to;
    private final BigDecimal amount;
    private final int[] fromStamp;
    private final int[] toStamp;
    private BigDecimal fromInitial;
    private BigDecimal toInitial;

    Transaction(Account from, Account to, BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.fromStamp = new int[1];
        this.toStamp = new int[1];
    }

    boolean prepare() {
        fromInitial = from.getBalance(fromStamp);
        toInitial = to.getBalance(toStamp);

        if (fromInitial.compareTo(amount) < 0) {
            return false; // Insufficient funds
        }

        return true;
    }

    boolean commit() {
        BigDecimal newFromAmount = fromInitial.subtract(amount);
        BigDecimal newToAmount = toInitial.add(amount);

        boolean fromUpdatedSuccessfully = from.compareAndSet(fromInitial, newFromAmount, fromStamp[0], fromStamp[0] + 1);
        boolean toUpdatedSuccessfully = to.compareAndSet(toInitial, newToAmount, toStamp[0], toStamp[0] + 1);

        return fromUpdatedSuccessfully && toUpdatedSuccessfully;
    }
}

class TransferService {

    public static boolean transfer(Account from, Account to, BigDecimal amount) {
        while (true) {
            Transaction transaction = new Transaction(from, to, amount);

            if (!transaction.prepare()) {
                return false; // Insufficient funds
            }

            if (transaction.commit()) {
                return true; // Transfer successful
            }

            // Retry logic: If the commit fails, loop will retry the transaction
        }
    }
}

