package tests;

import Bussiness.*;
import Gui.MessageCallback;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class RogueTest {
    private Rogue r1;
    private Rogue r2;
    private GameBoard gameBoard;

    @BeforeEach
    void setUp() {
        MessageCallback message=(s)->{};
        r1=new Rogue(new Position(3,7),"Rachel",70,70,30,80,message);
        r2=new Rogue(new Position(2,5),"Rachel2",70,70,30,120,message);
        Warrior w1=new Warrior(new Position(0,0),"Omer",100,40,60,4,message);
        LinkedList<Tile> tiles = new LinkedList<>();
        tiles.add(new Empty(new Position(3,8),message));
        tiles.add(new Wall(new Position(7,6),message));
        tiles.add(new Empty(new Position(0,1),message));
        tiles.add(new Mage(new Position(5,7),"Adi",50,20,80,100,70,15,4,10,message));
        tiles.add(r1);
        tiles.add(r2);
        tiles.add(w1);
        LinkedList<Tile> enemies = new LinkedList<>();
        enemies.add(new Monster('a',new Position(2,6),"AA",10,100000000,1,5,10,message));
        enemies.add(new Trap('q',new Position(3,5),"QQ",10,80,20,30,6,12,message));
        enemies.add(new Trap('q',new Position(1,1),"QE",9,79,19,29,5,11,message));
        tiles.add(enemies.get(0));
        tiles.add(enemies.get(1));
        tiles.add(enemies.get(2));
        gameBoard=new GameBoard(tiles,enemies,w1);
    }

    @Test
    void levelUpCurrentEnergy() {
        r1.levelUp();
        int currEnergy=r1.getCurrentEnergy();
        Assert.assertTrue(currEnergy==100);
    }

    @Test
    void levelUpAttack() {
        int prevAttack=r1.getAttack();
        r1.levelUp();
        int currAttack=r1.getAttack();
        Assert.assertTrue(currAttack==prevAttack+7*r1.getLevel());
    }

    @Test
    void playerTurnCurrentEnergy() {
        int prevEnergy=r1.getCurrentEnergy();
        r1.playerTurn(gameBoard,'s');
        int currEnergy=r1.getCurrentEnergy();
        Assert.assertTrue(currEnergy==Math.min(prevEnergy + 10, 100));
    }

    @Test
    void playerTurnDown() {
        int prevX=r1.getPosition().getX();
        int prevY=r1.getPosition().getY();
        r1.playerTurn(gameBoard,'s');
        Assert.assertTrue(r1.getPosition().getX()==prevX && r1.getPosition().getY()==prevY+1);
    }

    @Test
    void castAbilityCurrentEnergy1() {
        int prevEnergy=r1.getCurrentEnergy();
        int prevCost=r1.getCost();
        r1.castAbility(gameBoard);
        int currEnergy=r1.getCurrentEnergy();
        Assert.assertTrue(currEnergy==prevEnergy-prevCost);
    }

    @Test
    void castAbilityCurrentEnergy2() {
        int prevEnergy=r2.getCurrentEnergy();
        r2.castAbility(gameBoard);
        int currEnergy=r2.getCurrentEnergy();
        int cost=r2.getCost();
        Assert.assertTrue(currEnergy!=prevEnergy-cost);
    }

    @Test
    void castAbility() {
        int prevNumOfEnemies=gameBoard.getEnemies().size();
        r1.castAbility(gameBoard);
        int currNumOfEnemies=gameBoard.getEnemies().size();
        Assert.assertTrue(currNumOfEnemies<prevNumOfEnemies);
    }
}