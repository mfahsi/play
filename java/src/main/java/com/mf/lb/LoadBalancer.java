package com.mf.lb;

import com.sun.net.httpserver.HttpServer;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public interface LoadBalancer {
    HttpResponse process(HttpRequest request) throws Exception;

    default Optional<String> choseNode(HttpRequest request, BalancingStrategy algo) {
        var node = algo.choseNode(request);
        return node;
    }
}
