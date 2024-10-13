package Bussiness;

import Gui.MessageCallback;

public class Hunter extends Player {
    protected int range;
    protected int arrowsCount;
    protected int ticksCount;
    public Hunter(Position position, String name, int healthPool, int attack, int defense,int range, MessageCallback message){
        super(position, name, healthPool, attack, defense, message);
        this.range=range;
        this.arrowsCount=10*level;
        this.ticksCount=0;
    }

    @Override
    public Tile cloneTile(Position newPosition) {
        return new Hunter(newPosition,name,healthPool,attack,defense,range,message);
    }

    @Override
    public int specialAbilityAttack() {
        return attack;
    }

    @Override
    public void playerTurn(GameBoard gameBoard, char c) {
        if (ticksCount==10){
            arrowsCount=arrowsCount+level;
            ticksCount=0;
        }
        else ticksCount=ticksCount+1;
        super.playerTurn(gameBoard, c);
    }

    @Override
    public void levelUp() {
        super.levelUp();
        arrowsCount=arrowsCount+10*level;
        attack=attack+2*level;
        defense=defense+level;
    }

    @Override
    public void castAbility(GameBoard gameBoard) {
        if (arrowsCount==0)
            message.send(getName() + " tried to cast Shoot, but there was not enough arrows: " + arrowsCount);
        arrowsCount=arrowsCount-1;
        Tile closeEnemy = gameBoard.getEnemyByRange(this, range);
        if (closeEnemy != null) {
            closeEnemy.battlePlayer(gameBoard, this, true);
        }
        message.send(getName() + " cast Shoot");
    }


    @Override
    public String describe() {
        return String.format("%s\t\tHealth: %d/%d\t\tAttack: %d\t\tDefense: %d\t\tLevel: %d\t\tExperience: %d/%d\t\tRange: %d\t\tArrows: %d", getName(), healthAmount, healthPool, attack, defense, level, experience, 50 * level, range,arrowsCount);
    }

    public int getArrowsCount() {
        return arrowsCount;
    }

    public void setArrowsCount(int arrowsCount) {
        this.arrowsCount = arrowsCount;
    }

    public int getTicksCount() {
        return ticksCount;
    }

    public void setTicksCount(int ticksCount) {
        this.ticksCount = ticksCount;
    }
}
