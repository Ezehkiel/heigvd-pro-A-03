package ch.heigvd.pro.a03;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HelloWorldTest {

    @Test
    void itShouldGreetSomeone() {

        String name = "Jack";

        String greetings = HelloWorld.greet(name);

        assertEquals("Hello, Jack!", greetings);
    }
}