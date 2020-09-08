package io.bogdan0083.coffeemachine;

import io.bogdan0083.coffeemachine.coffee.Cappuccino;
import io.bogdan0083.coffeemachine.coffee.Espresso;
import io.bogdan0083.coffeemachine.coffee.Latte;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeMachineTest {

    private final CoffeeMachine coffeeMachine = new CoffeeMachine.Builder().build();

    @Test
    @DisplayName("should work with default values")
    void testWithDefaultValues() {
        assertDoesNotThrow(() -> coffeeMachine.buy(new Espresso()));
    }

    @Test
    @DisplayName("should buy a coffee")
    void testPurchase() {
        CoffeeMachineState stateBeforePurchase = assertDoesNotThrow(() -> (CoffeeMachineState) coffeeMachine.getState().clone());
        assertDoesNotThrow(() -> coffeeMachine.buy(new Espresso()));
        CoffeeMachineState stateAfterPurchase = coffeeMachine.getState();
        assertNotEquals(stateBeforePurchase, stateAfterPurchase);
    }

    @Test
    @DisplayName("should throw when run of consumables")
    void testThrowOnPurchase() {
        assertDoesNotThrow(() -> coffeeMachine.buy(new Espresso()));
        CoffeeMachineState stateBeforeSecondPurchase = assertDoesNotThrow(() -> (CoffeeMachineState) coffeeMachine.getState().clone());
        assertThrows(CoffeePurchaseException.class, () -> coffeeMachine.buy(new Latte()));
        CoffeeMachineState stateAfterSecondPurchase = coffeeMachine.getState();
        assertEquals(stateBeforeSecondPurchase, stateAfterSecondPurchase);
        assertEquals(stateAfterSecondPurchase.getWater(), stateBeforeSecondPurchase.getWater());
    }

    @Test
    @DisplayName("should fill")
    void testFilling() {
        CoffeeConsumables coffeeConsumables = TestUtils.getCoffeeConsumables();
        assertDoesNotThrow(() -> coffeeMachine.buy(new Espresso()));
        assertThrows(CoffeePurchaseException.class, () -> coffeeMachine.buy(new Latte()));

        CoffeeMachineState stateBeforeFilling = assertDoesNotThrow(() -> (CoffeeMachineState) coffeeMachine.getState().clone());
        coffeeMachine.fill(coffeeConsumables);
        CoffeeMachineState stateAfterFilling = coffeeMachine.getState();

        assertNotEquals(stateBeforeFilling, stateAfterFilling);
        assertNotEquals(stateBeforeFilling.getWater(), stateAfterFilling.getWater());
        assertTrue(stateBeforeFilling.getWater() < stateAfterFilling.getWater());
        assertEquals(stateBeforeFilling.getWater() + coffeeConsumables.water, stateAfterFilling.getWater());
    }

    @Test
    @DisplayName("should buy one more cup of coffee when filled")
    void testBuyCupAfterFilling() {
        CoffeeConsumables coffeeConsumables = TestUtils.getCoffeeConsumables();

        assertDoesNotThrow(() -> coffeeMachine.buy(new Espresso()));
        assertThrows(CoffeePurchaseException.class, () -> coffeeMachine.buy(new Latte()));

        coffeeMachine.fill(coffeeConsumables);

        assertDoesNotThrow(() -> coffeeMachine.buy(new Cappuccino()));
    }

    @Test
    @DisplayName("should buy lots of coffee when filled a lot of consumables")
    void testBuyLotsOfCupsAfterFilling() {
        CoffeeConsumables coffeeHugeConsumables = TestUtils.getHugeCoffeeConsumables();

        assertDoesNotThrow(() -> coffeeMachine.buy(new Espresso()));
        assertThrows(CoffeePurchaseException.class, () -> coffeeMachine.buy(new Espresso()));

        coffeeMachine.fill(coffeeHugeConsumables);

        assertDoesNotThrow(() -> coffeeMachine.buy(new Espresso()));
        assertDoesNotThrow(() -> coffeeMachine.buy(new Latte()));
        assertDoesNotThrow(() -> coffeeMachine.buy(new Cappuccino()));
    }

    @Test
    @DisplayName("should buy lots of coffee when filled a lot of consumables")
    void testTakeMoney() {
        int moneyBeforeTake = coffeeMachine.getMoneyAmount();

        System.out.println(moneyBeforeTake);
        assertDoesNotThrow(() -> coffeeMachine.buy(new Espresso()));

        coffeeMachine.takeMoney();

        int moneyAfterTake = coffeeMachine.getMoneyAmount();
        System.out.println(moneyAfterTake);

        assertNotEquals(moneyBeforeTake, moneyAfterTake);
    }

    @Test
    @DisplayName("buys lots of coffee when created with builder")
    void testBuyLotsOfCoffeeWithMegaCoffeeMachine() {
        CoffeeMachine megaMachine = new CoffeeMachine.Builder()
                .withWater(9999)
                .withMilk(9999)
                .withBeans(9999)
                .withMoney(9999)
                .build();

        assertDoesNotThrow(() -> megaMachine.buy(new Espresso()));
        assertDoesNotThrow(() -> megaMachine.buy(new Latte()));
        assertDoesNotThrow(() -> megaMachine.buy(new Cappuccino()));
    }
}