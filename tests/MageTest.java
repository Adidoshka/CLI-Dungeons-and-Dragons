package tests;

import Bussiness.*;
import Gui.MessageCallback;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class MageTest {
    private Mage m1;
    private Mage m2;
    private GameBoard gameBoard;
    @BeforeEach
    void setUp() {
        MessageCallback message=(s)->{};
        m1=new Mage(new Position(2,1),"Gal",10,100000,1,30,5,10,2,3,message);
        m2=new Mage(new Position(3,4),"Martin",15,100,100,4,2,8,2,5,message);
        LinkedList<Tile> tiles = new LinkedList<>();
        tiles.add(new Empty(new Position(2,4),message));
        tiles.add(new Wall(new Position(7,6),message));
        tiles.add(new Empty(new Position(3,1),message));
        tiles.add(new Rogue(new Position(1,3),"Adi",40,50,12,3,message));
        tiles.add(new Warrior(new Position(0,0),"Omer",100,40,60,4,message));
        tiles.add(m1);
        tiles.add(m2);
        LinkedList<Tile> enemies = new LinkedList<>();
        enemies.add(new Monster('a',new Position(1,2),"AA",2,1000000,1,5,10,message));
        enemies.add(new Trap('q',new Position(10,12),"QQ",10,80,20,30,6,12,message));
        enemies.add(new Trap('y',new Position(1,6),"QE",9,79,19,29,5,11,message));
        tiles.add(enemies.get(0));
        tiles.add(enemies.get(1));
        tiles.add(enemies.get(2));
        gameBoard=new GameBoard(tiles,enemies,m1);
    }

    @Test
    void levelUpManaPool() {
        int prevManaPool=m1.getManaPool();
        m1.levelUp();
        int currManaPool=m1.getManaPool();
        Assert.assertTrue(currManaPool==prevManaPool+25* m1.getLevel());
    }

    @Test
    void levelUpCurrentMana() {
        int prevMana=m1.getCurrentMana();
        m1.levelUp();
        int currMana=m1.getCurrentMana();
        Assert.assertTrue(currMana==Math.min(prevMana + (m1.getManaPool() / 4), m1.getManaPool()));
    }

    @Test
    void levelUpSpellPower() {
        int prevSpellPower=m1.getSpellPower();
        m1.levelUp();
        int currSpellPower=m1.getSpellPower();
        Assert.assertTrue((currSpellPower==prevSpellPower+10*m1.getLevel()));
    }

    @Test
    void playerTurnCurrentMana() {
        int prevMana=m1.getCurrentMana();
        m1.playerTurn(gameBoard,'d');
        int currMana=m1.getCurrentMana();
        Assert.assertTrue(currMana==Math.min(m1.getManaPool(), prevMana + m1.getLevel()));
    }

    @Test
    void playerTurnRight() {
        int prevX=m1.getPosition().getX();
        int prevY=m1.getPosition().getY();
        m1.playerTurn(gameBoard,'d');
        Assert.assertTrue(m1.getPosition().getX()==prevX+1 && m1.getPosition().getY()==prevY);;
    }

    @Test
    void castAbilityCurrentMana1() {
        int prevMana=m1.getCurrentMana();
        m1.castAbility(gameBoard);
        int currMana=m1.getCurrentMana();
        Assert.assertTrue(currMana==prevMana-m1.getManaCost());
    }

    @Test
    void castAbilityCurrentMana2() {
        int prevMana=m2.getCurrentMana();
        int prevCost=m2.getManaCost();
        m1.castAbility(gameBoard);
        Assert.assertTrue(prevMana<prevCost);
    }

    @Test
    void castAbility() {
        int prevNumOfEnemies=gameBoard.getEnemies().size();
        m1.castAbility(gameBoard);
        int currNumOfEnemies=gameBoard.getEnemies().size();
        Assert.assertTrue(currNumOfEnemies<prevNumOfEnemies);
    }
}