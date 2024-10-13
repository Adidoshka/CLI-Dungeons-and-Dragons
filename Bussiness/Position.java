package Bussiness;

public class Position implements Comparable<Position> {
    protected int x;
    protected int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position at(int x, int y) {
        return new Position(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Position other) {
        if (this.compareTo(other) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Position other) {
        if (this.y == other.y) {
            if (this.x == other.x) {
                return 0;
            } else if (this.x > other.x) {
                return 1;
            } else {
                return -1;
            }
        } else if (this.y > other.y) {
            return 1;
        } else {
            return -1;
        }
    }

}
