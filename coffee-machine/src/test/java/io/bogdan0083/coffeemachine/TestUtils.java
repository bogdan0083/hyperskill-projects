package io.bogdan0083.coffeemachine;

public class TestUtils {
    public static CoffeeConsumables getHugeCoffeeConsumables() {
        CoffeeConsumables coffeeConsumables = new CoffeeConsumables();
        coffeeConsumables.water = 9999;
        coffeeConsumables.milk = 9999;
        coffeeConsumables.beans = 9999;
        coffeeConsumables.cups = 9999;

        return coffeeConsumables;
    }

    public static CoffeeConsumables getCoffeeConsumables() {
        CoffeeConsumables coffeeConsumables = new CoffeeConsumables();
        coffeeConsumables.water = 500;
        coffeeConsumables.milk = 500;
        coffeeConsumables.beans = 500;
        coffeeConsumables.cups = 500;

        return coffeeConsumables;
    }
}
