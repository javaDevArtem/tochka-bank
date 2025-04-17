package com.tochka.bank;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.tochka.bank");
        context.getBean(OperationConsoleListener.class).listenUpdates();
    }
}
