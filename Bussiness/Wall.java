package Bussiness;

import Gui.MessageCallback;

public class Wall extends Tile {
    public Wall(Position p, MessageCallback message) {
        super('#', p, message);
    }

    public Tile cloneTile(Position newPosition) {
        return new Wall(newPosition, message);
    }

    @Override
    public void battle(GameBoard gameBoard, Unit unit) {}

    @Override
    public void battlePlayer(GameBoard gameBoard, Player player, boolean isSpecialAbility) {}

    @Override
    public void battleEnemy(GameBoard gameBoard, Enemy enemy, boolean isSpecialAbility) {}

    @Override
    public void visit(GameBoard gameBoard, Tile other) {}

    @Override
    public void accept(GameBoard gameBoard, Unit unit) {}

    @Override
    public void playerTurn(GameBoard gameBoard, char c) {}

    @Override
    public void enemyTurn(GameBoard gameBoard) {}

    @Override
    public void castSpecialAbility(GameBoard gameBoard) {}

    @Override
    public String describe() {
        return null;
    }
}