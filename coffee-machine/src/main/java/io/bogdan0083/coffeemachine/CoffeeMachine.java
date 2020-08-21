package io.bogdan0083.coffeemachine;

import io.bogdan0083.coffeemachine.coffee.Coffee;

public class CoffeeMachine {
    private final CoffeeMachineState state;

    public CoffeeMachine() {
        this.state = new CoffeeMachineState();
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
    }

    public void takeMoney() {
        this.state.setMoney(0);
    }

    public void fill(Consumables consumables) {
        state.setWater(state.getWater() + consumables.water);
        state.setMilk(state.getMilk() + consumables.milk);
        state.setBeans(state.getBeans() + consumables.coffeeBeans);
        state.setCups(state.getCups() + consumables.cups);
    }

    public CoffeeMachineState getState() {
        return state;
    }

    public int getMoneyAmount() {
        return state.getMoney();
    }

    public void buy(Coffee coffee) throws CoffeePurchaseException {
        int waterAfterPurchase = state.getWater() - coffee.getWater();
        int milkAfterPurchase = state.getMilk() - coffee.getMilk();
        int beansAfterPurchase = state.getBeans() - coffee.getBeans();
        int cupsAfterPurchase = state.getCups() - 1;

        if (waterAfterPurchase < 0) {
            throw new CoffeePurchaseException("Sorry, not enough water!");
        }

        if (milkAfterPurchase < 0) {
            throw new CoffeePurchaseException("Sorry, not enough milk!");
        }

        if (beansAfterPurchase < 0) {
            throw new CoffeePurchaseException("Sorry, not enough coffee beans!");
        }

        if (cupsAfterPurchase < 0) {
            throw new CoffeePurchaseException("Sorry, not enough disposable cups!");
        }

        state.setWater(state.getWater() - coffee.getWater());
        state.setMilk(state.getMilk() - coffee.getMilk());
        state.setBeans(state.getBeans() - coffee.getBeans());
        state.setMoney(state.getMoney() + coffee.getPrice());
        state.setCups(state.getCups() - 1);
    }
}
