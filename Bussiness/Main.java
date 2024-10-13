package Bussiness;

import GameController.GameController;

public class Main {
    public static void main(String[] args) {
        GameController game = new GameController(args[0]);
        game.startPlaying();

    }
}
