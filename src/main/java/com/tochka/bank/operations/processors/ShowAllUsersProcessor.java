package com.tochka.bank.operations.processors;

import com.tochka.bank.operations.OperationCommandProcessor;
import com.tochka.bank.user.User;
import com.tochka.bank.user.UserService;

import java.util.List;


public class ShowAllUsersProcessor implements OperationCommandProcessor {

    private final UserService userService;

    public ShowAllUsersProcessor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processOperation() {
        List<User> userList = userService.getAllUsers();
        System.out.println("List of all users:\n");
        userList.forEach(System.out::println);
    }
}
