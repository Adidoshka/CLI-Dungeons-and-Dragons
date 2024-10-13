package Bussiness;

import Gui.MessageCallback;

public class Warrior extends Player {
    protected int abilityCoolDown;
    protected int remainCoolDown;

    public Warrior(Position position, String name, int healthPool, int attack, int defense, int abilityCoolDown, MessageCallback message) {
        super(position, name, healthPool, attack, defense, message);
        this.abilityCoolDown = abilityCoolDown;
        this.remainCoolDown = 0;
    }

    public Tile cloneTile(Position newPosition) {
        return new Warrior(newPosition, name, healthPool, attack, defense, abilityCoolDown, message);
    }

    public int specialAbilityAttack() {
        int attackAmount = (int) (0.1 * healthPool);
        return attackAmount;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        remainCoolDown = 0;
        healthPool = healthPool + (5 * level);
        attack = attack + (2 * level);
        defense = defense + level;
    }

    @Override
    public void playerTurn(GameBoard gameBoard, char c) {
        remainCoolDown = Math.max(0, remainCoolDown - 1);
        super.playerTurn(gameBoard, c);
    }

    @Override
    public void castAbility(GameBoard gameBoard) {
        if (remainCoolDown > 0) {
            message.send(name + " tried to cast Avenger's Shield but there was a cooldown: " + remainCoolDown + ".");
        } else {
            int heal = Math.min(healthAmount + (10 * defense), healthPool);
            int beforeHealing = healthAmount;
            healthAmount = heal;
            remainCoolDown = abilityCoolDown;
            Tile closeEnemy = gameBoard.getEnemyByRange(this, 3);
            if (closeEnemy != null) {
                closeEnemy.battlePlayer(gameBoard, this, true);
            }
            message.send(name + " used Avenger's Shield, healing for " + (healthAmount - beforeHealing));
        }
    }

    @Override
    public String describe() {
        return String.format("%s\t\tHealth: %d/%d\t\tAttack: %d\t\tDefense: %d\t\tLevel: %d\t\tExperience: %d/%d\t\tCoolDown: %d/%d", getName(), healthAmount, healthPool, attack, defense, level, experience, 50 * level, remainCoolDown, abilityCoolDown);
    }

    public int getRemainCoolDown() {
        return remainCoolDown;
    }

    public void setRemainCoolDown(int remainCoolDown) {
        this.remainCoolDown = remainCoolDown;
    }
}
