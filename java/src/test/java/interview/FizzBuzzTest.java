package interview;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FizzBuzzTest {
    static FizzBuzz fb = FizzBuzz.createFizzBuzz();

    @Test
    public final void test_predicates() {
        assertEquals(FizzBuzz.transform(7), FizzBuzz.FB.Other);
        assertEquals(FizzBuzz.transform(10), FizzBuzz.FB.Buzz);
        assertEquals(FizzBuzz.transform(15), FizzBuzz.FB.FizzBuzz);
        assertEquals(FizzBuzz.transform(9), FizzBuzz.FB.Fizz);
    }
}
