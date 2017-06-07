package maze.view;
/*
 * Caleb Snoozy
 * 6/1/2017
 * maze.view
 * TextView.java
 * Final - description
 */

import javafx.scene.control.TextArea;
import maze.Driver;

import java.util.ArrayList;

public class TextView extends TextArea {

    private final char NL = '\n';
    private final char PIPE = '|';
    private final char BAR = '-';
    private static final int TEXT_WIDTH = 132;
    private static final int TEXT_HEIGHT = 35;
    private static final int TEXT_LOG_WIDTH = 100;

    // Text areas
    private String title = "TITLE";
    private String location = "LOCATION";
    private String[] log = new String[TEXT_HEIGHT - 1];

    private ArrayList<String> lines = new ArrayList<>();
    private ArrayList<String> sideBar = new ArrayList<>();

    public TextView() {
        setEditable(false);
        setWrapText(false);

        for (int i = 0; i < TEXT_HEIGHT; i++) {
            lines.add("");
            sideBar.add("");
        }

        lines.set(lines.size() - 1, makeLine(TEXT_LOG_WIDTH));
        sideBar.set(sideBar.size() - 1, makeLine(TEXT_WIDTH - TEXT_LOG_WIDTH));

        render();
    }

    private void render() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < TEXT_LOG_WIDTH; i++) {
            if (title.length() > 0 && title.length() > i)
                builder.append(title.charAt(i));
            else
                builder.append(" ");
        }
        builder.append(PIPE);
        for (int i = 0; i < TEXT_WIDTH - TEXT_LOG_WIDTH; i++) {
            if (location.length() > 0 && location.length() > i)
                builder.append(location.charAt(i));
            else
                builder.append(" ");
        }
        builder.append(NL);

        for (int j = lines.size() - 1; j >= 0; j--) {
            String line = lines.get(j);
            String data = sideBar.get(j);
            for (int i = 0; i < TEXT_LOG_WIDTH; i++) {
                if (line.length() > 0 && line.length() > i)
                    builder.append(line.charAt(i));
                else
                    builder.append(" ");
            }
            builder.append(PIPE);

            for (int i = 0; i < TEXT_WIDTH - TEXT_LOG_WIDTH; i++) {
                if (data.length() > 0 && data.length() > i)
                    builder.append(data.charAt(i));
                else
                    builder.append(" ");
            }

            builder.append(NL);
        }

        setText(builder.toString());
    }

    public void setTitle(String title) {
        this.title = title;
        Driver.setTitle(title);
        render();
    }

    public void setLocation(String location) {
        this.location = location;
        render();
    }

    public void addToLog(String string) {
        lines.add(0, string);
        lines.remove(lines.size() - 2);
        render();
    }

    private String makeLine(int width) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < width; i++) {
            builder.append(BAR);
        }
        return builder.toString();
    }


}
