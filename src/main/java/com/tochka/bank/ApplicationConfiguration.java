package com.tochka.bank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Scanner;

@Configuration
@ComponentScan("com.tochka.bank")
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

}
