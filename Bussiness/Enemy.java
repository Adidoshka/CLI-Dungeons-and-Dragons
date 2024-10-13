package Bussiness;

import Gui.MessageCallback;

public abstract class Enemy extends Unit {
    protected int experienceValue;

    protected Enemy(char tile, Position position, String name, int healthPool, int attack, int defense, int experienceValue, MessageCallback message) {
        super(tile, position, name, healthPool, attack, defense, message);
        this.experienceValue = experienceValue;
    }

    public void battle(GameBoard gameBoard, Unit unit) {
        unit.battleEnemy(gameBoard, this, false);
    }

    public void battleEnemy(GameBoard gameBoard, Enemy enemy, boolean isSpecialAbility) {}

    public void battlePlayer(GameBoard gameBoard, Player player, boolean isSpecialAbility) {
        int attackRoll;
        if (isSpecialAbility)
            attackRoll = player.specialAbilityAttack();
        else
            attackRoll = player.attack();
        int defendRoll = defend();
        message.send(player.getName() + " engaged in combat with " + this.getName() + ".\n" + player.describe() + "\n" + this.describe() +
                "\n" + player.getName() + " rolled " + attackRoll + " attack points.\n" + this.getName() + " rolled " + defendRoll + " defense points. \n" + player.getName() +
                " dealt " + Math.max(attackRoll - defendRoll, 0) + " damage to " + this.getName());
        if (attackRoll - defendRoll > 0)
            healthAmount = healthAmount - (attackRoll - defendRoll);
        if (healthAmount <= 0) {
            player.setExperience(experienceValue);
            if (isSpecialAbility)
                gameBoard.removeEnemyWithAbility(this);
            else
                gameBoard.removeEnemyWithoutAbility(this);
            message.send(getName()+" died. "+player.getName()+" gained "+experienceValue+" experience.");
        }
    }

    @Override
    public void castSpecialAbility(GameBoard gameBoard) {}

    @Override
    public void playerTurn(GameBoard gameBoard, char c) {}

    public abstract void enemyTurn(GameBoard gameBoard);

    public abstract String describe();

    public abstract int specialAbilityAttack();
}
