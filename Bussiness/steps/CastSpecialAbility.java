package Bussiness.Steps;

import Bussiness.GameBoard;
import Bussiness.Tile;

public class CastSpecialAbility implements Step {
    @Override
    public void move(GameBoard gameBoard, Tile tile) {
        tile.castSpecialAbility(gameBoard);
    }
}
