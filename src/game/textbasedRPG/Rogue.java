package game.textbasedRPG;

import java.lang.Math;
import java.util.ArrayList;

public class Rogue extends Player {
	
	private double critChance;
	
	public Rogue(String name, double critChance) {
		super(name, 75, 1, 10);
		this.critChance = (critChance < 1 && critChance > 0 ) ? critChance : 0.1;
	}
	
	public Rogue(String name, int health, int minDmg, int maxDmg) {
		super(name, health, minDmg, maxDmg);
		this.critChance = Math.random()*0.4+0.1;
	}
	
	public Rogue(String name, int health, int minDmg, int maxDmg, double critChance) {
		super(name, health, minDmg, maxDmg);
		this.critChance = (critChance < 1 && critChance > 0 ) ? critChance : 0.1;
	}
	
	/**
	 * Deals damage to the monster with a chance of using a crit
	 * @param monster is the monster that the player attacks
	 * @returns the amount of attack damage that was dealt
	 */
	public int attack(Monster monster) {
		if (Math.random() <= this.critChance) { //calculates the chance for a crit
			//gets the damage
			int damage = (int) (Math.random()*(this.getMaxDmg()-this.getMinDmg()+1)
					+this.getMinDmg()); 
			//doubles the damage
			damage *= 2;
			damage = wearableBonusApplier("attack", (int) damage);
			damage = monster.takeDamage(damage);
			System.out.println("A crit attack was done"); //lets the user know of the crit
			return (int) (damage);
		}
		return super.attack(monster); //otherwise does a normal attack
	}
	
	/**
	 * Levels up the player based on the monster that they defeated
	 * @param monsterLevel is the level of the monster that they defeated
	 */
	public void levelUp(int monsterLevel) {
		super.levelUp(monsterLevel);
		this.critChance *= 1.05;
	}
	
	public double getCritChance() {
		return this.critChance;
	}
	
	/**
	 * Upgrades the player class
	 * Only suppressing warnings because all classes are known subclasses in subclass array
	 * @returns one of rogue's subclasses
	 */
	@SuppressWarnings("unchecked")
	public Player upgradePlayer() {
		this.setCanUpgrade(false);
		Class<? extends Rogue>[] subclasses = new Class[] {
				Assassin.class,
				Thief.class
		};
		
		Class<? extends Rogue> subclass = subclasses
				[(int) (Math.random()*subclasses.length)];
		
		try {
			return subclass.getConstructor(String.class, int.class, int.class, int.class, ArrayList.class, int.class)
			.newInstance(this.getName(),this.getMaxHealth(),this.getMinDmg(),this.getMaxDmg(), this.getInventoryActual(), this.getLevel());
		} catch (Exception e) {
			//defaults to assassin if something goes wrong
			return new Assassin(this.getName(), this.getMaxHealth(), this.getMinDmg(), this.getMaxDmg(), this.getInventoryActual(), this.getLevel());
		}
	}
}
