package stages;

import java.util.*;

public class GameAlgorithm {

    private Random random;

    public GameAlgorithm() {
        random = new Random();
    }

    // Method to generate positions for the grid
    public int[][] generateGridPositions(int gridSize) {
        int[][] positions = new int[gridSize][gridSize];

        // Populate the grid with random values corresponding to item indices
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                positions[row][col] = random.nextInt(4); // Random index (4 items)
            }
        }

        return positions;
    }
}
