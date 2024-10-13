package tests;

import Bussiness.*;
import Gui.MessageCallback;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class TrapTest {
    Trap t1;
    Trap t2;
    GameBoard gameBoard;
    @BeforeEach
    void setUp() {
        MessageCallback message=(s)->{};
        t1 = new Trap('q',new Position(1,2),"QQ",10,80000,20,30,1,1,message);
        t2 = new Trap('t',new Position(1,2),"QR",10,80,20,30,1,4,message);
        Rogue r1=new Rogue(new Position(2,3),"Rachel",2,7,300,12,message);
        LinkedList<Tile> tiles = new LinkedList<>();
        tiles.add(new Empty(new Position(2,4),message));
        tiles.add(new Wall(new Position(7,6),message));
        tiles.add(new Empty(new Position(0,1),message));
        tiles.add(new Mage(new Position(1,3),"Adi",50,20,80,100,70,15,4,10,message));
        tiles.add(new Warrior(new Position(0,0),"Omer",100,40,60,4,message));
        tiles.add(t1);
        tiles.add(t2);
        tiles.add(r1);
        LinkedList<Tile> enemies = new LinkedList<>();
        enemies.add(new Trap('q',new Position(10,12),"QQ",10,80,20,30,6,12,message));
        enemies.add(new Trap('q',new Position(1,1),"QE",9,79,19,29,5,11,message));
        enemies.add(t1);
        enemies.add(t2);
        tiles.add(enemies.get(0));
        tiles.add(enemies.get(1));
        gameBoard=new GameBoard(tiles,enemies,r1);
    }

    @Test
    void enemyTurnTickCounts1() {
        int prevTicksCount=t1.getTicksCount();
        t1.enemyTurn(gameBoard);
        int currTicksCount=t1.getTicksCount();
        Assert.assertTrue(currTicksCount==prevTicksCount+1);
    }

    @Test
    void enemyTurnTickCounts2() {
        t1.enemyTurn(gameBoard);
        t1.enemyTurn(gameBoard);
        t1.enemyTurn(gameBoard);
        int currTicksCount=t1.getTicksCount();
        Assert.assertTrue(currTicksCount==0);
    }

    @Test
    void enemyTurnTickVisibility() {
        char prevTile=t2.getTile();
        t2.enemyTurn(gameBoard);
        t2.enemyTurn(gameBoard);
        t2.enemyTurn(gameBoard);
        char currTile=t2.getTile();
        Assert.assertTrue(prevTile!=currTile);
    }

    @Test
    void enemyTurn() {
        t1.enemyTurn(gameBoard);
        Assert.assertTrue(gameBoard.getPlayerLifeStatus()==false);
    }
}