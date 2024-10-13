package Bussiness;

import Bussiness.Steps.*;
import Gui.MessageCallback;

import java.util.HashMap;

public abstract class Player extends Unit implements HeroicUnit {
    protected int experience;
    protected int level;

    protected Player(Position position, String name, int healthPool, int attack, int defense, MessageCallback message) {
        super('@', position, name, healthPool, attack, defense, message);
        this.experience = 0;
        this.level = 1;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int toAdd) {
        experience = experience + toAdd;
        if (experience >= 50 * level) {
            levelUp();
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;}

    public void playerTurn(GameBoard gameBoard, char c) {
        HashMap<Character, Step> steps = new HashMap<>();
        steps.put('w', new Up());
        steps.put('s', new Down());
        steps.put('a', new Left());
        steps.put('d', new Right());
        steps.put('e', new CastSpecialAbility());
        steps.put('q', new DoNothing());
        steps.get(c).move(gameBoard, this);
    }

    public void levelUp() {
        experience = experience - (50 * level);
        level = level + 1;
        healthPool = healthPool + (10 * level);
        healthAmount = healthPool;
        attack = attack + (4 * level);
        defense = defense + level;
        message.send(name+" reached level "+level+": +"+healthAmount+" Health, +"+attack+" Attack, +"+defense+" Defense");
    }

    @Override
    public void battle(GameBoard gameBoard, Unit unit) {
        unit.battlePlayer(gameBoard, this, false);
    }

    public void battlePlayer(GameBoard gameBoard, Player player, boolean isSpecialAbility) {}

    public void battleEnemy(GameBoard gameBoard, Enemy enemy, boolean isSpecialAbility) {
        int attackRoll;
        if (isSpecialAbility) attackRoll = enemy.specialAbilityAttack();
        else attackRoll = enemy.attack();
        int defendRoll = defend();
        message.send(enemy.getName() + " engaged in combat with " + this.getName() + ".\n" + enemy.describe() + "\n" + this.describe() + "\n" + enemy.getName() + " rolled " + attackRoll + " attack points.\n" + this.getName() + " rolled " + defendRoll + " defense points. \n" + enemy.getName() + " dealt " + Math.max(attackRoll - defendRoll, 0) + " damage to " + this.getName());
        if (attackRoll - defendRoll > 0) healthAmount = healthAmount - (attackRoll - defendRoll);
        if (healthAmount <= 0) {
            this.tile = 'X';
            message.send("You Lost.");
            this.setHealthAmount(0);
            gameBoard.setPlayerLifeStatus(false);
        }
    }

    @Override
    public void enemyTurn(GameBoard gameBoard) {}

    @Override
    public void castSpecialAbility(GameBoard gameBoard){castAbility(gameBoard);}

    public abstract void castAbility(GameBoard gameBoard);

    public abstract int specialAbilityAttack();

    public abstract String describe();
}
