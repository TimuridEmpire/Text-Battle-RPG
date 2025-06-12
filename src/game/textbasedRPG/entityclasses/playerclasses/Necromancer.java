package game.textbasedRPG.entityclasses.playerclasses;

import java.util.ArrayList;

import game.textbasedRPG.Item;
import game.textbasedRPG.entityclasses.monsterclasses.Monster;

public class Necromancer extends Mage {

	private double minionAttackChance;
	private int undeadCount;

	public Necromancer(String name, int health, int minDmg, int maxDmg, ArrayList<Item> inventory, int level) {
		super(name, health, minDmg, maxDmg);
		this.setInventory(inventory);
		this.setLevel(level);
		this.setMinionAttackChance(Math.random() * 0.3 + 0.2);
		this.setUndeadCount(5);
	}

	public double getMinionAttackChance() {
		return minionAttackChance;
	}

	public void setMinionAttackChance(double minionAttackChance) {
		this.minionAttackChance = minionAttackChance;
	}
	
	public int getUndeadCount() {
		return this.undeadCount;
	}

	public void setUndeadCount(int undeadCount) {
		this.undeadCount = undeadCount;
	}
	
	/**
	 * Levels up the player based on the monster that they defeated
	 * 
	 * @param monsterLevel is the level of the monster that they defeated
	 */
	public void levelUp(int monsterLevel) {
		super.levelUp(monsterLevel);
		if (Math.random() < ((monsterLevel%10)/20.0)) { //chance to get minion increases the more monsterLevel increases but resets every so often
			this.undeadCount++;
			System.out.println("The number of your undead minions has increased");
			System.out.println("You now have "+this.undeadCount+" number of undead minions");
		}
	}

	/**
	 * Deals damage to the monster if there is enough mana to spare 
	 * Has the potential to summon undead to attack for them
	 * @param monster is the monster that the player attacks
	 * @returns the amount of attack damage that was dealt
	 */
	public int attack(Monster monster) {
		int damage = super.attack(monster);
		if (Math.random() < this.minionAttackChance) {
			System.out.println("The player summoned their undead minions");
			for (int i = 0; i < undeadCount; i++) {
				int minionDamage = damage/10;
				monster.takeDamage(minionDamage);
				System.out.println("The undead minion did "+minionDamage+" damage");
			}
			System.out.println("The monster took more damage as a result");
		}
		return damage;
	}
}
