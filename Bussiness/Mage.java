package Bussiness;

import Gui.MessageCallback;

public class Mage extends Player {
    protected int manaPool;
    protected int currentMana;
    protected int manaCost;
    protected int spellPower;
    protected int hitsCount;
    protected int abilityRange;

    public Mage(Position position, String name, int healthPool, int attack, int defense, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange, MessageCallback message) {
        super(position, name, healthPool, attack, defense, message);
        this.manaPool = manaPool;
        this.currentMana = manaPool / 4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }

    public Tile cloneTile(Position newPosition) {
        return new Mage(newPosition, name, healthPool, attack, defense, manaPool, manaCost, spellPower, hitsCount, abilityRange, message);
    }

    public int specialAbilityAttack() {
        return spellPower;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        manaPool = manaPool + (25 * level);
        currentMana = Math.min(currentMana + (manaPool / 4), manaPool);
        spellPower = spellPower + (10 * level);
    }

    @Override
    public void playerTurn(GameBoard gameBoard, char c) {
        currentMana = Math.min(manaPool, currentMana + level);
        super.playerTurn(gameBoard, c);
    }

    @Override
    public void castAbility(GameBoard gameBoard) {
        if (currentMana < manaCost) {
            message.send(name + " tried to cast Blizzard, but there was not enough mana: " + currentMana + "/" + manaCost + ".");
        } else {
            currentMana = currentMana - manaCost;
            int hits = 0;
            Tile closeEnemy = gameBoard.getEnemyByRange(this, abilityRange);
            while (hits < hitsCount && closeEnemy != null) {
                closeEnemy.battlePlayer(gameBoard, this, true);
                hits = hits + 1;
                closeEnemy = gameBoard.getEnemyByRange(this, abilityRange);
            }
            message.send(name + " cast Blizzard.");
        }
    }

    @Override
    public String describe() {
        return String.format("%s\t\tHealth: %d/%d\t\tAttack: %d\t\tDefense: %d\t\tLevel: %s\t\tExperience: %d/%d\t\tManaPool: %d\t\tCurrentMana: %d", getName(), healthAmount, healthPool, attack, defense, level, experience, 50 * level, manaPool, currentMana);
    }

    public int getManaPool() {
        return manaPool;
    }

    public void setManaPool(int manaPool) {
        this.manaPool = manaPool;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public int getSpellPower() {
        return spellPower;
    }

    public void setSpellPower(int spellPower) {
        this.spellPower = spellPower;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
}
