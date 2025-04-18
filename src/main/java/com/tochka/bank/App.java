package com.tochka.bank;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.tochka.bank");
        OperationConsoleListener operationConsoleListener = context.getBean(OperationConsoleListener.class);
        operationConsoleListener.start();
        operationConsoleListener.listenUpdates();
        operationConsoleListener.endListen();
    }
}
