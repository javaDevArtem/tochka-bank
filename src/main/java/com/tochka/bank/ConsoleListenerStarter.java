package com.tochka.bank;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class ConsoleListenerStarter {

    private final OperationConsoleListener consoleListener;

    private Thread consoleListenerThread;

    public ConsoleListenerStarter(OperationConsoleListener consoleListener) {
        this.consoleListener = consoleListener;
    }

    @PostConstruct
    public void postConstruct() {
        this.consoleListenerThread = new Thread(() -> {
            consoleListener.start();
            consoleListener.listenUpdates();
        });
        consoleListenerThread.start();
    }

    @PreDestroy
    public void preDestroy() {
        consoleListenerThread.interrupt();
        consoleListener.endListen();
    }
}
