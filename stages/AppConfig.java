package stages;

import javafx.stage.Screen;

public class AppConfig {

    // Get the screen's resolution dynamically
    public static final double WINDOW_WIDTH = 1000;  
    public static final double WINDOW_HEIGHT =600;  

    // Color sections' percentage
    public static final double WHITE_PERCENTAGE = 0.02;  // 2% for white sections
    public static final double BLUE_PERCENTAGE = 0.4;   // 16% for blue section
    public static final double GREEN_PERCENTAGE = 0.50;  // 80% for green section

    // Method to update the region width dynamically based on screen size
    public static double getRegionWidth(double screenWidth, double percentage) {
        return screenWidth * percentage;
    }

    // Additional settings like margins, padding, or fonts can be added here
    public static final String BUTTON_TEXT = "Go to Second Scene";
    
    // Other config values can be added here, like theme settings, default fonts, etc.
}
