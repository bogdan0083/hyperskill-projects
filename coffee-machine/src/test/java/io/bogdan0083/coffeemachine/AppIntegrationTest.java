package io.bogdan0083.coffeemachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class AppIntegrationTest {
    private ByteArrayOutputStream byteArrayOutputStream;

    @BeforeEach
    public void setup() {
        byteArrayOutputStream = new ByteArrayOutputStream();
    }

    /*
     * Run our coffee machine app with certain input
     * to test that all the dialogs are displayed correctly
     */
    private void runApp(String input, Consumer<String> func) {
        InputStream oldSystemIn = System.in;
        PrintStream oldSystemOut = System.out;
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
            System.setOut(new PrintStream(byteArrayOutputStream));
            System.setIn(inputStream);
            App.main(new String[0]);
            func.accept(byteArrayOutputStream.toString());
        } finally {
            System.setOut(oldSystemOut);
            System.setIn(oldSystemIn);
        }
    }

    @Test
    @DisplayName("should display purchase dialog")
    void testCoffeePurchaseDialog() {
        runApp("buy 1 exit", (appOutput) -> {
            assertThat(appOutput).contains("What do you want to buy?");
            assertThat(appOutput).contains("making you a coffee");
        });
    }

    @Test
    @DisplayName("should display remaining consumables")
    void testDisplayRemaining() {
        runApp("remaining exit", (appOutput) -> {
            assertThat(appOutput).contains("of water");
            assertThat(appOutput).contains("of milk");
            assertThat(appOutput).contains("of coffee beans");
        });
    }

    @Test
    @DisplayName("should fill coffee machine")
    void testFillingCoffeeMachine() {
        runApp("take fill 1000 1000 1000 1000 remaining exit", (appOutput) -> {
            assertThat(appOutput).containsPattern(Pattern.compile("\\d{4} of water"));
            assertThat(appOutput).containsPattern(Pattern.compile("\\d{4} of milk"));
            assertThat(appOutput).containsPattern(Pattern.compile("\\d{4} of coffee beans"));
        });
    }

    @Test
    @DisplayName("should take all money")
    void testTakingAllMoney() {
        runApp("take remaining exit", (appOutput) -> assertThat(appOutput).contains("$0 of money"));
    }
}
