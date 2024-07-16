package com.mf.lb;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class RoundRobin implements BalancingStrategy {
    private final CopyOnWriteArraySet<String> nodes = new CopyOnWriteArraySet<String>();
    private final AtomicInteger current = new AtomicInteger(-1);

    public RoundRobin(List<String> servers) {
        assert servers != null && !servers.isEmpty();
        nodes.addAll(servers);
    }

    @Override
    public Optional<String> choseNode(HttpRequest request) {
        String[] arr = nodes.stream().toArray(String[]::new);
        if(arr.length == 0) {
            return Optional.empty();
        }
      return  Optional.of(arr[(current.incrementAndGet() % arr.length )]);
    }
}
