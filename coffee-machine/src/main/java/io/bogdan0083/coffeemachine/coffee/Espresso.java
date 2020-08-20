package io.bogdan0083.coffeemachine.coffee;

public class Espresso extends Coffee {
    static final int water = 250;
    static final int beans = 16;
    static final int price = 4;

    public int getWater() {
        return water;
    }

    public int getBeans() {
        return beans;
    }

    public int getPrice() {
        return price;
    }
}
