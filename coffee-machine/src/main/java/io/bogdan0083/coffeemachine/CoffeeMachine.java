package io.bogdan0083.coffeemachine;

import io.bogdan0083.coffeemachine.coffee.Coffee;

public class CoffeeMachine {
    private final CoffeeMachineState state;

    public CoffeeMachine(CoffeeMachineState state) {
        this.state = state;
    }

    public void takeMoney() {
        this.state.setMoney(0);
    }

    public void fill(CoffeeConsumables coffeeConsumables) {
        state.setWater(state.getWater() + coffeeConsumables.water);
        state.setMilk(state.getMilk() + coffeeConsumables.milk);
        state.setBeans(state.getBeans() + coffeeConsumables.beans);
        state.setCups(state.getCups() + coffeeConsumables.cups);
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
        int moneyAfterPurchase = state.getMoney() + coffee.getPrice();

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

        state.setWater(waterAfterPurchase);
        state.setMilk(milkAfterPurchase);
        state.setBeans(beansAfterPurchase);
        state.setMoney(moneyAfterPurchase);
        state.setCups(cupsAfterPurchase);
    }

    public static class Builder {
        // TODO: 21.08.2020 learn the difference between protected and private fields
        protected int water = 400;
        protected int milk = 540;
        protected int beans = 120;
        protected int cups = 9;
        protected int money = 550;

        public Builder withWater(int water) {
            this.water = water;
            return this;
        }

        public Builder withMilk(int milk) {
            this.milk = milk;
            return this;
        }

        public Builder withBeans(int beans) {
            this.beans = beans;
            return this;
        }

        public Builder withMoney(int money) {
            this.money = money;
            return this;
        }

        public Builder withCups(int cups) {
            this.cups = cups;
            return this;
        }

        public CoffeeMachine build() {
            CoffeeMachineState state = new CoffeeMachineState(water, milk, beans, cups, money);
            return new CoffeeMachine(state);
        }
    }
}
