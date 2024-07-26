package com.mf.acc;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TransferServiceTest {

    TransferService transferService = new TransferService();

    @Test
    public void testTransfer(){
        Account bob = new Account("Bob",BigDecimal.valueOf(-20));
        Account alice = new Account("Alice",BigDecimal.valueOf(200));
        transferService.transfer(alice,bob,BigDecimal.valueOf(100));
        assertEquals(alice.balance().doubleValue(), 100);
    }

}

