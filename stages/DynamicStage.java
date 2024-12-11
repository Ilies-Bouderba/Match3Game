package stages;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

public class DynamicStage {

    private static final String[] imagePaths = {
        "assets/img/red.png",    // Image 1 (Red)
        "assets/img/purple.png",  // Image 2 (Purple)
        "assets/img/black.png",   // Image 3 (Blue)
        "assets/img/yellow.png",  // Image 4 (Yellow)
        "assets/img/grey.png",  // Image 4 (Yellow)
        "assets/img/orange.png"  // Image 4 (Yellow)
    };
    
    public Scene getScene() {
        HBox hbox = new HBox();

        Region white1 = new Region();
        white1.setStyle("-fx-background-color: white;");
        white1.setPrefWidth(AppConfig.getRegionWidth(AppConfig.WINDOW_WIDTH, AppConfig.WHITE_PERCENTAGE));

        Region blue = new Region();
        blue.setStyle("-fx-background-color: blue;");
        blue.setPrefWidth(AppConfig.getRegionWidth(AppConfig.WINDOW_WIDTH, AppConfig.BLUE_PERCENTAGE));

        Region white2 = new Region();
        white2.setStyle("-fx-background-color: white;");
        white2.setPrefWidth(AppConfig.getRegionWidth(AppConfig.WINDOW_WIDTH, AppConfig.WHITE_PERCENTAGE));

        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: green;");
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Grid size (8x8)
        int rows = 8;
        int cols = 8;

        // Create an 8x8 grid
        String[][] grid = new String[rows][cols];

        // Randomize the grid while ensuring solvability
        fillGridWithValidImages(grid);

        // Calculate available width and height for the grid (excluding other regions)
        double availableWidth = AppConfig.WINDOW_WIDTH * AppConfig.GREEN_PERCENTAGE;
        double availableHeight = AppConfig.WINDOW_HEIGHT;

        // Adjust the width and height of each cell in the grid dynamically
        double gap = gridPane.getHgap();
        double cellWidth = (availableWidth - (gap * (cols - 1))) / cols;
        double cellHeight = (availableHeight - (gap * (rows - 1))) / rows;

        // Populate gridPane with ImageView instances based on grid data
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                ImageView imageView = new ImageView();
                imageView.setFitWidth(cellWidth);
                imageView.setFitHeight(cellHeight);

                // Load image based on the grid data
                String imagePath = "file:" + imagePaths[getImageIndex(grid[row][col])];
                Image image = new Image(imagePath);
                imageView.setImage(image);

                gridPane.add(imageView, col, row);
            }
        }

        HBox.setHgrow(gridPane, Priority.ALWAYS);

        hbox.getChildren().addAll(white1, blue, white2, gridPane);
        Scene layoutScene = new Scene(hbox, AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);

        // Add resize listeners to dynamically adjust grid size
        layoutScene.widthProperty().addListener((obs, oldWidth, newWidth) -> adjustGridSize(gridPane, ((Number) newWidth).doubleValue(), AppConfig.WINDOW_HEIGHT));
        layoutScene.heightProperty().addListener((obs, oldHeight, newHeight) -> adjustGridSize(gridPane, AppConfig.WINDOW_WIDTH, ((Number) newHeight).doubleValue()));

        return layoutScene;
    }

    private void adjustGridSize(GridPane gridPane, double newWidth, double newHeight) {
        int rows = 8;
        int cols = 8;
        double gap = gridPane.getHgap();
        
        // Calculate available width and height for the grid (excluding other regions)
        double availableWidth = newWidth * AppConfig.GREEN_PERCENTAGE;
        double availableHeight = newHeight;

        // Adjust the width and height of each cell in the grid dynamically
        double cellWidth = (availableWidth - (gap * (cols - 1))) / cols;
        double cellHeight = (availableHeight - (gap * (rows - 1))) / rows;

        // Ensure the grid stays within bounds
        gridPane.setMaxWidth(availableWidth);
        gridPane.setMaxHeight(availableHeight);

        // Adjust all image views to the new size
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            ImageView imageView = (ImageView) gridPane.getChildren().get(i);
            imageView.setFitWidth(cellWidth);
            imageView.setFitHeight(cellHeight);
        }
    }

    private void fillGridWithValidImages(String[][] grid) {
        Random rand = new Random();
        boolean solvable = false;

        // Keep randomizing until we have a solvable grid
        while (!solvable) {
            // Randomly fill the grid with images
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    int randomIndex = rand.nextInt(imagePaths.length);
                    grid[row][col] = imagePaths[randomIndex];
                }
            }

            // Check if the grid is solvable
            solvable = isSolvable(grid);
        }
    }

    private boolean isSolvable(String[][] grid) {
        // Check for any horizontal or vertical matches of 3 or more
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                // Horizontal match check
                if (col + 2 < grid[row].length && grid[row][col].equals(grid[row][col + 1]) && grid[row][col].equals(grid[row][col + 2])) {
                    return true;
                }

                // Vertical match check
                if (row + 2 < grid.length && grid[row][col].equals(grid[row + 1][col]) && grid[row][col].equals(grid[row + 2][col])) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getImageIndex(String imagePath) {
        // This method returns the index of the image in the imagePaths array
        for (int i = 0; i < imagePaths.length; i++) {
            if (imagePaths[i].equals(imagePath)) {
                return i;
            }
        }
        return -1;  // Return -1 if not found (shouldn't happen)
    }
}
