import java.util.Arrays;

public class GameOfLife {

    public static final int LIVING_CELL = 1;
    public static final int DEAD_CELL = 0;
    public static final int NUMBER_OF_NEIGHBOURS_TO_BRING_BACK_TO_LIFE = 3;
    public static final int NUMBER_OF_NEIGHBOURS_TO_KILL_DUE_TO_UNDERPOPULATION = 2;
    public static final int NUMBER_OF_NEIGHBOURS_TO_KILL_DUE_TO_OVERPOPULATION = 3;

    private final Cells cells;

    public GameOfLife(int cells[][]) {
        this.cells = new Cells(createCopyOf(cells));
    }

    public void tick() {

        Cells copyOfCells = this.cells.copy();

        for (int row = 0; row < cells.getRows(); row++) {
            for (int column = 0; column < cells.getColumns(); column++) {
                int livingNeighbours = copyOfCells.countNeighboursMatching(row, column, LIVING_CELL);
                livingCellChecks(copyOfCells, row, column, livingNeighbours);
                deadCellChecks(copyOfCells, row, column, livingNeighbours);
            }
        }
    }

    private void deadCellChecks(Cells cells, int row, int column, int livingNeighbours) {
        if (cells.at(row, column, DEAD_CELL)) {
            checkForBringingBackToLife(row, column, livingNeighbours);
        }
    }

    private void livingCellChecks(Cells cells, int row, int column, int livingNeighbours) {
        if (cells.at(row, column, LIVING_CELL)) {
            checkForUnderpopulation(row, column, livingNeighbours);
            checkForOverpopulation(this.getCells(), row, column, livingNeighbours);
        }
    }

    private int[][] createCopyOf(int[][] cells) {
        return Arrays.stream(cells).map(int[]::clone).toArray(int[][]::new);
    }

    private void checkForBringingBackToLife(int row, int column,
            int livingNeighbours) {
        if (livingNeighbours == NUMBER_OF_NEIGHBOURS_TO_BRING_BACK_TO_LIFE) {
            reviveCell(row, column);
        }
    }

    private void reviveCell(int row, int column) {
        this.getCells()[row][column] = LIVING_CELL;
        this.cells.reviveCell(row, column);
    }

    private void checkForUnderpopulation(int row, int column,
            int livingNeighbours) {
        if (livingNeighbours < NUMBER_OF_NEIGHBOURS_TO_KILL_DUE_TO_UNDERPOPULATION) {
            killCell(row, column);
        }
    }

    private void checkForOverpopulation(int[][] cells, int row, int column,
            int livingNeighbours) {
        if (livingNeighbours > NUMBER_OF_NEIGHBOURS_TO_KILL_DUE_TO_OVERPOPULATION) {
            killCell(row, column);
        }
    }

    private void killCell(int row, int column) {
        this.getCells()[row][column] = DEAD_CELL;
        this.cells.killCell(row, column);
    }

    private int getNumberOfLivingNeighboursFor(int[][] cells, int row, int column) {
        int livingNeighbours = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isNeighbour(i, j) &&
                        isWithinGrid(row, column, i, j)) {
                    if (cells[row + i][column + j] == LIVING_CELL) {
                        livingNeighbours++;
                    }
                }
            }
        }
        return livingNeighbours;
    }

    private boolean isNeighbour(int i, int j) {
        return i != 0 || j != 0;
    }

    private boolean isWithinGrid(int row, int column, int i, int j) {
        return row + i >= 0
                && column + j >= 0
                && row + i < cells.getRows()
                && column + j < cells.getColumns();
    }

    public void assertState(int[][] cells) {
        assert Arrays.deepEquals(this.getCells(), cells);
    }

    public int[][] getCells() {
        return cells.getCells();
    }
}
