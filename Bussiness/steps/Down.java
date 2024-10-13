package Bussiness.Steps;

import Bussiness.GameBoard;
import Bussiness.Tile;

public class Down implements Step {

    @Override
    public void move(GameBoard gameBoard, Tile tile) {
        Tile goToTile = gameBoard.get(tile.getPosition().getX(), tile.getPosition().getY() + 1);
        tile.visit(gameBoard, goToTile);
    }
}
