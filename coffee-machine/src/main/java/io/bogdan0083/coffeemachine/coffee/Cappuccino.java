package io.bogdan0083.coffeemachine.coffee;

public class Cappuccino extends Coffee {
    static final int water = 200;
    static final int milk = 100;
    static final int beans = 12;
    static final int price = 6;

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
