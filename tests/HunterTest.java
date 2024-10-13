package tests;

import Bussiness.*;
import Gui.MessageCallback;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class HunterTest {
    private Hunter h1;
    private Hunter h2;
    private GameBoard gameBoard;
    @BeforeEach
    void setUp() {
        MessageCallback message=(s)->{};
        h1=new Hunter(new Position(0,11),"Omer",100,40,60,4,message);
        h2=new Hunter(new Position(2,5),"Amit",50,30,50,5,message);
        LinkedList<Tile> tiles = new LinkedList<>();
        tiles.add(new Empty(new Position(0,1),message));
        tiles.add(new Empty(new Position(0,2),message));
        tiles.add(new Empty(new Position(0,3),message));
        tiles.add(new Empty(new Position(0,4),message));
        tiles.add(new Empty(new Position(0,5),message));
        tiles.add(new Empty(new Position(0,6),message));
        tiles.add(new Empty(new Position(0,7),message));
        tiles.add(new Empty(new Position(0,8),message));
        tiles.add(new Empty(new Position(0,9),message));
        tiles.add(new Empty(new Position(0,0),message));
        tiles.add(new Empty(new Position(0,10),message));
        tiles.add(new Empty(new Position(2,4),message));
        tiles.add(new Wall(new Position(7,6),message));
        tiles.add(new Empty(new Position(0,1),message));
        tiles.add(new Mage(new Position(5,7),"Adi",50,20,80,100,70,15,4,10,message));
        tiles.add(h1);
        tiles.add(h2);
        LinkedList<Tile> enemies = new LinkedList<>();
        enemies.add(new Monster('a',new Position(1,2),"AA",10,100000000,1,5,10,message));
        enemies.add(new Trap('q',new Position(10,12),"QQ",10,80,20,30,6,12,message));
        enemies.add(new Trap('y',new Position(0,12),"QE",3,79,19,29,5,11,message));
        tiles.add(enemies.get(0));
        tiles.add(enemies.get(1));
        tiles.add(enemies.get(2));
        gameBoard=new GameBoard(tiles,enemies,h1);
    }
    @Test
    void levelUpArrowsCount() {
        int prevArrowsCount=h1.getArrowsCount();
        h1.levelUp();
        int currArrowsCount=h1.getArrowsCount();
        Assert.assertTrue(currArrowsCount==prevArrowsCount+10*h1.getLevel());
    }

    @Test
    void levelUpAttack() {
        int prevAttack=h1.getAttack();
        h1.levelUp();
        int currAttack=h1.getAttack();
        Assert.assertTrue(currAttack==prevAttack+6*h1.getLevel());
    }

    @Test
    void levelUpDefense() {
        int prevDefense=h1.getDefense();
        h1.levelUp();
        int currDefense=h1.getDefense();
        Assert.assertTrue(currDefense==prevDefense+ 2*h1.getLevel());
    }

    @Test
    void playerTurnTicksCount1() {
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        Assert.assertTrue(h1.getTicksCount()==0);
    }

    @Test
    void playerTurnTicksCount2() {
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        Assert.assertTrue(h1.getTicksCount()==2);
    }

    @Test
    void playerTurnArrowsCount1() {
        int prevArrowsCount= h1.getArrowsCount();
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        int currArrowsCount= h1.getArrowsCount();
        Assert.assertTrue(currArrowsCount==prevArrowsCount+ h1.getLevel());
    }

    @Test
    void playerTurnArrowsCount2() {
        int prevArrowsCount= h1.getArrowsCount();
        h1.playerTurn(gameBoard,'w');
        h1.playerTurn(gameBoard,'w');
        int currArrowsCount= h1.getArrowsCount();
        Assert.assertTrue(currArrowsCount==prevArrowsCount);
    }

    @Test
    void castAbilityArrowsCount() {
        int prevArrowsCount=h2.getArrowsCount();
        h2.castAbility(gameBoard);
        h2.castAbility(gameBoard);
        h2.castAbility(gameBoard);
        h2.castAbility(gameBoard);
        h2.castAbility(gameBoard);
        h2.castAbility(gameBoard);
        h2.castAbility(gameBoard);
        h2.castAbility(gameBoard);
        h2.castAbility(gameBoard);
        h2.castAbility(gameBoard);
        int currArrowsCount=h2.getArrowsCount();
        Assert.assertTrue(h2.getArrowsCount()==0);
    }

    @Test
    void castAbilityBattle(){
        int prevNumOfEnemies=gameBoard.getEnemies().size();
        h1.castAbility(gameBoard);
        int currNumOfEnemies=gameBoard.getEnemies().size();
        Assert.assertTrue(currNumOfEnemies<prevNumOfEnemies);
    }
}