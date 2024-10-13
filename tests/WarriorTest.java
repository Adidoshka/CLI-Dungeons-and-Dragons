package tests;

import Bussiness.*;
import Gui.MessageCallback;
import org.junit.Assert;

import java.util.LinkedList;

class WarriorTest {
    private Warrior w1;
    private Warrior w2;
    private GameBoard gameBoard;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        MessageCallback message=(s)->{};
        w1=new Warrior(new Position(0,0),"Omer",100,40,60,4,message);
        w2=new Warrior(new Position(2,5),"Amit",50,30,50,5,message);
        LinkedList<Tile> tiles = new LinkedList<>();
        tiles.add(new Empty(new Position(2,4),message));
        tiles.add(new Wall(new Position(7,6),message));
        tiles.add(new Empty(new Position(0,1),message));
        tiles.add(new Mage(new Position(5,7),"Adi",50,20,80,100,70,15,4,10,message));
        tiles.add(w1);
        tiles.add(w2);
        LinkedList<Tile> enemies = new LinkedList<>();
        enemies.add(new Monster('a',new Position(0,1),"AA",3,100000000,1,5,10,message));
        enemies.add(new Trap('q',new Position(10,12),"QQ",10,80,20,30,6,12,message));
        enemies.add(new Trap('t',new Position(1,1),"QT",2,80,20,30,6,12,message));
        enemies.add(new Trap('y',new Position(1,7),"QE",9,79,19,29,5,11,message));
        tiles.add(enemies.get(0));
        tiles.add(enemies.get(1));
        tiles.add(enemies.get(2));
        tiles.add(enemies.get(3));
        gameBoard=new GameBoard(tiles,enemies,w1);
    }

    @org.junit.jupiter.api.Test
    void levelUpRemainCoolDown() {
        w1.levelUp();
        int remainCoolDown=w1.getRemainCoolDown();
        Assert.assertTrue(remainCoolDown==0);
    }

    @org.junit.jupiter.api.Test
    void levelUpExperience() {
        int prevExperience=w1.getExperience();
        int prevLevel= w1.getLevel();
        w1.levelUp();
        int currExperience=w1.getExperience();
        Assert.assertTrue(currExperience==prevExperience-50* prevLevel);
    }

    @org.junit.jupiter.api.Test
    void levelUpHealthPool() {
        int prevHealthPool=w1.getHealthPool();
        w1.levelUp();
        int currHealthPool= w1.getHealthPool();
        Assert.assertTrue(currHealthPool==prevHealthPool+15*w1.getLevel());
    }

    @org.junit.jupiter.api.Test
    void levelUpAttack() {
        int prevAttack=w1.getAttack();
        w1.levelUp();
        int currAttack= w1.getAttack();
        Assert.assertTrue(currAttack==prevAttack+6*w1.getLevel());
    }

    @org.junit.jupiter.api.Test
    void levelUpDefense() {
        int prevDefense=w1.getDefense();
        w1.levelUp();
        int currDefense=w1.getDefense();
        Assert.assertTrue(currDefense==prevDefense+2*w1.getLevel());
    }

    @org.junit.jupiter.api.Test
    void playerTurnRemainCoolDown() {
        int prevRemainCoolDown=w1.getRemainCoolDown();
        w1.playerTurn(gameBoard,'q');
        Assert.assertTrue(w1.getRemainCoolDown()==Math.max(0, prevRemainCoolDown - 1));
    }

    @org.junit.jupiter.api.Test
    void playerTurnCastAbilityRemainCoolDown() {
        int prevHealthAmount=w2.getHealthAmount();
        w2.castAbility(gameBoard);
        int currHealthAmount= w2.getHealthAmount();
        Assert.assertTrue(prevHealthAmount==currHealthAmount);
    }

    @org.junit.jupiter.api.Test
    void castAbilityHealthAmount() {
        int prevHealthAmount=w1.getHealthAmount();
        w1.castAbility(gameBoard);
        int currHealthAmount=w1.getHealthAmount();
        Assert.assertTrue(currHealthAmount==Math.min(prevHealthAmount + (10 * w1.getDefense()), w1.getHealthPool()));
    }

    @org.junit.jupiter.api.Test
    void castAbilityBattle() {
        int prevNumOfEnemies=gameBoard.getEnemies().size();
        w1.castAbility(gameBoard);
        int currNumOfEnemies=gameBoard.getEnemies().size();
        Assert.assertTrue(currNumOfEnemies<prevNumOfEnemies);
    }
}