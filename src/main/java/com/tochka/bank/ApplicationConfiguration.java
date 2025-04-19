package com.tochka.bank;

import com.tochka.bank.account.AccountProperties;
import com.tochka.bank.account.AccountService;
import com.tochka.bank.operations.ConsoleOperationType;
import com.tochka.bank.operations.OperationCommandProcessor;
import com.tochka.bank.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public OperationConsoleListener operationConsoleListener(
            Scanner scanner,
            List<OperationCommandProcessor> commandProcessorList
    ) {
        Map<ConsoleOperationType, OperationCommandProcessor> processorMap =
                commandProcessorList.stream()
                        .collect(Collectors.toMap(
                                processor -> processor.getOperationType(),
                                processor -> processor
                        ));
        return new OperationConsoleListener(scanner, processorMap);

    }

    @Bean
    public UserService userService(AccountService accountService) {
        return new UserService(accountService);
    }

    @Bean
    public AccountService accountService(
            AccountProperties accountProperties
    ) {
        return new AccountService(accountProperties);
    }

    @Bean
    public ConsoleListenerStarter consoleListenerStarter(
            OperationConsoleListener consoleListener
    ) {
        return new ConsoleListenerStarter(consoleListener);
    }
}
