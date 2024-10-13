package tests;

import Bussiness.*;
import Gui.MessageCallback;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class PlayerTest {

    GameBoard gameBoard;
    Mage m1;
    Warrior w1;
    Monster m2;
    Monster m3;
    Trap t1;
    @BeforeEach
    void setUp() {
        MessageCallback message=(s)->{};
        m1=new Mage(new Position(2,1),"Gal",10,100000,1,30,5,10,2,3,message);
        w1=new Warrior(new Position(2,5),"Amit",50,30,50,5,message);
        m2=new Monster('n',new Position(3,1),"Pizza",2,1000000,1,5,10,message);
        LinkedList<Tile> tiles = new LinkedList<>();
        tiles.add(new Empty(new Position(2,4),message));
        tiles.add(new Empty(new Position(1,1),message));
        tiles.add(new Empty(new Position(2,2),message));
        tiles.add(new Wall(new Position(7,6),message));
        tiles.add(new Empty(new Position(3,1),message));
        tiles.add(new Rogue(new Position(1,3),"Adi",40,50,12,3,message));
        tiles.add(new Warrior(new Position(0,0),"Omer",100,40,60,4,message));
        tiles.add(m1);
        tiles.add(w1);
        tiles.add(m2);
        LinkedList<Tile> enemies = new LinkedList<>();
        enemies.add(m2);
        t1= new Trap('q',new Position(1,2),"QQ",3,2000000,20,30,6,12,message);
        m3=new Monster('a',new Position(2,2),"AA",40,10,1,5,10,message);
        enemies.add(m3);
        enemies.add(t1);
        enemies.add(new Trap('y',new Position(1,6),"QE",9,79,19,29,5,11,message));
        tiles.add(enemies.get(3));
        gameBoard=new GameBoard(tiles,enemies,m1);
    }

    @Test
    void testPlayerTurnDown() {
        int prevX=m1.getPosition().getX();
        int prevY=m1.getPosition().getY();
        m1.playerTurn(gameBoard,'s');
        Assert.assertTrue(m1.getPosition().getX()==prevX && m1.getPosition().getY()==prevY+1);
    }

    @Test
    void testPlayerTurnLeft() {
        int prevX=m1.getPosition().getX();
        int prevY=m1.getPosition().getY();
        m1.playerTurn(gameBoard,'a');
        Assert.assertTrue(m1.getPosition().getX()==prevX-1 && m1.getPosition().getY()==prevY);
    }

    @Test
    void testPlayerTurnDoNothing() {
        int prevX=m1.getPosition().getX();
        int prevY=m1.getPosition().getY();
        m1.playerTurn(gameBoard,'q');
        Assert.assertTrue(m1.getPosition().getX()==prevX && m1.getPosition().getY()==prevY);
    }

    @org.junit.jupiter.api.Test
    void playerTurnCastAbility() {
        int prevNumOfEnemies=gameBoard.getEnemies().size();
        m1.playerTurn(gameBoard,'e');
        int currNumOfEnemies=gameBoard.getEnemies().size();
        Assert.assertTrue(currNumOfEnemies<prevNumOfEnemies);
    }

    @Test
    void testLevelUpExperience() {
        int prevExperience=m1.getExperience();
        int prevLevel=m1.getLevel();
        m1.levelUp();
        int currExperience=m1.getExperience();
        Assert.assertTrue(currExperience==prevExperience-50*prevLevel);
    }

    @Test
    void testLevelUpLevel() {
        int prevLevel=m1.getLevel();
        m1.levelUp();
        int currLevel=m1.getLevel();
        Assert.assertTrue(currLevel==prevLevel+1);
    }

    @Test
    void testLevelHealthPool() {
        int prevHealthPool=m1.getHealthPool();
        m1.levelUp();
        int currHealthPool= m1.getHealthPool();
        Assert.assertTrue(currHealthPool==prevHealthPool+10* m1.getLevel());
    }

    @Test
    void testLevelHealthAmount() {
        m1.levelUp();
        Assert.assertTrue(m1.getHealthAmount()== m1.getHealthPool());
    }

    @Test
    void testLevelAttack() {
        int prevAttack=m1.getAttack();
        m1.levelUp();
        int currAttack= m1.getAttack();
        Assert.assertTrue(currAttack==prevAttack+4* m1.getLevel());
    }

    @Test
    void testLevelDefense() {
        int prevDefense=m1.getDefense();
        m1.levelUp();
        int currDefense= m1.getDefense();
        Assert.assertTrue(currDefense==prevDefense+m1.getLevel());
    }

    @Test
    void testBattleAgainstPlayer() {
        int prevHealth=w1.getHealthAmount();
        m1.battle(gameBoard,w1);
        int currHealth=w1.getHealthAmount();
        Assert.assertTrue(prevHealth==currHealth);
    }

    @Test
    void testBattleAgainstEnemy() {
        int prevNumOfEnemies=gameBoard.getEnemies().size();
        m1.battle(gameBoard,m2);
        int currNumOfEnemies=gameBoard.getEnemies().size();
        Assert.assertTrue(currNumOfEnemies<prevNumOfEnemies);
    }

    @Test
    void testBattleEnemyWithKillPlayer() {
        m1.battleEnemy(gameBoard,t1,false);
        Assert.assertTrue(gameBoard.getPlayerLifeStatus()==false);
    }

    @Test
    void testBattleEnemyWithLivePlayer() {
        m1.battleEnemy(gameBoard,m3,false);
        Assert.assertTrue(gameBoard.getPlayerLifeStatus()==true);
    }

}