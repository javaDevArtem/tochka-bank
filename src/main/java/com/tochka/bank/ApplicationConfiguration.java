package com.tochka.bank;

import com.tochka.bank.account.AccountService;
import com.tochka.bank.operations.ConsoleOperationType;
import com.tochka.bank.operations.OperationCommandProcessor;
import com.tochka.bank.operations.processors.CreateAccountProcessor;
import com.tochka.bank.operations.processors.CreateUserProcessor;
import com.tochka.bank.operations.processors.ShowAllUsersProcessor;
import com.tochka.bank.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Scanner;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public OperationConsoleListener operationConsoleListener(
            Scanner scanner,
            CreateUserProcessor createUserProcessor,
            CreateAccountProcessor createAccountProcessor,
            ShowAllUsersProcessor showAllUsersProcessor
    ) {
        Map<ConsoleOperationType, OperationCommandProcessor> processorMap =
                Map.of(
                        ConsoleOperationType.USER_CREATE, createUserProcessor,
                        ConsoleOperationType.ACCOUNT_CREATE, createAccountProcessor,
                        ConsoleOperationType.SHOW_ALL_USERS, showAllUsersProcessor

                );
        return new OperationConsoleListener(scanner, processorMap);

    }

    @Bean
    public UserService userService(AccountService accountService) {
        return new UserService(accountService);
    }

    @Bean
    public AccountService accountService() {
        return new AccountService();
    }
}
