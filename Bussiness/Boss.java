package Bussiness;

import Bussiness.Steps.*;
import Gui.MessageCallback;

import java.util.HashMap;
import java.util.Random;

public class Boss extends Enemy implements HeroicUnit {
    protected int visionRange;
    protected int abilityFrequency;
    protected int combatTicks;

    public Boss(char tile, Position position, String name, int healthPool, int attack, int defense, int experienceValue, int visionRange, int abilityFrequency, MessageCallback message) {
        super(tile, position, name, healthPool, attack, defense, experienceValue, message);
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;
    }

    @Override
    public Tile cloneTile(Position newPosition) {
        return new Boss(tile, newPosition, name, healthPool, attack, defense, experienceValue, visionRange, abilityFrequency, message);
    }

    @Override
    public void enemyTurn(GameBoard gameBoard) {
        HashMap<Integer, Step> steps = new HashMap<>();
        steps.put(0, new Up());
        steps.put(1, new Down());
        steps.put(2, new Right());
        steps.put(3, new Left());
        steps.put(4, new DoNothing());
        Tile player = gameBoard.getPlayer();
        if (this.range(player) < visionRange) {
            if (combatTicks == abilityFrequency) {
                combatTicks = 0;
                castAbility(gameBoard);
            } else {
                int dx = this.position.getX() - player.getPosition().getX();
                int dy = this.position.getY() - player.getPosition().getY();
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0)
                        steps.get(3).move(gameBoard, this);
                    else
                        steps.get(2).move(gameBoard, this);
                } else {
                    if (dy > 0)
                        steps.get(0).move(gameBoard, this);
                    else
                        steps.get(1).move(gameBoard, this);
                }
            }
        } else {
            combatTicks = 0;
            Random rand = new Random();
            int randomStep = rand.nextInt(5);
            steps.get(randomStep).move(gameBoard, this);
        }

    }

    @Override
    public String describe() {
        return String.format("%s\t\tHealth: %d/%d\t\tAttack: %d\t\tDefense: %d\t\tExperience Value: %d\t\tVision Range: %d\t\tAbility Frequency: %d", getName(), healthAmount, healthPool, attack, defense, experienceValue, visionRange, abilityFrequency);
    }

    public int specialAbilityAttack() {
        return attack;
    }

    @Override
    public void castAbility(GameBoard gameBoard) {
        Tile closePlayer = gameBoard.getPlayerByRange(this, visionRange);
        if (closePlayer != null) {
            closePlayer.battleEnemy(gameBoard, this, true);
        }
    }

    public int getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }

    public int getAbilityFrequency() {
        return abilityFrequency;
    }

    public void setAbilityFrequency(int abilityFrequency) {
        this.abilityFrequency = abilityFrequency;
    }

    public int getCombatTicks() {
        return combatTicks;
    }

    public void setCombatTicks(int combatTicks) {
        this.combatTicks = combatTicks;
    }
}
