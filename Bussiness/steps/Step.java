package Bussiness.Steps;

import Bussiness.GameBoard;
import Bussiness.Tile;

public interface Step {
     void move(GameBoard gameBoard, Tile tile);
}
