package tests;

import Bussiness.*;
import Gui.MessageCallback;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class MonsterTest {
    Monster m1;
    GameBoard gameBoard;

    @BeforeEach
    void setUp() {
        MessageCallback message=(s)->{};
        m1= new Monster('a',new Position(1,2),"AA",10,100000000,1,5,10,message);
        Rogue r1=new Rogue(new Position(1,3),"Rachel",70,70,30,12,message);
        LinkedList<Tile> tiles = new LinkedList<>();
        tiles.add(new Empty(new Position(2,4),message));
        tiles.add(new Wall(new Position(7,6),message));
        tiles.add(new Empty(new Position(0,1),message));
        tiles.add(new Mage(new Position(3,6),"Adi",50,20,80,100,70,15,4,10,message));
        tiles.add(new Warrior(new Position(0,0),"Omer",100,40,60,4,message));
        tiles.add(m1);
        tiles.add(r1);
        LinkedList<Tile> enemies = new LinkedList<>();
        enemies.add(new Trap('q',new Position(10,12),"QQ",10,80,20,30,6,12,message));
        enemies.add(new Trap('q',new Position(1,1),"QE",9,79,19,29,5,11,message));
        enemies.add(m1);
        tiles.add(enemies.get(0));
        tiles.add(enemies.get(1));
        gameBoard=new GameBoard(tiles,enemies,r1);
    }

    @Test
    void enemyTurn() {
        m1.enemyTurn(gameBoard);
        Assert.assertTrue(gameBoard.getPlayerLifeStatus()==false);
    }
}