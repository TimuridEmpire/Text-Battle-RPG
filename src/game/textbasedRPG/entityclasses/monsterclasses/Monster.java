package game.textbasedRPG.entityclasses.monsterclasses;

import java.lang.Math;

import game.textbasedRPG.entityclasses.Entity;
import game.textbasedRPG.entityclasses.playerclasses.Player;

public class Monster extends Entity {
	
	private String type;
	private int originalHealth;
	private int level;
	private boolean isAlive;
	
	public Monster(String type) {
		super(10, 1, 10);
		this.type = type;
		this.level = 1;
		this.isAlive = true;
	}
	
	public Monster(String type, int health, int minDmg, int maxDmg, int level) {
		super((health > 0) ? health : 10, (minDmg > 0) ? minDmg : 1, maxDmg < minDmg ? minDmg+((int) Math.random()*10+1) : maxDmg);
		this.type = type;
		this.originalHealth = this.health;
		this.level = level < 1 ? 1 : level;
		this.isAlive = true;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setMinDmg(int minDmg) {
		this.minDmg = minDmg;
	}
	
	public void setMaxDmg(int maxDmg) {
		this.maxDmg = maxDmg;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setOriginalHealth(int originalHealth) {
		this.originalHealth = originalHealth;
	}

	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getMinDmg() {
		return this.minDmg;
	}
	
	public int getMaxDmg() {
		return this.maxDmg;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public int getOriginalHealth() {
		return originalHealth;
	}
	
	public boolean getIsAlive() {
		this.isAlive = (this.health) > 0 ? true : false;
		return this.isAlive;
	}
	
	/**
	 * Deals damage to the player
	 * @param player is the player
	 * @returns the amount of damage dealt
	 */
	public int attack(Player player) {
		int damage = (int) (Math.random()*(this.maxDmg-this.minDmg+1)+this.minDmg);
		damage = (int) (damage*(Math.max((double) Math.sqrt(this.health/this.originalHealth),0.4)));
		damage = player.takeDamage(damage);
		return damage;
	}
	
	@Override
	/**
	 * Decreases the health of the monster by a set amount
	 * @param damage is the damage dealt to the monster
	 * @returns the damage dealt
	 */
	public int takeDamage(int damage) {
        this.health = (this.health-damage) < 0 ? 0 : (this.health-damage);
        return damage;
	}
	
	/**
	 * Increases the level of the monster and increases its health
	 */
	public void levelUp() {
		this.setLevel(this.getLevel() + 1);
		this.health *= 1.1;
	}
	
	@Override
	/**
	 * Heals the monster's damage
	 * @param amount is the amount that it is healed by
	 */
	public void healDamage(int amount) {
		this.setHealth(this.getHealth()+amount);
	}
	
	@Override
	/**
	 * Increases the monster's min and max damage
	 * @param plusAmount is the amount that the monster's strength increases by
	 */
	public void getStrong(int plusAmount) {
		this.setDmg(this.minDmg+plusAmount, this.maxDmg+plusAmount);
	}
	
	/**
	 * Sets the monster's min and max damage
	 * @param minDmg is the minimum amount of damage that the monster can do
	 * @param maxDmg is the maximum amount of damage that the monster can do
	 */
	public void setDmg(int minDmg, int maxDmg) {
		this.maxDmg = Math.max(maxDmg, minDmg);
		this.minDmg = (int) Math.max(this.maxDmg*0.75, minDmg); //ensures min dmg is always relatively close to max dmg no matter what
	}

	public String toString() {
		return (getIsAlive() ? "The "+this.type+" has "+this.health+" health left" :
			"The "+this.type+" is dead");
	}
	
	/**
	 * Checks if two monster objects are equal to each other
	 */
	public boolean equals(Object anObject) {
		if (anObject instanceof Monster) {
			Monster otherMonster = (Monster) anObject;
			if (this.getType() == otherMonster.getType() &&
					this.getHealth() == otherMonster.getHealth() &&
					this.getMinDmg() == otherMonster.getMinDmg() &&
					this.getMaxDmg() == otherMonster.getMaxDmg() &&
					this.getLevel() == otherMonster.getLevel()) {
				return true;
			}
		}
		return false;
	}
 }
