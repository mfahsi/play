package interview;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class FizzBuzz {

    static Predicate<Integer> fizzBuzz = i -> i % 15 == 0;
    static Predicate<Integer> fizz = Predicate.not(fizzBuzz).and(i -> i % 3 == 0);
    static Predicate<Integer> buzz = Predicate.not(fizzBuzz).and(i -> i % 5 == 0);

    private FizzBuzz() {
    }

    public static void main(String[] args) {
        LinkedBlockingDeque<FB> work = new LinkedBlockingDeque<>();
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
           var producer = scope.fork(()-> IntStream.range(1,25).map(i-> transform(i)).forEach(v->work.add(v)));
           producer.fork(()-> fizzDisp(work) )
           producer.fork(()-> fizzBuzzDisp(work) )
           producer.fork(()-> buzzDisp(work) )
          // producer.fork(()-> fizzDisp(work) )
            scope.join()
        }

    }

    public static FizzBuzz createFizzBuzz() {
        return new FizzBuzz();
    }

    public static FB transform(int a) {
        switch (a) {
            case int n when fizzBuzz.test(n) -> {
                var e = FB.FizzBuzz;
                e.value = Optional.of(n);
                return e;
            }
            case int n when fizz.test(n) -> {
                var e = FB.Fizz;
                e.value = Optional.of(n);
                return e;
            }
            case int n when buzz.test(n) -> {
                var e = FB.Buzz;
                e.value = Optional.of(n);
                return e;
            }
            case int n -> {
                var e = FB.Other;
                e.value = Optional.of(n);
                return e;
            }
        }
    }

    static public void fizzDisp(java.util.concurrent.LinkedBlockingDeque<FB> queue) {
        var f = queue.peek();
        switch (f) {
            case FB.Fizz -> {
                System.out.println("Fizz");
                queue.poll();
            }
            case Integer i -> {
            }
        }
    }

    static public void buzzDisp(java.util.concurrent.LinkedBlockingDeque<FB> queue) {
        var f = queue.peek();
        switch (f) {
            case FB.Buzz -> {
                System.out.println("Buzz");
                queue.poll();
            }
            case Integer i -> {
            }
        }
    }

   static public void fizzBuzzDisp(java.util.concurrent.LinkedBlockingDeque<FB> queue) {
        var f = queue.peek();
        switch (f) {
            case FB.Fizz -> System.out.println("FizzBuzz");
            case Integer i -> {
            }
        }
    }

    static enum FB {
        Fizz, Buzz, FizzBuzz, Other;
        public volatile Optional<Integer> value;

        FB() {
            this.value = Optional.empty();
        }
    }
}




