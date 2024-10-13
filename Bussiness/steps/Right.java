package Bussiness.Steps;

import Bussiness.GameBoard;
import Bussiness.Tile;

public class Right implements Step {

    @Override
    public void move(GameBoard gameBoard, Tile tile) {
        Tile goToTile = gameBoard.get(tile.getPosition().getX() + 1, tile.getPosition().getY());
        tile.visit(gameBoard, goToTile);
    }
}
