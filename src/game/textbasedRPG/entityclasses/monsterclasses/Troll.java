package game.textbasedRPG.entityclasses.monsterclasses;

import java.lang.Math;

public class Troll extends Giant{
	
	private double blockStrength; //percentage of damage that the troll can block
	
	public Troll(String type, int health, int minDmg, int maxDmg, int level) {
		super(type, health, minDmg, maxDmg, level);
		//block strength is between .1 and .5 based on level of the troll
		this.blockStrength = Math.max(.1, Math.min(0.35, (double) level/10.0));
	}
	
	public Troll(String type, int health, int minDmg, int maxDmg, int level, double blockStrength) {
		super(type, health, minDmg, maxDmg, level);
		this.blockStrength = blockStrength;
	}

	public double getBlock() {
		return blockStrength;
	}

	public void setBlock(double blockStrength) {
		this.blockStrength = blockStrength;
	}
	
	/**
	 * Has the same level up as its parent class yet also increases its attack modifier as well
	 */
	public void levelUp() {
		super.levelUp();
		this.blockStrength = Math.round(Math.cbrt(this.blockStrength)*100.0)/100.0;
	}
	
	/**
	 * Takes damages and reduces the player's damage received
	 * @param damage is the damage that the player receives initially
	 * @returns the damage that the player actually receives
	 */
	public int takeDamage(int damage) {
		int damageBlocked = (int) (damage*this.blockStrength);
		damageBlocked = Math.max(damageBlocked, 1);
		int damageDone = (int) ((damage)-(damageBlocked));
        System.out.println(this.getClass().getSimpleName()+" blocks "+damageBlocked+" points of damage out of "+damage);
        return super.takeDamage(damageDone);
	}
}
