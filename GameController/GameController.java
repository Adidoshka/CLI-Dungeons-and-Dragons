package GameController;

import Bussiness.GameBoard;
import Bussiness.Position;
import Bussiness.Tile;
import Gui.ViewModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GameController {
    private ViewModel viewModel;
    private String levelsPath;
    private TileFactory tileFactory;


    public GameController(String levelsPath) {
        viewModel = new ViewModel();
        tileFactory = new TileFactory(viewModel.getMessage());
        this.levelsPath = levelsPath;
    }

    private static List<String> readPath(String path) {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String next;
            while ((next = reader.readLine()) != null) {
                lines.add(next);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + path);
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n" +
                    e.getStackTrace());
        }
        return lines;
    }

    public void startPlaying() {
        viewModel.printMenu(tileFactory.listPlayers());
        selectPlayer();
        game();
    }

    private boolean isLegalStepInput(String step) {
        if (step.length() != 1)
            return false;
        else {
            String legalSteps = "wadseq";
            if (legalSteps.indexOf(step.charAt(0)) != -1) {
                return true;
            } else
                return false;
        }
    }

    private boolean continueGame(GameBoard gameBoard) {
        if (gameBoard.getPlayerLifeStatus() && gameBoard.getEnemies().size() > 0)
            return true;
        return false;
    }

    public void game() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i < 5; i = i + 1) {
            List<String> levels = readPath(levelsPath + "\\level" + i + ".txt");
            if (levels == null) throw new IllegalArgumentException("This file is Illegal");
            GameBoard gameBoard = prepareGameBoard(levels);
            gameBoard.setTiles(gameBoard.getTiles().stream().sorted(Tile::compareTo).collect(Collectors.toCollection(LinkedList::new)));
            viewModel.printGameBoard(gameBoard.getTiles(), levels.size(), levels.get(0).length());
            viewModel.printMessage(gameBoard.getPlayer().describe());
            while (continueGame(gameBoard)) {
                String step = scanner.nextLine();
                step = step.trim();
                if (isLegalStepInput(step)) {
                    gameBoard.getPlayer().playerTurn(gameBoard, step.charAt(0));
                    enemyTurns(gameBoard);
                    if (!gameBoard.getEnemies().isEmpty()) {
                        gameBoard.setTiles(gameBoard.getTiles().stream().sorted(Tile::compareTo).collect(Collectors.toCollection(LinkedList::new)));
                        viewModel.printGameBoard(gameBoard.getTiles(), levels.size(), levels.get(0).length());
                        viewModel.printMessage(gameBoard.getPlayer().describe());
                    }
                }
            }
            if (!gameBoard.getPlayerLifeStatus()) {
                viewModel.printMessage("Game Over.");
                return;
            }
        }
    }

    private void enemyTurns(GameBoard gameBoard) {
        for (Tile enemy : gameBoard.getEnemies()) {
            enemy.enemyTurn(gameBoard);
        }
    }

    private GameBoard prepareGameBoard(List<String> levels) {
        LinkedList<Tile> enemies = new LinkedList<Tile>();
        LinkedList<Tile> tiles = new LinkedList<Tile>();
        for (int i = 0; i < levels.size(); i = i + 1) {
            for (int j = 0; j < levels.get(i).length(); j = j + 1) {
                Position position = new Position(j, i);
                Tile newTile = tileFactory.produceTile(levels.get(i).charAt(j), position);
                tiles.add(newTile);
                if (tileFactory.checkIfEnemy(newTile)) {
                    enemies.add(newTile);
                }
            }
        }

        GameBoard gameBoard = new GameBoard(tiles, enemies, tileFactory.getPlayer());
        return gameBoard;
    }

    public void selectPlayer() {
        Scanner scanner = new Scanner(System.in);
        String playerID = scanner.nextLine();
        while (!isLegalInput(playerID)) {
            System.out.println("Invalid Input");
            viewModel.printMenu(tileFactory.listPlayers());
            playerID = scanner.nextLine();
        }
        int input = Integer.parseInt(playerID);
        tileFactory.addPlayerToGame(input);
    }

    public boolean isLegalInput(String input) {
        try {
            int intValue = Integer.parseInt(input);
            if (intValue >= 1 && intValue <= 7)
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
