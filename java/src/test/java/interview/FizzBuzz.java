package interview;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Predicate;
import java.util.function.Supplier;
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
            System.out.println("StructuredTaskScope ");
            var producer = scope.fork(() -> {
                IntStream.range(1, 25).boxed().map(i -> transform(i)).forEach(v -> {
                    work.add(v);
                });
                work.add(FB.Terminate); // Add Terminate signal after all work is done
                return "";
            });
            Supplier<String> fiz = scope.fork(() -> fizzDisp(work));
            var fizbuzz = scope.fork(() -> fizzBuzzDisp(work));
            var buz = scope.fork(() -> buzzDisp(work));
            var other = scope.fork(()-> otherDisp(work));

            scope.join().throwIfFailed();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public static FizzBuzz createFizzBuzz() {
        return new FizzBuzz();
    }

    public static FB transform(Integer a) {
        System.out.println("transform " + a);
        switch (a) {
            case Integer n when fizzBuzz.test(n) -> {
                var e = FB.FizzBuzz;
                e.value = Optional.of(n);
                return e;
            }
            case Integer n when fizz.test(n) -> {
                var e = FB.Fizz;
                e.value = Optional.of(n);
                return e;
            }
            case Integer n when buzz.test(n) -> {
                var e = FB.Buzz;
                e.value = Optional.of(n);
                return e;
            }
            case Integer n -> {
                var e = FB.Other;
                e.value = Optional.of(n);
                return e;
            }
        }
    }

    static public String fizzDisp(java.util.concurrent.LinkedBlockingDeque<FB> queue) {
       var terminate = false;
       while(! terminate) {

           var f = queue.peek();
           if (f != null) {
               switch (f) {
                   case FB.Fizz -> {
                       System.out.println("Fizz");
                       queue.poll();
                       //return "Fizz";
                   }
                   case FB.Terminate -> {
                       terminate = true;
                       return "Terminate"; // Handle Terminate signal
                   }
               }

           }

       }
        return "";
    }

    static public String buzzDisp(java.util.concurrent.LinkedBlockingDeque<FB> queue) {
        var terminate = false;
        while(! terminate) {
            var f = queue.peek();
            if (f != null) {
                switch (f) {
                    case FB.Buzz -> {
                        System.out.println("Buzz");
                        queue.poll();
                        //return "Buzz";
                    }
                    case FB.Terminate -> {
                        return "Terminate"; // Handle Terminate signal
                    }
                }
            }
        }
        return "";
    }

    static public String fizzBuzzDisp(java.util.concurrent.LinkedBlockingDeque<FB> queue) {
        var terminate = false;
        while(! terminate) {
            var f = queue.peek();
            if (f != null) {
                switch (f) {
                    case FB.FizzBuzz -> {
                        System.out.println("FizzBuzz");
                        queue.poll();
                       // return "FizzBuzz";
                    }
                    case FB.Terminate -> {
                        terminate=true;
                        return "Terminate"; // Handle Terminate signal
                    }
                }
            }
        }
        return "";
    }
    static public String otherDisp(java.util.concurrent.LinkedBlockingDeque<FB> queue) {
        var terminate = false;
        while(! terminate) {
            var f = queue.peek();
            if (f != null) {
                switch (f) {
                    case FB.Other  -> {
                        System.out.println("other");
                        queue.poll();
                       // return "other";
                    }
                    case FB.Terminate -> {
                        terminate = true;
                        return "Terminate"; // Handle Terminate signal
                    }
                }
            }
        }
        return "";
    }

    static enum FB {
        Fizz, Buzz, FizzBuzz, Other, Terminate; // Added Terminate enum
        public volatile Optional<Integer> value;

        FB() {
            this.value = Optional.empty();
        }
    }
}
