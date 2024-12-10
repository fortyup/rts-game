package org.example.controller;

import javafx.application.Platform;

import java.util.LinkedList;
import java.util.Queue;

public class CommandManager {
    private final Queue<Command> bagOfCommands = new LinkedList<>();
    private boolean isRunning;

    public CommandManager() {
        startProcessingCommands();
    }

    public synchronized void addCommand(Command command) {
        bagOfCommands.add(command);
    }

    public void startProcessingCommands() {
        if (isRunning) return;
        isRunning = true;

        new Thread(() -> {
            while (true) {
                Command command = null;

                synchronized (this) {
                    if (!bagOfCommands.isEmpty()) {
                        command = bagOfCommands.poll();
                    }
                }

                if (command != null) {
                    final Command finalCommand = command;
                    Platform.runLater(finalCommand::execute);
                }

                try {
                    Thread.sleep(100); // Pause entre deux traitements
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }
}
