package game.textbasedRPG;

import java.util.ArrayList;

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
				monster.takeDamage((int) (damage / 5));
				System.out.println("The undead minion did "+((int) (damage / 5))+" damage");
			}
			System.out.println("The monster took more damage as a result");
		}
		return damage;
	}
}
