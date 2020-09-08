package io.bogdan0083.encryptdecrypt;

import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    /**
     * Run app with the arguments
     * @param arguments to pass
     * @param func lambda function to trigger after the app is run.
     *             Sent output from application is used as lambda parameter
     */
    private void runApp(String[] arguments, Consumer<String> func) {
        PrintStream oldSystemOut = System.out;
        try {
            System.setOut(new PrintStream(byteArrayOutputStream));
            App.main(arguments);
            func.accept(byteArrayOutputStream.toString());
        } finally {
            System.setOut(oldSystemOut);
        }
    }

    @DisplayName("should not encode with shift cipher if key is not passed")
    @Test
    void testShiftCipherEncodeWithKeyNotPassed() {
        String rawData = "!@ abcdiefghijklmnopqrstuvwxyzABCDIFGHIJKLMNOPQRSTUVWXYZ #!";
        String[] args = new String[]{"-alg", "shift", "-mode", "enc", "-data", rawData};
        runApp(args, (output) -> assertThat(output).isEqualTo(rawData));
    }

    @DisplayName("should encode with shift cipher and key 2")
    @Test
    void testShiftCipherEncodeWithKey2() {
        String rawData = "!@ abcdiefghijklmnopqrstuvwxyzABCDIFGHIJKLMNOPQRSTUVWXYZ #!";
        String expected = "!@ cdefkghijklmnopqrstuvwxyzabCDEFKHIJKLMNOPQRSTUVWXYZAB #!";
        String[] args = new String[]{"-alg", "shift", "-mode", "enc", "-data", rawData, "-key", "2"};
        runApp(args, (output) -> assertThat(output).isEqualTo(expected));
    }

    @DisplayName("should encode with shift cipher and key 5")
    @Test
    void testShiftCipherEncodeWithKey5() {
        String rawData = "!@ abcdiefghijklmnopqrstuvwxyzABCDIFGHIJKLMNOPQRSTUVWXYZ #!";
        String expected = "!@ fghinjklmnopqrstuvwxyzabcdeFGHINKLMNOPQRSTUVWXYZABCDE #!";
        String[] args = new String[]{"-alg", "shift", "-mode", "enc", "-data", rawData, "-key", "5"};
        runApp(args, (output) -> assertThat(output).isEqualTo(expected));
    }

    @DisplayName("should not decode with shift cipher if key is not passed")
    @Test
    void testShiftCipherDecodeWithKeyNotPassed() {
        String rawData = "!@ cdefkghijklmnopqrstuvwxyzabCDEFKHIJKLMNOPQRSTUVWXYZAB #!";
        String[] args = new String[]{"-alg", "shift", "-mode", "dec", "-data", rawData};
        runApp(args, (output) -> assertThat(output).isEqualTo(rawData));
    }


    @DisplayName("should decode with shift cipher and key 2")
    @Test
    void testShiftCipherDecodeWithKey2() {
        String encoded = "!@ cdefkghijklmnopqrstuvwxyzabCDEFKHIJKLMNOPQRSTUVWXYZAB #!";
        String expected = "!@ abcdiefghijklmnopqrstuvwxyzABCDIFGHIJKLMNOPQRSTUVWXYZ #!";
        String[] args = new String[]{"-alg", "shift", "-mode", "dec", "-data", encoded, "-key", "2"};
        runApp(args, (output) -> assertThat(output).isEqualTo(expected));
    }

    @DisplayName("should decode with shift cipher and key 5")
    @Test
    void testShiftCipherDecodeWithKey5() {
        String encoded = "!@ fghinjklmnopqrstuvwxyzabcdeFGHINKLMNOPQRSTUVWXYZABCDE #!";
        String expected = "!@ abcdiefghijklmnopqrstuvwxyzABCDIFGHIJKLMNOPQRSTUVWXYZ #!";
        String[] args = new String[]{"-alg", "shift", "-mode", "dec", "-data", encoded, "-key", "5"};
        runApp(args, (output) -> assertThat(output).isEqualTo(expected));
    }
}