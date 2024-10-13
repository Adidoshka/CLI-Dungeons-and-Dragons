package Bussiness;

import Gui.MessageCallback;

import java.util.List;

public class Rogue extends Player {
    protected int cost;
    protected int currentEnergy;

    public Rogue(Position position, String name, int healthPool, int attack, int defense, int cost, MessageCallback message) {
        super(position, name, healthPool, attack, defense, message);
        this.cost = cost;
        this.currentEnergy = 100;
    }

    public Tile cloneTile(Position newPosition) {
        return new Rogue(newPosition, name, healthPool, attack, defense, cost, message);
    }

    @Override
    public void levelUp() {
        super.levelUp();
        currentEnergy = 100;
        attack = attack + (3 * level);
    }

    public int specialAbilityAttack() {
        return attack;
    }

    @Override
    public void playerTurn(GameBoard gameBoard, char c) {
        currentEnergy = Math.min(currentEnergy + 10, 100);
        super.playerTurn(gameBoard, c);
    }

    @Override
    public void castAbility(GameBoard gameBoard) {
        if (currentEnergy < cost) {
            message.send(name + "tried to cast Fan of Knives, but there was not enough energy: " + currentEnergy + "/" + cost);
        } else {
            currentEnergy = currentEnergy - cost;
            List<Tile> closeEnemies = gameBoard.getEnemiesByRange(this, 2);
            for (Tile enemy:closeEnemies) {
                enemy.battlePlayer(gameBoard, this, true);
            }
            message.send(name + "cast Fan of Knives");
        }
    }

    @Override
    public String describe() {
        return String.format("%s\t\tHealth: %d/%d\t\tAttack: %d\t\tDefense: %d\t\tLevel: %s\t\tExperience: %d/%d\t\tCost: %d\t\tCurrentEnergy: %d", getName(), healthAmount, healthPool, attack, defense, level, experience, 50 * level, cost, currentEnergy);
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
