package io.bogdan0083.coffeemachine.coffee;

public class Latte extends Coffee {
    static final int water = 350;
    static final int milk = 75;
    static final int beans = 20;
    static final int price = 7;

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getBeans() {
        return beans;
    }

    public int getPrice() {
        return price;
    }
}
