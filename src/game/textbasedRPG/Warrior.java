package game.textbasedRPG;

import java.util.ArrayList;

public class Warrior extends Player {

	private double blockStrength; // percentage of damage that the player can block

	public Warrior(String name, double blockStrength) {
		super(name, 125, 5, 15);
		this.blockStrength = (blockStrength < 1 && blockStrength > 0) ? blockStrength : 0.1;
	}

	public Warrior(String name, int health, int minDmg, int maxDmg) {
		super(name, health, minDmg, maxDmg);
		this.blockStrength = Math.random() * 0.4 + 0.1;
	}

	public Warrior(String name, int health, int minDmg, int maxDmg, double blockStrength) {
		super(name, health, minDmg, maxDmg);
		this.blockStrength = (blockStrength < 1 && blockStrength > 0) ? blockStrength : 0.1;
	}

	/**
	 * Takes damages and reduces the player's damage received
	 * 
	 * @param damage is the damage that the player receives initially
	 * @returns the damage that the player actually receives
	 */
	public int takeDamage(int damage) {
		int damageBlocked = (int) Math.max(damage * this.blockStrength, 1);
		int damageDone = (int) ((damage) - (damageBlocked));
		System.out.println(this.getName() + " blocks " + (damageBlocked) + " points of damage out of " + damage);
		damageDone = super.takeDamage(damageDone);
		return damageDone;
	}

	/**
	 * Levels up the player based on the monster that they defeated
	 * 
	 * @param monsterLevel is the level of the monster that they defeated
	 */
	public void levelUp(int monsterLevel) {
		super.levelUp(monsterLevel);
		this.blockStrength *= 1.025;
	}

	public double getBlock() {
		return this.blockStrength;
	}

	/**
	 * Upgrades the player class
	 * Only suppressing warnings because all classes are known subclasses in subclass array
	 * @returns one of warrior's subclasses
	 */
	@SuppressWarnings("unchecked")
	public Player upgradePlayer() {
		this.setCanUpgrade(false);
		Class<? extends Warrior>[] subclasses = new Class[] {
				Tank.class,
				Brawler.class
		};
		
		Class<? extends Warrior> subclass = subclasses
				[(int) (Math.random()*subclasses.length)];
		
		try {
			return subclass.getConstructor(String.class, int.class, int.class, int.class, int.class, ArrayList.class, int.class)
			.newInstance(this.getName(),this.getMaxHealth(),this.getMinDmg(),this.getMaxDmg(), this.getInventoryActual(), this.getLevel());
		} catch (Exception e) {
			//defaults to tank if something goes wrong
			return new Tank(this.getName(), this.getMaxHealth(), this.getMinDmg(), this.getMaxDmg(), this.getInventoryActual(), this.getLevel());
		}
	}
}
