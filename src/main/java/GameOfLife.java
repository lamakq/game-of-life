public class GameOfLife {

    private static final int LIVING_CELL = 1;
    private static final int DEAD_CELL = 0;
    private static final int NUMBER_OF_NEIGHBOURS_TO_BRING_BACK_TO_LIFE = 3;
    private static final int NUMBER_OF_NEIGHBOURS_TO_KILL_DUE_TO_UNDERPOPULATION = 2;
    private static final int NUMBER_OF_NEIGHBOURS_TO_KILL_DUE_TO_OVERPOPULATION = 3;

    private Cells cells;

    public GameOfLife(int cells[][]) {
        this.cells = new Cells(cells);
    }

    public void tick() {

        Cells cellsAfterTick = this.cells.copy();

        for (int row = 0; row < cells.getRows(); row++) {
            for (int column = 0; column < cells.getColumns(); column++) {
                livingCellChecks(cellsAfterTick, row, column);
                deadCellChecks(cellsAfterTick, row, column);
            }
        }

        this.cells = cellsAfterTick;
    }

    private void deadCellChecks(Cells cellsAfterTick, int row, int column) {
        if (cells.at(row, column, DEAD_CELL)) {
            checkForBringingBackToLife(cellsAfterTick, row, column);
        }
    }

    private void livingCellChecks(Cells cellsAfterTick, int row, int column) {
        if (cells.at(row, column, LIVING_CELL)) {
            checkForUnderpopulation(cellsAfterTick, row, column);
            checkForOverpopulation(cellsAfterTick, row, column);
        }
    }

    private void checkForBringingBackToLife(Cells cellsAfterTick, int row, int column) {
        if (livingNeighboursAround(row, column)
                == NUMBER_OF_NEIGHBOURS_TO_BRING_BACK_TO_LIFE) {
            bringCellToLife(cellsAfterTick, row, column);
        }
    }

    private int livingNeighboursAround(int row, int column) {
        return cells.countNeighboursMatching(row, column, LIVING_CELL);
    }

    private void bringCellToLife(Cells cellsAfterTick, int row, int column) {
        cellsAfterTick.set(row, column, LIVING_CELL);
    }

    private void checkForUnderpopulation(Cells cellsAfterTick, int row, int column) {
        if (livingNeighboursAround(row, column)
                < NUMBER_OF_NEIGHBOURS_TO_KILL_DUE_TO_UNDERPOPULATION) {
            killCell(cellsAfterTick, new Coordinate(row, column));
        }
    }

    private void killCell(Cells cellsAfterTick, Coordinate coordinate) {
        cellsAfterTick.set(coordinate.getRow(), coordinate.getColumn(), DEAD_CELL);
    }

    private void checkForOverpopulation(Cells cellsAfterTick, int row, int column) {
        if (livingNeighboursAround(row, column)
                > NUMBER_OF_NEIGHBOURS_TO_KILL_DUE_TO_OVERPOPULATION) {
            killCell(cellsAfterTick, new Coordinate(row, column));
        }
    }

    public void assertState(int[][] cells) {
        this.cells.assertState(cells);
    }
}
