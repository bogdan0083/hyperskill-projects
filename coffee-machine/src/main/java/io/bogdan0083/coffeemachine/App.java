package io.bogdan0083.coffeemachine;

import io.bogdan0083.coffeemachine.cli.CoffeeMachineCLI;

public class App {
    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine.Builder().build();
        CoffeeMachineCLI cli = new CoffeeMachineCLI(machine);
        cli.run();
    }
}
