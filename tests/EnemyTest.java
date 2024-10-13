package tests;

import Bussiness.*;
import Gui.MessageCallback;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class EnemyTest {
    GameBoard gameBoard;
    Mage m1;
    Warrior w1;
    Monster m2;
    Monster m3;
    Trap t1;
    @BeforeEach
    void setUp() {
        MessageCallback message=(s)->{};
        m1=new Mage(new Position(2,1),"Gal",10,1000,1,30,5,10,2,3,message);
        w1=new Warrior(new Position(2,5),"Amit",50,30,50,5,message);
        m2=new Monster('n',new Position(3,1),"Pizza",5,6000000,1,5,10,message);
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
        m3=new Monster('a',new Position(2,2),"AA",1000,10,1,5,10,message);
        enemies.add(m3);
        enemies.add(t1);
        tiles.add(m3);
        tiles.add(t1);
        enemies.add(new Trap('y',new Position(1,6),"QE",9,79,19,29,5,11,message));
        tiles.add(enemies.get(3));
        gameBoard=new GameBoard(tiles,enemies,m1);
    }

    @Test
    void testBattleEnemy() {
        int prevHealth=m2.getHealthAmount();
        m2.battle(gameBoard,t1);
        int currHealth=m2.getHealthAmount();
        Assert.assertTrue(prevHealth==currHealth);
    }

    @Test
    void testBattlePlayer() {
        m2.battle(gameBoard,w1);
        Assert.assertTrue(gameBoard.getPlayerLifeStatus()==false);
    }

    @Test
    void testBattlePlayerFunctionKillEnemy() {
        int prevNumOfEnemies=gameBoard.getEnemies().size();
        m2.battlePlayer(gameBoard,m1,false);
        int currNumOfEnemies=gameBoard.getEnemies().size();
        Assert.assertTrue(currNumOfEnemies<prevNumOfEnemies);
    }

    @Test
    void testBattlePlayerFunctionNotKillEnemy() {
        int prevNumOfEnemies=gameBoard.getEnemies().size();
        m3.battlePlayer(gameBoard,m1,false);
        int currNumOfEnemies=gameBoard.getEnemies().size();
        Assert.assertTrue(currNumOfEnemies==prevNumOfEnemies);
    }


}