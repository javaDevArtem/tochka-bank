package com.tochka.bank;

import com.tochka.bank.account.Account;
import com.tochka.bank.account.AccountService;
import com.tochka.bank.user.User;
import com.tochka.bank.user.UserService;

import java.util.List;
import java.util.Scanner;

public class OperationConsoleListener {

    private final Scanner scanner;
    private final AccountService accountService;
    private final UserService userService;

    public OperationConsoleListener(Scanner scanner, AccountService accountService, UserService userService) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.userService = userService;
    }

    public void listenUpdates() {
        String operationType = "";
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
        if (operation.equals(OperationCommand.USER_CREATE.toString())) {
            System.out.println("Enter login for new User:\n");
            String login = scanner.nextLine();
            User user = userService.createUser(login);
            System.out.println("User created =" + user.toString());
        } else if (operation.equals(OperationCommand.SHOW_ALL_USERS.toString())) {
            List<User> userList = userService.getAllUsers();
            System.out.println("List of all users:\n");

            userList.forEach(System.out::println);
        } else if (operation.equals(OperationCommand.ACCOUNT_CREATE.toString())) {
            System.out.println("Enter the user id for which to create an account:\n");
            int userId = Integer.parseInt(scanner.nextLine());
            User user = userService.findUserById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("No such user with id=%s".formatted(userId)));
            Account account = accountService.createAccount(user);
            user.getAccountList().add(account);
            System.out.printf("Account created with id: %s for user: %s\n", account.getId(), user.getLogin());

        } else {
            System.out.println("Not found operation");
        }
    }
}
