package com.tochka.bank;

import com.tochka.bank.operations.ConsoleOperationType;
import com.tochka.bank.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class OperationConsoleListener {

    private final Scanner scanner;
    private final Map<ConsoleOperationType, OperationCommandProcessor> processorMap;

    public OperationConsoleListener(Scanner scanner, List<OperationCommandProcessor> processorList) {
        this.scanner = scanner;
        this.processorMap = processorList.stream()
                .collect(Collectors.toMap(
                        processor -> processor.getOperationType(),
                        processor -> processor
                ));
    }

    public void start() {
        System.out.println("Console listener started");
    }

    public void endListen() {
        System.out.println("Console listener end listen");

    }

    public void listenUpdates() {
        while (!Thread.currentThread().isInterrupted()) {
            ConsoleOperationType operationType = listenNextOperation();
            if (operationType == null) {
                return;
            }
            processNextOperation(operationType);
        }
    }

    private ConsoleOperationType listenNextOperation() {
        System.out.println("\nPlease type operations: ");
        printAllAvailableOperations();
        System.out.println();
        while (!Thread.currentThread().isInterrupted()) {
            var nextOperation = scanner.nextLine();
            try {
                return ConsoleOperationType.valueOf(nextOperation);
            } catch (IllegalArgumentException e) {
                System.out.println("No such command found");
            }
        }
        return null;
    }

    private void printAllAvailableOperations() {
        processorMap.keySet().forEach(System.out::println);
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
