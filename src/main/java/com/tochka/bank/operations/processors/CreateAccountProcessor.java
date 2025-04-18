package com.tochka.bank.operations.processors;

import com.tochka.bank.account.Account;
import com.tochka.bank.account.AccountService;
import com.tochka.bank.operations.OperationCommandProcessor;
import com.tochka.bank.user.User;
import com.tochka.bank.user.UserService;

import java.util.Scanner;

public class CreateAccountProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public CreateAccountProcessor(Scanner scanner, UserService userService, AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter the user id for which to create an account:\n");
        int userId = Integer.parseInt(scanner.nextLine());
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user with id=%s".formatted(userId)));
        Account account = accountService.createAccount(user);
        user.getAccountList().add(account);
        System.out.printf("Account created with id: %s for user: %s\n", account.getId(), user.getLogin());
    }
}
