package Bussiness;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameBoard {

    private List<Tile> enemies;
    private Tile player;
    private List<Tile> tiles;
    private boolean playerLifeStatus;

    public GameBoard(LinkedList<Tile> tiles, LinkedList<Tile> enemies, Tile player) {
        this.tiles = tiles;
        this.enemies = enemies;
        this.player = player;
        this.playerLifeStatus = true;}

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(LinkedList<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Tile> getEnemies() {
        return enemies;
    }

    public void setEnemies(LinkedList<Tile> enemies) {
        this.enemies = enemies;
    }

    public Tile getPlayer() {
        return player;
    }

    public void setPlayer(Tile player) {
        this.player = player;
    }

    public void setPlayerLifeStatus(boolean newStatus) {
        playerLifeStatus=newStatus;
    }

    public boolean getPlayerLifeStatus() {
        return playerLifeStatus;
    }

    public Tile get(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getPosition().equals(Position.at(x, y))) {
                return tile;
            }
        }
        return null;
    }

    public Tile getEnemyByRange(Player player, int range) {
        for (Tile tile : enemies) {
            if (tile.range(player) < range) {
                return tile;
            }
        }
        return null;
    }

    public Tile getPlayerByRange(Enemy enemy, int range) {
        if (player.range(enemy) < range) {
            return player;
        }
        return null;
    }

    public List<Tile> getEnemiesByRange(Player player, int range) {
        List<Tile> rangeEnemies = new ArrayList<Tile>();
        for (Tile tile : enemies) {
            if (tile.range(player) < range) {
                rangeEnemies.add(tile);
            }
        }
        return rangeEnemies;
    }

    public void replaceTiles(Tile one, Tile two) {
        Position position = one.getPosition();
        one.setPosition(two.getPosition());
        two.setPosition(position);
    }

    public void removeEnemyWithoutAbility(Tile enemy) {
        Position p = new Position(player.getPosition().getX(), player.getPosition().getY());
        replaceTiles(enemy, player);
        tiles.add(new Empty(p, player.message));
        tiles.remove(enemy);
        enemies.remove(enemy);
    }

    public void removeEnemyWithAbility(Enemy enemy) {
        Position p = new Position(enemy.getPosition().getX(), enemy.getPosition().getY());
        tiles.add(new Empty(p, player.message));
        tiles.remove(enemy);
        enemies.remove(enemy);
    }

}

