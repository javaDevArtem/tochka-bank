package com.tochka.bank;

import com.tochka.bank.account.AccountService;
import com.tochka.bank.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public OperationConsoleListener operationConsoleListener(Scanner scanner, AccountService accountService, UserService userService
    ) {
        return new OperationConsoleListener(scanner, accountService, userService);

    }

    @Bean
    public UserService userSevice(AccountService accountService) {
        return new UserService(accountService);
    }

    @Bean
    public AccountService accountService() {
        return new AccountService();
    }
}
