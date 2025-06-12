package game.textbasedRPG.entityclasses.playerclasses;

import java.util.ArrayList;

import game.textbasedRPG.Item;
import game.textbasedRPG.entityclasses.monsterclasses.Monster;

public class Thief extends Rogue {
	
	private double stealChance;
	
	public Thief(String name, int health, int minDmg, int maxDmg, ArrayList<Item> inventory, int level) {
		super(name, health, minDmg, maxDmg);
		this.setInventory(inventory);
		this.setLevel(level);
		this.setStealChance((Math.random()*0.2+0.1)); //0.1-0.3
	}

	public double getStealChance() {
		return stealChance;
	}

	public void setStealChance(double stealChance) {
		this.stealChance = stealChance;
	}
	
	/**
	 * Deals damage to the monster with a chance of stealing life from the enemy
	 * @param monster is the monster that the player attacks
	 * @returns the amount of attack damage that was dealt
	 */
	public int attack(Monster monster) {
		if (Math.random() < this.stealChance) { //calculates the chance for life steal
			int damage = super.attack(monster);
			this.healDamage((int) (damage*0.1)); //heals player using damage that they did to enemy
			System.out.println("Part of their life was stolen for yourself");
			return damage;
		}
		return super.attack(monster); //otherwise does a normal attack
	}
}
