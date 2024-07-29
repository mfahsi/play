package com.mf.lb;

import scala.util.Either;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public interface BalancingStrategy {
    Optional<String> choseNode(HttpRequest request);
    Either<String,String> electServer(HttpRequest request);
}
