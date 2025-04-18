package com.tochka.bank;

import com.tochka.bank.account.AccountService;
import com.tochka.bank.user.User;
import com.tochka.bank.user.UserSevice;

import java.util.Scanner;

public class OperationConsoleListener {

    private final Scanner scanner;
    private final AccountService accountService;
    private final UserSevice userSevice;

    public OperationConsoleListener(Scanner scanner, AccountService accountService, UserSevice userSevice) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.userSevice = userSevice;
    }

    public void listenUpdates() {
        String operationType;
        while (true) {
            try {
                operationType = listenNextOperation();
                processNextOperation(operationType);
            } catch (Exception e) {
                System.out.printf("Error executing command %s: error =%s%n", operationType, e.getMessage());
            }
        }
    }

    private String listenNextOperation() {
        System.out.println("Please type operations:\n");
        return scanner.nextLine();
    }

    private void processNextOperation(String operation) {
        if (operation.equals(OperationCommand.USER_CREATE)) {
            System.out.println("Enter login for new User:\n");
            String login = scanner.nextLine();
            User user = userSevice.createUser(login);
            System.out.println("User created =" + user.toString());
        } else if (operation.equals(OperationCommand.ACCOUNT_CREATE)) {
            System.out.println("Account Created\n");

        } else {
            System.out.println("Not found operation");
        }
    }
}
