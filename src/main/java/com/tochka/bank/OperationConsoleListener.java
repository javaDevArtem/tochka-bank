package com.tochka.bank;

import java.util.Scanner;

public class OperationConsoleListener {

    private final Scanner scanner;

    public OperationConsoleListener(Scanner scanner) {
        this.scanner = scanner;
    }

    public void listenUpdates() {
        System.out.println("Please type operations:\n");
        while (true) {
            System.out.println("Please type next operations:\n");
            String nextOperation = scanner.nextLine();
            if (nextOperation.equals(OperationCommand.USER_CREATE.toString())) {
                System.out.println("User Created\n");
            } else if (nextOperation.equals(OperationCommand.ACCOUNT_CREATE.toString())) {
                System.out.println("Account Created\n");

            } else {
                System.out.println("Not found operation");
            }
        }
    }
}
