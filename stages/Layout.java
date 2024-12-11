    package stages;

    import javafx.scene.Scene;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.Region;
    import javafx.scene.layout.Priority;
    import javafx.scene.layout.GridPane;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;

    public class Layout {

        public Scene getScene() {
            // Create an HBox layout for horizontal stacking
            HBox hbox = new HBox();

            // Create the white section (2% of the width)
            Region white1 = new Region();
            white1.setStyle("-fx-background-color: white;");
            white1.setPrefWidth(AppConfig.getRegionWidth(AppConfig.WINDOW_WIDTH, AppConfig.WHITE_PERCENTAGE));

            // Create the blue section (16% of the width)
            Region blue = new Region();
            blue.setStyle("-fx-background-color: blue;");
            blue.setPrefWidth(AppConfig.getRegionWidth(AppConfig.WINDOW_WIDTH, AppConfig.BLUE_PERCENTAGE));

            // Create the second white section (2% of the width)
            Region white2 = new Region();
            white2.setStyle("-fx-background-color: white;");
            white2.setPrefWidth(AppConfig.getRegionWidth(AppConfig.WINDOW_WIDTH, AppConfig.WHITE_PERCENTAGE));

            // Create the 8x8 GridPane in place of the green area
            GridPane gridPane = new GridPane();
            gridPane.setStyle("-fx-background-color: green;");
            
            // Set horizontal and vertical gap between grid cells
            gridPane.setHgap(5);  // Horizontal gap (space between columns)
            gridPane.setVgap(5);  // Vertical gap (space between rows)

            // Load an image (replace with the actual image path)
            Image image = new Image("assets\\img\\yellow.png");

            // Calculate the width and height of each cell, accounting for gaps
            double availableWidth = AppConfig.WINDOW_WIDTH * AppConfig.GREEN_PERCENTAGE;
            double availableHeight = AppConfig.WINDOW_HEIGHT;
            double gap = gridPane.getHgap();
            double cellWidth = (availableWidth - (gap * 7)) / 8;  // Subtracting space for horizontal gaps
            double cellHeight = availableHeight / 8;  // Divide the height by 8 for the rows

            // Create an 8x8 grid (8 rows, 8 columns)
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(cellWidth);  // Set width based on available space
                    imageView.setFitHeight(cellHeight);  // Set height based on available space
                    gridPane.add(imageView, col, row);  // Add to the grid at (col, row)
                }
            }

            // Set grid width to fill the available space
            HBox.setHgrow(gridPane, Priority.ALWAYS);

            // Add all the sections to the HBox
            hbox.getChildren().addAll(white1, blue, white2, gridPane);

            // Create the scene with a responsive HBox layout
            Scene layoutScene = new Scene(hbox, AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);

            // Add listener for resizing
            layoutScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                // Update the width of each region based on the new window size
                white1.setPrefWidth(AppConfig.getRegionWidth(newWidth.doubleValue(), AppConfig.WHITE_PERCENTAGE));
                blue.setPrefWidth(AppConfig.getRegionWidth(newWidth.doubleValue(), AppConfig.BLUE_PERCENTAGE));
                white2.setPrefWidth(AppConfig.getRegionWidth(newWidth.doubleValue(), AppConfig.WHITE_PERCENTAGE));
                
                // Recalculate the gap and the cell width
                double availableWidthForCells = newWidth.doubleValue() * AppConfig.GREEN_PERCENTAGE;
                double newCellWidth = (availableWidthForCells - (gap * 7)) / 8;
                
                // Update the grid cell width based on the new window size
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        ImageView imageView = (ImageView) gridPane.getChildren().get(row * 8 + col);
                        imageView.setFitWidth(newCellWidth);  // Adjust width based on the gap
                    }
                }
            });

            layoutScene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                // Update the grid cell height based on the new window size
                double newCellHeight = newHeight.doubleValue() / 8;  // Recalculate height per row
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        ImageView imageView = (ImageView) gridPane.getChildren().get(row * 8 + col);
                        imageView.setFitHeight(newCellHeight);  // Adjust height based on the window size
                    }
                }
            });

            return layoutScene;
        }
    }
