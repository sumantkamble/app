package de.umzugsauktion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    @BeforeEach
    public void setUpOutput() {
        var testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private void provideInput(String input) {
        var testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
    }

    @Test
    @DisplayName("Valid User Inputs :: Result Size")
    void validUserInputResultSizeTest() {
        var input = "1" + System.lineSeparator() + "2";
        provideInput(input);
        var result = new NumberConverter().start();
        assertEquals(100, result.size());
    }

    @Test
    @DisplayName("Valid User Inputs :: Valid Result")
    void validUserInputValidResultTest() {
        var input = "3" + System.lineSeparator() + "5";
        provideInput(input);
        var result = new NumberConverter().start();
        assertEquals(NumberConverter.UMZUGS, result.get(2));
        assertEquals(NumberConverter.AUKTION, result.get(4));
        assertEquals(NumberConverter.UMZUGS_AUKTION, result.get(14));
    }

    @Test
    @DisplayName("Invalid Input Number1 :: Failed Test")
    void invalidInputNumber1ThrowsExceptionTest() {
        var exception = assertThrows(NoSuchElementException.class, () -> {
            var input = "abc";
            provideInput(input);
            new NumberConverter().start();
        });
        assertEquals("No line found", exception.getMessage());
    }

    @Test
    @DisplayName("Invalid Input Number2 :: Failed Test")
    void invalidInputNumber2ThrowsExceptionTest() {
        var exception = assertThrows(NoSuchElementException.class, () -> {
            var input = "3" + System.lineSeparator() + "abc";
            provideInput(input);
            new NumberConverter().start();
        });
        assertEquals("No line found", exception.getMessage());
    }

    @Test
    @DisplayName("Same Input Number2 :: Failed Test")
    void sameInputNumber2ThrowsExceptionTest() {
        var exception = assertThrows(NoSuchElementException.class, () -> {
            var input = "5" + System.lineSeparator() + "5";
            provideInput(input);
            new NumberConverter().start();
        });
        assertEquals("No line found", exception.getMessage());
    }

}
