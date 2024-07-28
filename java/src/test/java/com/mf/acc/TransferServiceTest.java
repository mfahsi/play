package com.mf.acc;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferServiceTest {

    TransferService transferService = new TransferService();

    @Test
    public void testTransferSuccessful() {
        Account bob = new Account("Bob", BigDecimal.valueOf(100));
        Account alice = new Account("Alice", BigDecimal.valueOf(200));

        boolean result = transferService.transfer(alice, bob, BigDecimal.valueOf(100));

        assertEquals(true, result, "Transfer should be successful");
        assertEquals(BigDecimal.valueOf(100), alice.balance.getReference(), "Alice's balance should be 100");
        assertEquals(BigDecimal.valueOf(200), bob.balance.getReference(), "Bob's balance should be 200");
    }

    @Test
    public void testTransferInsufficientFunds() {
        Account bob = new Account("Bob", BigDecimal.valueOf(100));
        Account alice = new Account("Alice", BigDecimal.valueOf(50));

        boolean result = transferService.transfer(alice, bob, BigDecimal.valueOf(100));

        assertEquals(false, result, "Transfer should fail due to insufficient funds");
        assertEquals(BigDecimal.valueOf(50), alice.balance.getReference(), "Alice's balance should remain 50");
        assertEquals(BigDecimal.valueOf(100), bob.balance.getReference(), "Bob's balance should remain 100");
    }

    @Test
    public void testTransferZeroAmount() {
        Account bob = new Account("Bob", BigDecimal.valueOf(100));
        Account alice = new Account("Alice", BigDecimal.valueOf(200));

        boolean result = transferService.transfer(alice, bob, BigDecimal.ZERO);

        assertEquals(true, result, "Transfer of zero amount should be successful");
        assertEquals(BigDecimal.valueOf(200), alice.balance.getReference(), "Alice's balance should remain 200");
        assertEquals(BigDecimal.valueOf(100), bob.balance.getReference(), "Bob's balance should remain 100");
    }
}
