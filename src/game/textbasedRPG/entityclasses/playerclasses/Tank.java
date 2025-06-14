package game.textbasedRPG.entityclasses.playerclasses;

import java.util.ArrayList;

import game.textbasedRPG.Item;
import game.textbasedRPG.entityclasses.monsterclasses.Monster;

public class Tank extends Warrior {
	
	private double rageChance;
	
	public Tank(String name, int health, int minDmg, int maxDmg, ArrayList<Item> inventory, int level) {
			super(name, (int) (health*(Math.random()*0.5+1.1)), minDmg, maxDmg);
			this.setInventory(inventory);
			this.setLevel(level);
			this.setRageChance((Math.random()*0.2+0.1)); //0.1-0.3
	}
	
	/**
	 * Deals damage to the monster
	 * @param monster is the monster that the player attacks
	 * @returns the amount of attack damage that was dealt
	 */
	public int attack(Monster monster) {
		if (Math.random() < this.rageChance) {
			this.applyEffect("rage", 1);
		}
		int damage = (int) (Math.random()*(this.maxDmg-this.minDmg+1)+this.minDmg);
		damage = wearableBonusApplier("attack", damage);
		damage = monster.takeDamage(damage);
		return damage;
	}

	public double getRageChance() {
		return rageChance;
	}

	public void setRageChance(double rageChance) {
		this.rageChance = rageChance;
	}
}
