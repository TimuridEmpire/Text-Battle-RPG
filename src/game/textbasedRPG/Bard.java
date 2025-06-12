package game.textbasedRPG;

import java.util.ArrayList;

public class Bard extends Mage {

	private double charmChance;
	private double healChance;

	public Bard(String name, int health, int minDmg, int maxDmg, ArrayList<Item> inventory, int level) {
		super(name, health, minDmg, maxDmg);
		this.setInventory(inventory);
		this.setLevel(level);
		this.setCharmChance(Math.random() * 0.3 + 0.2);
		this.setHealChance(this.charmChance + 0.1);
	}

	public double getCharmChance() {
		return charmChance;
	}

	public void setCharmChance(double charmChance) {
		this.charmChance = charmChance;
	}

	public double getHealChance() {
		return healChance;
	}

	public void setHealChance(double healChance) {
		this.healChance = healChance;
	}

	/**
	 * Deals damage to the monster if there is enough mana to spare Has the
	 * potential to charm the monster to deal more damage Has the potential to heal
	 * themselves during their attack
	 * 
	 * @param monster is the monster that the player attacks
	 * @returns the amount of attack damage that was dealt
	 */
	public int attack(Monster monster) {
		int damage = super.attack(monster);
		if (Math.random() < charmChance) {
			monster.takeDamage((int) (damage*0.25)); // 25% more damage (calls take damage again with 25% damage -> 100% damage + 25% damage = 125% damage)
			System.out.println("The charm made the " + monster.getType() + " lower their guard");
			System.out.println("The player did more damage as a result");
		}
		if (Math.random() < healChance) {
			int healAmount = (int) (damage * 0.1);
			this.healDamage(healAmount); // heals 10% of the damage dealt to the opponent
			System.out.println("The player healed slightly after their attack by " + healAmount + " points of health");
		}
		return damage;
	}
}
