package maze.utils;/*
 * Caleb Snoozy
 * 6/4/2017
 * maze.utils
 * TaskManager.java
 * Final - description
 */

import javafx.application.Platform;
import javafx.concurrent.Task;

public class TaskManager {
    private TaskManager() {
    }

    public static void autoRunTask(Runnable runnable) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(runnable);
                return null;
            }
        };
        task.run();
    }

    public static Task<Void> newTask(Runnable runnable) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(runnable);
                return null;
            }
        };
    }
}
