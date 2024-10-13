package GameController;

import Bussiness.*;
import Gui.MessageCallback;

import java.util.HashMap;


public class TileFactory {
    private Player[] playersList;
    private HashMap<Character, Tile> tilesMap;
    private Tile player;
    private MessageCallback message;

    public TileFactory(MessageCallback message) {
        this.message = message;
        playersList = initPlayers();
        tilesMap = initTiles();
    }

    private HashMap<Character, Tile> initTiles() {
        Position position = new Position(0, 0);
        HashMap<Character, Tile> tilesHashMap = new HashMap<>();
        tilesHashMap.put('.', new Empty(position, message));
        tilesHashMap.put('#', new Wall(position, message));
        tilesHashMap.put('s', new Monster('s', position, "Lannister Solider", 80, 8, 3, 25, 3, message));
        tilesHashMap.put('k', new Monster('k', position, "Lannister Knight", 200, 14, 8, 50, 4, message));
        tilesHashMap.put('q', new Monster('q', position, "Queen's Guard", 400, 20, 15, 100, 5, message));
        tilesHashMap.put('z', new Monster('z', position, "Wright", 600, 30, 15, 100, 3, message));
        tilesHashMap.put('b', new Monster('b', position, "Bear-Wright", 1000, 75, 30, 250, 4, message));
        tilesHashMap.put('g', new Monster('g', position, "Giant-Wright", 1500, 100, 40, 500, 5, message));
        tilesHashMap.put('w', new Monster('w', position, "White Walker", 2000, 150, 50, 1000, 6, message));
        tilesHashMap.put('M',new Boss('M',position,"The Mountain", 1000, 60, 25,  500, 6, 5,message));
        tilesHashMap.put('C', new Boss('C', position, "Queen Cersei", 100, 10, 10,1000, 1, 8 ,message));
        tilesHashMap.put('K', new Boss('K', position, "Night's King", 5000, 300, 150, 5000, 8, 3, message));
        tilesHashMap.put('B', new Trap('B', position, "Bonus Trap", 1, 1, 1, 250, 1, 10, message));
        tilesHashMap.put('Q', new Trap('Q', position, "Queen's Trap", 250, 50, 10, 100, 3, 10, message));
        tilesHashMap.put('D', new Trap('D', position, "Death Trap", 500, 100, 20, 250, 1, 10, message));
        return tilesHashMap;
    }

    private Player[] initPlayers() {
        Player[] players = new Player[7];
        Position position = new Position(0, 0);
        players[0] = new Warrior(position, "Jon Snow", 300, 30, 4, 3, message);
        players[1] = new Warrior(position, "The Hound", 400, 20, 6, 5, message);
        players[2] = new Mage(position, "Melisandre", 100, 5, 1, 300, 30, 15, 5, 6, message);
        players[3] = new Mage(position, "Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4, message);
        players[4] = new Rogue(position, "Arya Stark", 150, 40, 2, 20, message);
        players[5] = new Rogue(position, "Bronn", 250, 35, 3, 50, message);
        players[6] = new Hunter(position,"Ygritte", 220, 30, 2, 6,message);
        return players;
    }

    public Player[] listPlayers() {
        return playersList;
    }

    // TODO: Add additional callbacks of your choice

    public void addPlayerToGame(int idx) {
        tilesMap.put('@', playersList[idx - 1]);
        this.player = playersList[idx - 1];
        message.send("You have selected:" + "\n" + playersList[idx - 1].getName());
    }

    public Tile getPlayer() {
        return player;
    }

    public boolean checkIfEnemy(Tile tile) {
        if (tile.getTile() == '@' || tile.getTile() == '.' || tile.getTile() == '#') {
            return false;
        } else {
            return true;
        }
    }

    public Tile produceTile(char tile, Position position) {
        if (tile == '@') {
            player.setPosition(position);
            return player;
        }
        Tile getTile = tilesMap.get(tile);
        Tile clone = getTile.cloneTile(position);
        return clone;
    }
}