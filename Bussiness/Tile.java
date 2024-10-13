package Bussiness;

import Gui.MessageCallback;

import java.util.Objects;

public abstract class Tile implements Comparable<Tile> {
    protected char tile;
    protected Position position;
    protected MessageCallback message;

    protected Tile(char tile, Position position, MessageCallback message) {
        this.position = position;
        this.tile = tile;
        this.message = message;
    }
    public char getTile() {
        return tile;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    protected double range(Tile other) {
        int yPart = this.position.y - other.position.y;
        int xPart = this.position.x - other.position.x;
        double range = Math.sqrt((yPart * yPart) + (xPart * xPart));
        return range;
    }
    public void replacePosition(Tile toReplace) {
        Position p = this.position;
        this.setPosition(toReplace.getPosition());
        toReplace.position = p;
    }
    @Override
    public int compareTo(Tile tile) {
        return getPosition().compareTo(tile.getPosition());
    }
    @Override
    public String toString() {
        return String.valueOf(tile);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile1 = (Tile) o;
        return tile == tile1.tile && Objects.equals(position, tile1.position);
    }
    public abstract Tile cloneTile(Position newPosition);
    public abstract void battle(GameBoard gameBoard, Unit unit);
    public abstract void battlePlayer(GameBoard gameBoard, Player player, boolean isSpecialAbility);
    public abstract void battleEnemy(GameBoard gameBoard, Enemy enemy, boolean isSpecialAbility);
    public abstract void visit(GameBoard gameBoard, Tile other);
    public abstract void accept(GameBoard gameBoard, Unit unit);
    public abstract void playerTurn(GameBoard gameBoard, char c);
    public abstract void enemyTurn(GameBoard gameBoard);
    public abstract void castSpecialAbility(GameBoard gameBoard);
    public abstract String describe();

}
