package io.bogdan0083.coffeemachine;

import io.bogdan0083.coffeemachine.cli.CoffeeMachineCLI;

public class App {
    public static void main(String[] args) {
        // TODO: 21.08.2020 make coffee machine more composable
        CoffeeMachine machine = new CoffeeMachine.Builder().build();
        CoffeeMachineCLI cli = new CoffeeMachineCLI(machine);
        cli.run();
    }
}
