package com.tochka.bank;

import com.tochka.bank.operations.ConsoleOperationType;
import com.tochka.bank.operations.OperationCommandProcessor;

import java.util.Map;
import java.util.Scanner;

public class OperationConsoleListener {

    private final Scanner scanner;
    private final Map<ConsoleOperationType, OperationCommandProcessor> processorMap;

    public OperationConsoleListener(Scanner scanner, Map<ConsoleOperationType, OperationCommandProcessor> processorMap) {
        this.scanner = scanner;
        this.processorMap = processorMap;
    }

    public void listenUpdates() {
        System.out.println("Please type operations:\n");
        while (true) {
            ConsoleOperationType operationType = listenNextOperation();
            processNextOperation(operationType);
        }
    }


    private ConsoleOperationType listenNextOperation() {
        System.out.println("Please type operations: ");
        while (true) {
            var nextOperation = scanner.nextLine();
            try {
                return ConsoleOperationType.valueOf(nextOperation);
            } catch (IllegalArgumentException e) {
                System.out.println("No such command found");
            }
        }
    }

    private void processNextOperation(ConsoleOperationType operation) {
        try {
            OperationCommandProcessor processor = processorMap.get(operation);
            processor.processOperation();
        } catch (Exception e) {
            System.out.printf("Error executing command %s: error =%s%n", operation, e.getMessage());
        }
    }


}
