package com.mf.acc;

import scala.util.Either;
import scala.util.Left;
import scala.util.Try;

import java.math.BigDecimal;

public class TransferService() {

    public Either<String,Boolean> transfer(Account from, Account to, BigDecimal amount){
        return new Left<String,Boolean> ("not implemented");
    }
}

