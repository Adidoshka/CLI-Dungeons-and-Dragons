package tests;

import Bussiness.*;
import Gui.MessageCallback;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class UnitTest {
    GameBoard gameBoard;
    Mage m1;
    Warrior w1;
    Monster m2;
    Monster m3;
    Trap t1;
    @BeforeEach
    void setUp() {
        MessageCallback message=(s)->{};
        m1=new Mage(new Position(2,1),"Gal",10,100,100,30,5,10,2,3,message);
        w1=new Warrior(new Position(2,5),"Amit",50,30,5000000,5,message);
        m2=new Monster('n',new Position(3,1),"Pizza",2,1000000,1,5,10,message);
        LinkedList<Tile> tiles = new LinkedList<>();
        tiles.add(new Empty(new Position(2,4),message));
        tiles.add(new Empty(new Position(1,1),message));
        tiles.add(new Empty(new Position(2,2),message));
        tiles.add(new Wall(new Position(7,6),message));
        tiles.add(new Wall(new Position(8,6),message));
        tiles.add(new Empty(new Position(3,1),message));
        tiles.add(new Rogue(new Position(1,3),"Adi",40,50,12,3,message));
        tiles.add(new Warrior(new Position(0,0),"Omer",100,40,60,4,message));
        tiles.add(m1);
        tiles.add(w1);
        tiles.add(m2);
        LinkedList<Tile> enemies = new LinkedList<>();
        enemies.add(m2);
        t1= new Trap('q',new Position(1,2),"QQ",3,2000000,20,30,6,12,message);
        m3=new Monster('a',new Position(2,2),"AA",40,10,1000000,5,10,message);
        enemies.add(m3);
        enemies.add(t1);
        tiles.add(m3);
        tiles.add(t1);
        enemies.add(new Trap('y',new Position(1,6),"QE",9,79,19,29,5,11,message));
        tiles.add(enemies.get(3));
        gameBoard=new GameBoard(tiles,enemies,m1);
    }

    @Test
    void visitEnemyEnemy() {
        int prevX=m2.getPosition().getX();
        int prevY=m2.getPosition().getY();
        m2.visit(gameBoard,t1);
        int currX=m2.getPosition().getX();
        int currY=m2.getPosition().getY();
        Assert.assertTrue(prevX==currX && prevY==currY);
    }

    @Test
    void visitPlayerPlayer() {
        int prevX=m1.getPosition().getX();
        int prevY=m1.getPosition().getY();
        m1.visit(gameBoard,w1);
        int currX=m1.getPosition().getX();
        int currY=m1.getPosition().getY();
        Assert.assertTrue(prevX==currX && prevY==currY);
    }

    @Test
    void visitPlayerEnemyWithBattle() {
        int prevXm2=m2.getPosition().getX();
        int prevYm2=m2.getPosition().getY();
        m1.visit(gameBoard,m2);
        int currX=m1.getPosition().getX();
        int currY=m1.getPosition().getY();
        Assert.assertTrue(currX==prevXm2 && currY==prevYm2);
    }

    @Test
    void visitEnemyPlayerWithBattle() {
        m2.visit(gameBoard,m1);
        char cM1= m1.getTile();
        Assert.assertTrue(cM1=='X');
    }

    @Test
    void visitPlayerEnemyWithoutBattle() {
        int prevX=m3.getPosition().getX();
        int prevY=m3.getPosition().getY();
        m1.visit(gameBoard,m3);
        int currX=m3.getPosition().getX();
        int currY=m3.getPosition().getY();
        Assert.assertTrue(currX==prevX && currY==prevY);
    }

    @Test
    void visitPlayerEmpty() {
        int prevX=m1.getPosition().getX();
        int prevY=m1.getPosition().getY();
        m1.visit(gameBoard,gameBoard.getTiles().get(1));
        int currX=m1.getPosition().getX();
        int currY=m1.getPosition().getY();
        Assert.assertTrue(currX==prevX-1 && currY==prevY);
    }

    @Test
    void visitPlayerWall() {
        int prevX=m1.getPosition().getX();
        int prevY=m1.getPosition().getY();
        m1.visit(gameBoard,gameBoard.getTiles().get(3));
        int currX=m1.getPosition().getX();
        int currY=m1.getPosition().getY();
        Assert.assertTrue(currX==prevX && currY==prevY);
    }

    @Test
    void visitWallWall() {
        int prevX=gameBoard.getTiles().get(4).getPosition().getX();
        int prevY=gameBoard.getTiles().get(4).getPosition().getY();
        gameBoard.getTiles().get(4).visit(gameBoard,gameBoard.getTiles().get(5));
        int currX=gameBoard.getTiles().get(4).getPosition().getX();
        int currY=gameBoard.getTiles().get(4).getPosition().getY();
        Assert.assertTrue(currX==prevX && currY==prevY);
    }

    @Test
    void visitEmptyWall() {
        int prevX=gameBoard.getTiles().get(1).getPosition().getX();
        int prevY=gameBoard.getTiles().get(1).getPosition().getY();
        gameBoard.getTiles().get(1).visit(gameBoard,gameBoard.getTiles().get(5));
        int currX=gameBoard.getTiles().get(1).getPosition().getX();
        int currY=gameBoard.getTiles().get(1).getPosition().getY();
        Assert.assertTrue(currX==prevX && currY==prevY);
    }

    @Test
    void visitEmptyEmpty() {
        int prevX=gameBoard.getTiles().get(1).getPosition().getX();
        int prevY=gameBoard.getTiles().get(1).getPosition().getY();
        gameBoard.getTiles().get(1).visit(gameBoard,gameBoard.getTiles().get(2));
        int currX=gameBoard.getTiles().get(1).getPosition().getX();
        int currY=gameBoard.getTiles().get(1).getPosition().getY();
        Assert.assertTrue(currX==prevX && currY==prevY);
    }

    @Test
    void acceptPlayerPlayer() {
        int prevX=m1.getPosition().getX();
        int prevY=m1.getPosition().getY();
        m1.accept(gameBoard,w1);
        int currX=m1.getPosition().getX();
        int currY=m1.getPosition().getY();
        Assert.assertTrue(prevX==currX && prevY==currY);
    }

    @Test
    void acceptEmptyPlayer() {
        int prevX=m1.getPosition().getX();
        int prevY=m1.getPosition().getY();
        gameBoard.getTiles().get(1).accept(gameBoard,m1);
        int currX=m1.getPosition().getX();
        int currY=m1.getPosition().getY();
        Assert.assertTrue(currX==prevX-1 && currY==prevY);
    }

    @Test
    void acceptWallPlayer() {
        int prevX=m1.getPosition().getX();
        int prevY=m1.getPosition().getY();
        gameBoard.getTiles().get(3).accept(gameBoard,m1);
        int currX=m1.getPosition().getX();
        int currY=m1.getPosition().getY();
        Assert.assertTrue(currX==prevX && currY==prevY);
    }

    @Test
    void acceptEnemyPlayerWithBattle() {
        int prevXEnemy=m2.getPosition().getX();
        int prevYEnemy=m2.getPosition().getY();
        m2.accept(gameBoard,m1);
        int currXPlayer=m1.getPosition().getX();
        int currYPlayer=m1.getPosition().getY();
        Assert.assertTrue(currXPlayer==prevXEnemy && currYPlayer==prevYEnemy);
    }

    @Test
    void acceptEnemyPlayerWithoutBattle() {
        int prevX=w1.getPosition().getX();
        int prevY=w1.getPosition().getY();
        t1.accept(gameBoard,w1);
        int currX=w1.getPosition().getX();
        int currY=w1.getPosition().getY();
        Assert.assertTrue(currX==prevX && currY==prevY);
    }

    @Test
    void acceptPlayerEnemy() {
        m1.accept(gameBoard,m2);
        char cM1= m1.getTile();
        Assert.assertTrue(cM1=='X');
    }
}