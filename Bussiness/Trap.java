package Bussiness;

import Gui.MessageCallback;

public class Trap extends Enemy {

    protected int visibilityTime;
    protected int invisibilityTime;
    protected int ticksCount;
    protected boolean visible;

    protected char saveChar;

    public Trap(char tile, Position position, String name, int healthPool, int attack, int defense, int experienceValue,
                int visibilityTime, int invisibilityTime, MessageCallback message) {
        super(tile, position, name, healthPool, attack, defense, experienceValue, message);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
        this.saveChar = tile;
    }

    public Tile cloneTile(Position newPosition) {
        return new Trap(tile, newPosition, name, healthPool, attack, defense, experienceValue, visibilityTime, invisibilityTime, message);
    }

    @Override
    public int specialAbilityAttack() {return 0;}

    @Override
    public void enemyTurn(GameBoard gameBoard) {
        visible = (ticksCount < visibilityTime);
        if (!visible) {
            tile = '.';
        } else {
            tile = saveChar;
        }
        if (ticksCount == visibilityTime + invisibilityTime)
            ticksCount = 0;
        else
            ticksCount = ticksCount + 1;
        Tile player = gameBoard.getPlayer();
        if (this.range(gameBoard.getPlayer()) < 2) {
            player.battleEnemy(gameBoard, this, false);
        }
    }

    @Override
    public String describe() {
        return String.format("%s\t\tHealth: %d/%d\t\tAttack: %d\t\tDefense: %d\t\tExperience Value: %d", getName(), healthAmount, healthPool, attack, defense, experienceValue);
    }

    public int getTicksCount() {
        return ticksCount;
    }

    public void setTicksCount(int ticksCount) {
        this.ticksCount = ticksCount;
    }
}
