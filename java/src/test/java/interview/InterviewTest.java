package interview;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

record Hello(String name){}

class InterviewTest {
    static Hello hello = new Hello("Peter");

    @Test
    public final void test_hello() {
        assertEquals( "Hello[name=Peter]",hello.toString());
    }
}
