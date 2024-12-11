package stages;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a button to navigate to the layout scene
        Button goToLayoutButton = new Button("Go to Layout");

        // Set the button action to change the scene
        goToLayoutButton.setOnAction(e -> {
            // Create a new DynamicStage instance and set the layout scene
            DynamicStage dynamicStage = new DynamicStage();
            primaryStage.setScene(dynamicStage.getScene()); // Switch to the layout scene

            // Maximize the window after switching to the layout scene
            primaryStage.setMaximized(true);
        });

        // Create the main scene for the main view with the button
        Scene mainScene = new Scene(goToLayoutButton, AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);

        // Set the title for the main window
        primaryStage.setTitle("Main Scene");

        // Maximize the main window
        primaryStage.setMaximized(true);

        // Set the initial scene and show the stage
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
