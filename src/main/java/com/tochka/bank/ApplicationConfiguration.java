package com.tochka.bank;

import com.tochka.bank.account.AccountService;
import com.tochka.bank.user.User;
import com.tochka.bank.user.UserSevice;
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
    public OperationConsoleListener operationConsoleListener(Scanner scanner, AccountService accountService, UserSevice userSevice
    ) {
        return new OperationConsoleListener(scanner, accountService, userSevice);

    }

    @Bean
    public UserSevice userSevice(AccountService accountService) {
        return new UserSevice(accountService);
    }

    @Bean
    public AccountService accountService() {
        return new AccountService();
    }
}
