package com.tochka.bank.operations;

import com.tochka.bank.account.AccountService;
import com.tochka.bank.operations.processors.CreateAccountProcessor;
import com.tochka.bank.operations.processors.CreateUserProcessor;
import com.tochka.bank.operations.processors.ShowAllUsersProcessor;
import com.tochka.bank.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class OperationProcessorsConfiguration {

    @Bean
    public CreateUserProcessor createUserProcessor(
            Scanner scanner,
            UserService userService
    ) {
        return new CreateUserProcessor(scanner, userService);
    }

    @Bean
    public CreateAccountProcessor createAccountProcessor(
            Scanner scanner,
            UserService userService,
            AccountService accountService
    ) {
        return new CreateAccountProcessor(scanner, userService, accountService);
    }

    @Bean
    public ShowAllUsersProcessor showAllUsersProcessor(
            UserService userService
    ) {
        return new ShowAllUsersProcessor(userService);
    }

}
