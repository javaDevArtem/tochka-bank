package com.tochka.bank.operations;

import com.tochka.bank.account.AccountProperties;
import com.tochka.bank.account.AccountService;
import com.tochka.bank.operations.processors.*;
import com.tochka.bank.user.UserService;
import org.springframework.beans.factory.annotation.Value;
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

    @Bean
    public AccountDepositProcessor accountDepositProcessor(
            Scanner scanner,
            AccountService accountService
    ) {
        return new AccountDepositProcessor(scanner, accountService);
    }

    @Bean
    public AccountWithdrawProcessor accountWithdrawProcessor(
            Scanner scanner,
            AccountService accountService
    ) {
        return new AccountWithdrawProcessor(scanner, accountService);
    }

    @Bean
    public CloseAccountProcessor closeAccountProcessor(
            Scanner scanner,
            AccountService accountService,
            UserService userService
    ) {
        return new CloseAccountProcessor(scanner, accountService, userService);
    }

    @Bean
    public AccountTransferProcessor accountTransferProcessor(
            Scanner scanner,
            AccountService accountService
    ) {
        return new AccountTransferProcessor(scanner, accountService);
    }

    @Bean
    public AccountProperties accountProperties(
            @Value("${account.default-amount}") int defaultAmount,
            @Value("${account.transfer-commission}") double transferCommission
    ) {
        return new AccountProperties(defaultAmount, transferCommission);
    }

}
