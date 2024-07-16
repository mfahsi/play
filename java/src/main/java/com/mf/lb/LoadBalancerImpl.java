package com.mf.lb;

import scala.NotImplementedError;

import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class LoadBalancerImpl implements LoadBalancer {
    private final Set<String> servers = new ConcurrentSkipListSet<>();

    private BalancingStrategy strategy = new RoundRobin(servers.stream().toList());

    HttpResponse handle(HttpRequest request, String target){
        switch (target){
            case null ->{  return null;}//505}
            default -> {
            //process
                throw new RuntimeException("Unimplemented target: " + target);
            }
        }
    }

    @Override
    public HttpResponse process(HttpRequest request) throws Exception {
        Optional<String> node = strategy.choseNode(request);
        switch (node){
            case Optional<String> e when e.isEmpty() ->  {return handle(request,null);}
            default -> {return handle(request,node.get());}
        }
    }
}
