package game.textbasedRPG;

import java.util.ArrayList;

public class Assassin extends Rogue {
	
	private double sneakiness;
	
	public Assassin(String name, int health, int minDmg, int maxDmg, ArrayList<Item> inventory, int level) {
		super(name, health, minDmg, maxDmg);
		this.setAdvancedInventory(inventory);
		this.setLevel(level);
		this.setSneakiness((Math.random()*0.2+0.1)); //0.1-0.3
	}

	public double getSneakiness() {
		return sneakiness;
	}

	public void setSneakiness(double sneakiness) {
		this.sneakiness = sneakiness;
	}
	
	/**
	 * Levels up the player based on the monster that they defeated
	 * @param monsterLevel is the level of the monster that they defeated
	 */
	public void levelUp(int monsterLevel) {
		super.levelUp(monsterLevel);
		this.sneakiness *= 1.025;
	}
	
	/**
	 * Deals damage to the monster with a chance of doing an assassination
	 * @param monster is the monster that the player attacks
	 * @returns the amount of attack damage that was dealt
	 */
	public int attack(Monster monster) {
		if (Math.random() < this.sneakiness) { //calculates the chance for an assassination
			//gets the damage
			int damage = (int) (Math.random()*(this.getMaxDmg()-this.getMinDmg()+1)
					+this.getMinDmg()); 
			//multiplies damage by random variable (like rogue superclass crit but better)
			damage *= Math.random()*2+2;
			damage = (int) damage;
			damage = monster.takeDamage(damage);
			if (monster.getIsAlive()) {
				System.out.println("An assassination was attempted\nThe player did a lot of damage"); //lets the user know of the assassination attempt
			} else {
				System.out.println("An assassination was done"); //lets the user know of the assassination
			}
			return (damage);
		}
		return super.attack(monster); //otherwise does a normal attack
	}
}
