package Gui;

import Bussiness.Player;
import Bussiness.Tile;

import java.util.Iterator;
import java.util.List;

public class ViewModel {
    private MessageCallback message;

    public ViewModel() {
        message = this::printMessage;
    }

    public MessageCallback getMessage() {
        return message;
    }

    public void setMessage(MessageCallback message) {
        this.message = message;
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printGameBoard(List<Tile> tile, int length, int width) {
        Iterator<Tile> printIterator = tile.iterator();
        for (int i = 0; i < length; i = i + 1) {
            for (int j = 0; j < width; j = j + 1) {
                System.out.print(printIterator.next().toString());
            }
            System.out.println();
        }
    }

    public void printMenu(Player[] playerArray) {
        message.send("Select player:");
        for (int i = 0; i < 7; i = i + 1)
            message.send(i + 1 + ". " + playerArray[i].describe());
    }
}

