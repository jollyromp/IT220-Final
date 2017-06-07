package maze;
/*
 * Name: Caleb Snoozy, Jacob Tran
 * Date: 5/31/2017
 * Driver.java
 * Load files and start the maze game
 */


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import maze.game.Game;
import maze.utils.TaskManager;
import maze.view.TextInput;
import maze.view.TextView;

public class Driver extends Application {

    // Constants
    private static final String TITLE = "Text based maze crawler.";

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;

    // Stage
    private static Stage primaryStage;

    // UI
    private TextInput input = new TextInput();
    private TextView output = new TextView();

    // UI Scaling

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Driver.primaryStage = primaryStage;
        primaryStage.setTitle(TITLE);
        BorderPane root = new BorderPane();

        root.setCenter(output);
        root.setBottom(input);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.BLACK);
        scene.getStylesheets().add("styles.css");

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        System.out.println("Screen Loaded");

        Thread game = new Thread(() -> new Game(input, output));
        game.start();

        primaryStage.setOnCloseRequest(event -> game.stop());
    }

    public static void setTitle(String title) {
        TaskManager.autoRunTask(() -> primaryStage.setTitle(title));
    }
}
