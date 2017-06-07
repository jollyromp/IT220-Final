package maze.view;/*
 * Caleb Snoozy
 * 6/1/2017
 * maze.view
 * TextInput.java
 * Final - description
 */

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import maze.utils.TaskManager;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TextInput extends TextField {

    private final String defaultPrompt = "This will display relevant commands if empty. [move] [attack] [etc...]";
    private LinkedList<String> currentCommands = new LinkedList<>();

    public TextInput() {
        setPrompt(defaultPrompt);
        addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                tabComplete();
                event.consume();
            }
        });
    }

    public void setPrompt(String text) {
        TaskManager.autoRunTask(() -> setPromptText(text));
    }

    public void resetPrompt() {
        setPrompt(defaultPrompt);
    }

    public String getInput(String prompt, Predicate<String> restriction) {
        Object lock = new Object();
        String result = "";
        setPrompt(prompt);
        setOnAction(event -> {
            synchronized (lock) {
                lock.notify();
            }
        });
        while (result.isEmpty()) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (restriction.test(getText())) {
                result = getText();
            } else
                System.err.println("Invalid input");
        }
        clear();
        resetPrompt();
        return result;
    }


    public void setCurrentCommands(String... newCommands) {
        currentCommands = new LinkedList<>(Arrays.stream(newCommands).map(String::toLowerCase).collect(Collectors.toList()));
    }

    /**
     * Auto completes the last word in a command string to a matching word in the stored list.
     */
    private void tabComplete() {
        String[] words = getText().split(" ");
        String latest = words[words.length - 1].toLowerCase();
        for (int i = 0; i < currentCommands.size(); i++) {
            if (currentCommands.get(i).equals(latest)) {
                if (i + 1 < currentCommands.size())
                    latest = currentCommands.get(i + 1);
                else
                    latest = currentCommands.getFirst();
                break;
            } else if (currentCommands.get(i).contains(latest)) {
                latest = currentCommands.get(i);
                break;
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            result.append(words[i]).append(" ");
        }
        result.append(latest);
        setText(result.toString());
    }
}
