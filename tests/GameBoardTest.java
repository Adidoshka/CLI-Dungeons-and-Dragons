package tests;

import Bussiness.*;
import Gui.MessageCallback;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class GameBoardTest {
    GameBoard gameBoard;
    Mage m1;
    Monster m2;
    Trap t1;
    @BeforeEach
    void setUp() {
        MessageCallback message=(s)->{};
        m1=new Mage(new Position(2,1),"Gal",10,100,100,30,5,10,2,3,message);
        Warrior w1=new Warrior(new Position(2,5),"Amit",50,30,5000000,5,message);
        m2=new Monster('n',new Position(3,1),"Pizza",2,1000000,1,5,10,message);
        LinkedList<Tile> tiles = new LinkedList<>();
        tiles.add(m1);
        tiles.add(w1);
        tiles.add(m2);
        tiles.add(new Empty(new Position(2,4),message));
        tiles.add(new Empty(new Position(1,1),message));
        tiles.add(new Empty(new Position(2,2),message));
        tiles.add(new Wall(new Position(7,6),message));
        tiles.add(new Wall(new Position(8,6),message));
        tiles.add(new Empty(new Position(3,1),message));
        tiles.add(new Rogue(new Position(1,3),"Adi",40,50,12,3,message));
        tiles.add(new Warrior(new Position(0,0),"Omer",100,40,60,4,message));
        LinkedList<Tile> enemies = new LinkedList<>();
        enemies.add(m2);
        t1= new Trap('q',new Position(1,2),"QQ",3,2000000,20,30,6,12,message);
        Monster m3=new Monster('a',new Position(2,2),"AA",40,10,1000000,5,10,message);
        enemies.add(m3);
        tiles.add(m3);
        enemies.add(t1);
        enemies.add(new Trap('y',new Position(1,6),"QE",9,79,19,29,5,11,message));
        tiles.add(enemies.get(3));
        gameBoard=new GameBoard(tiles,enemies,m1);
    }

    @Test
    void getEnemyByRange() {
        Tile enemy=gameBoard.getEnemyByRange(m1,5);
        Assert.assertTrue(enemy.equals(m2));
    }

    @Test
    void getPlayerByRange() {
        Tile player = gameBoard.getPlayerByRange(m2,5);
        Assert.assertTrue(player.equals(m1));
    }

    @Test
    void replaceTiles() {
        int prevX=m1.getPosition().getX();
        int prevY=m1.getPosition().getY();
        gameBoard.replaceTiles(m1,gameBoard.getTiles().get(7));
        int currXEmpty=gameBoard.getTiles().get(7).getPosition().getX();
        int currYEmpty=gameBoard.getTiles().get(7).getPosition().getY();
        Assert.assertTrue(currXEmpty==prevX && currYEmpty==prevY);
    }

    @Test
    void removeEnemyWithAbility() {
        int prevSizeEnemies=gameBoard.getEnemies().size();
        gameBoard.removeEnemyWithAbility(t1);
        int currSizeEnemies=gameBoard.getEnemies().size();
        Assert.assertTrue(currSizeEnemies==prevSizeEnemies-1);
    }

    @Test
    void removeEnemyWithoutAbility() {
        int prevX=m2.getPosition().getX();
        int prevY=m2.getPosition().getY();
        gameBoard.removeEnemyWithoutAbility(m2);
        int currXPlayer=m1.getPosition().getX();
        int currYPlayer=m1.getPosition().getY();
        Assert.assertTrue(currXPlayer==prevX && currYPlayer==prevY);
    }


}