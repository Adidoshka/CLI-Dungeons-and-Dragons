package Bussiness;

import Gui.MessageCallback;

import java.util.Random;

public abstract class Unit extends Tile {
    protected String name;
    protected int healthPool;
    protected int healthAmount;
    protected int attack;
    protected int defense;

    protected Unit(char tile, Position position, String name, int healthPool, int attack, int defense, MessageCallback message) {
        super(tile, position, message);
        this.name = name;
        this.healthPool = healthPool;
        this.healthAmount = healthPool;
        this.attack = attack;
        this.defense = defense;
    }
    public void setHealthAmount(int healthAmount){
        this.healthAmount=healthAmount;
    }

    public String getName() {
        return name;
    }

    public void visit(GameBoard gameBoard, Tile other) {
        other.accept(gameBoard, this);
    }

    public void accept(GameBoard gameBoard, Unit unit) {
        unit.battle(gameBoard, this);
    }

    protected int attack() {
        return new Random().nextInt(attack + 1);
    }

    protected int defend() {
        return new Random().nextInt(defense + 1);
    }

    // Combat against another unit.
    public abstract void battle(GameBoard gameBoard, Unit unit);

    public abstract void battlePlayer(GameBoard gameBoard, Player player, boolean isSpecialAbility);

    public abstract void battleEnemy(GameBoard gameBoard, Enemy enemy, boolean isSpecialAbility);

    @Override
    public abstract void castSpecialAbility(GameBoard gameBoard);

    @Override
    public abstract String describe();

    public int getHealthAmount() {
        return healthAmount;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHealthPool() {
        return healthPool;
    }

    public void setHealthPool(int healthPool) {
        this.healthPool = healthPool;
    }
}
