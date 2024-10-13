package tests;

import Bussiness.*;
import Gui.MessageCallback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class EmptyTest {
    Warrior w1;
    Monster m1;
    Empty e1;
    GameBoard gameBoard;
    @BeforeEach
    void setUp() {
        MessageCallback message=(s)->{};
        e1=new Empty(new Position(0,1),message);
        m1= new Monster('a',new Position(1,2),"AAB",10,100000000,1,5,10,message);
        w1=new Warrior(new Position(0,0),"Omer",100,40,60,4,message);
        LinkedList<Tile> tiles = new LinkedList<>();
        tiles.add(new Empty(new Position(2,4),message));
        tiles.add(new Wall(new Position(7,6),message));
        tiles.add(new Empty(new Position(0,1),message));
        tiles.add(new Mage(new Position(5,7),"Adi",50,20,80,100,70,15,4,10,message));
        tiles.add(e1);
        tiles.add(m1);
        tiles.add(w1);
        LinkedList<Tile> enemies = new LinkedList<>();
        enemies.add(new Monster('a',new Position(1,2),"AA",10,100000000,1,5,10,message));
        enemies.add(new Trap('q',new Position(10,12),"QQ",10,80,20,30,6,12,message));
        enemies.add(new Trap('q',new Position(1,1),"QE",9,79,19,29,5,11,message));
        tiles.add(enemies.get(0));
        tiles.add(enemies.get(1));
        tiles.add(enemies.get(2));
        gameBoard=new GameBoard(tiles,enemies,w1);
    }

    @Test
    void acceptWarrior() {
        e1.accept(gameBoard,w1);
    }

    @Test
    void acceptEnemy() {
        e1.accept(gameBoard,m1);
    }
}