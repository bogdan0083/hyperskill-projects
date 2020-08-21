package io.bogdan0083.coffeemachine.cli;

import io.bogdan0083.coffeemachine.Consumables;

import java.util.Scanner;

public class SupplyConsumablesScanner {
    private final Scanner scanner;

    public SupplyConsumablesScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Consumables scan() {
        int water = scanWaterAmount();
        int milk = scanMilkAmount();
        int coffeeBeans = scanCoffeeBeansAmount();
        int cups = scanCoffeeCupsAmount();

        Consumables scanned = new Consumables();

        scanned.water = water;
        scanned.milk = milk;
        scanned.coffeeBeans = coffeeBeans;
        scanned.cups = cups;
        return scanned;
    }

    private int scanWaterAmount() throws NumberFormatException {
        System.out.println("Write how many ml of water do you want to add:");
        String water = this.scanner.next();
        return Integer.parseInt(water);
    }

    private int scanMilkAmount() throws NumberFormatException {
        System.out.println("Write how many ml of milk do you want to add:");
        String milk = this.scanner.next();
        return Integer.parseInt(milk);
    }

    private int scanCoffeeBeansAmount() throws NumberFormatException {
        System.out.println("Write how many grams of coffee beans do you want to add:");
        String coffeeBeans = this.scanner.next();
        return Integer.parseInt(coffeeBeans);
    }

    private int scanCoffeeCupsAmount() throws NumberFormatException {
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        String cups = this.scanner.next();
        return Integer.parseInt(cups);
    }
}

