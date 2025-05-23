package com.tochka.bank.operations.processors;

import com.tochka.bank.operations.ConsoleOperationType;
import com.tochka.bank.operations.OperationCommandProcessor;
import com.tochka.bank.user.User;
import com.tochka.bank.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateUserProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final UserService userService;

    public CreateUserProcessor(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter login for new User:\n");
        String login = scanner.nextLine();
        User user = userService.createUser(login);
        System.out.println("User created =" + user.toString());
    }
}
