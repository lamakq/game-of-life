import java.util.Arrays;

public class GameOfLife {

    private final int[][] cells;

    public GameOfLife(int cells[][]) {
        this.cells = cells;
    }

    public void tick() {

        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[row].length; column++) {
                if (cells[row][column] == 1) {
                    int livingNeighbours = getNumberOfLivingNeighboursFor(row, column);
                    checkForUnderpopulation(row, column, livingNeighbours);
                    checkForOverpopulation(row, column, livingNeighbours);
                }
            }
        }
    }

    private void checkForUnderpopulation(int row, int column, int livingNeighbours) {
        if (livingNeighbours < 2) {
            cells[row][column] = 0;
        }
    }

    private void checkForOverpopulation(int row, int column, int livingNeighbours) {
        if (livingNeighbours > 3) {
            cells[row][column] = 0;
        }
    }

    private int getNumberOfLivingNeighboursFor(int row, int column) {
        int livingNeighbours = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isWithinGrid(row, column, i, j)) {
                    if (cells[row + i][column + j] == 1) {
                        livingNeighbours++;
                    }
                }
            }
        }
        return livingNeighbours;
    }

    private boolean isWithinGrid(int row, int column, int i, int j) {
        return row + i >= 0
                && column + j >= 0
                && row + i < cells.length
                && column + j < cells[row].length;
    }

    public void assertState(int[][] cells) {
        assert Arrays.deepEquals(this.cells, cells);
    }
}
