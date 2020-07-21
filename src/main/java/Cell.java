public abstract class Cell {

    int value;

    public static final Cell DEAD = new DeadCell();
    public static final Cell ALIVE = new LivingCell();

    Cell(int value) {
        this.value = value;
    }

    public boolean isAlive() {
        return true;
    }

    public final boolean isDead() {
        return !isAlive();
    }

    public static Cell create(int value) {
        if (value == 0) {
            return new DeadCell();
        }
        return new LivingCell();
    }

    public boolean is(int value) {
        return this.value == value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cell cell = (Cell) o;

        return value == cell.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}