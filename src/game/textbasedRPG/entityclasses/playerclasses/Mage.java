package game.textbasedRPG.entityclasses.playerclasses;

import java.util.ArrayList;

import game.textbasedRPG.entityclasses.monsterclasses.Monster;

public class Mage extends Player {

	private int mana;
	private int maxMana;

	public Mage(String name, int mana) {
		super(name, 50, 5, 10);
		this.mana = this.maxMana = (mana > 0) ? mana : 50;
	}

	public Mage(String name, int health, int minDmg, int maxDmg) {
		super(name, health, minDmg, maxDmg);
		this.mana = this.maxMana = (int) (Math.random() * 50 + 25);
	}

	public Mage(String name, int health, int minDmg, int maxDmg, int mana) {
		super(name, health, minDmg, maxDmg);
		this.mana = this.maxMana = (mana > 0) ? mana : 50;
	}

	/**
	 * Deals damage to the monster is there is enough mana to spare
	 * 
	 * @param monster is the monster that the player attacks
	 * @returns the amount of attack damage that was dealt
	 */
	public int attack(Monster monster) {
		int manaCost = (int) (Math.random() * 1.5 * (this.maxMana / 2) + 1);
		if (this.mana >= manaCost) {
			return manaAttack(manaCost, monster);
		}
		System.out.println("Not enough mana to attack properly");
		if (this.mana == 0) { // 20% chance to attack if you have no mana (avoids the player dying if they run
								// out of mana)
			if (Math.random() < .2) {
				System.out.println("Emergency mana granted");
				this.mana += (this.maxMana / 2) + 1;
				return manaAttack(manaCost, monster);
			}
		}
		return 0;
	}

	/**
	 * Consumes mana for an attack if the player can attack
	 * 
	 * @param manaCost is the cost of the attack in mana
	 * @param monster  is the monster that is being attacked
	 * @returns the damage as an int
	 */
	protected int manaAttack(int manaCost, Monster monster) {
		if (this.mana >= manaCost) {
			this.setMana(this.mana - manaCost);
			System.out.println(this.getName() + " consumed " + manaCost + " mana");
			System.out.println(this.getName() + " has " + this.mana + " mana left");
			return super.attack(monster);
		}
		System.out.println("Still not enough mana to attack");
		return 0;
	}

	public void restoreMana(int addedMana) {
		this.mana = (this.mana + addedMana > this.maxMana) ? this.maxMana : this.mana + addedMana;
	}

	/**
	 * Levels up the player based on the monster that they defeated
	 * 
	 * @param monsterLevel is the level of the monster that they defeated
	 */
	public void levelUp(int monsterLevel) {
		super.levelUp(monsterLevel);
		this.setMana((int) (this.mana * (Math.min(2, Math.sqrt(monsterLevel)))));
		this.maxMana *= 1.05;
		System.out.println("Mana has been increased to " + this.mana);
		System.out.println("Max mana has been increased to " + this.maxMana);
	}

	public int getMana() {
		return this.mana;
	}

	public void setMana(int mana) {
		this.mana = Math.min(mana, this.maxMana);
	}

	public int getMaxMana() {
		return this.maxMana;
	}

	public void useMana(int manaUsed) {
		this.setMana(Math.max(this.mana - manaUsed, 0));
	}

	/**
	 * Upgrades the player class Only suppressing warnings because all classes are
	 * known subclasses in subclass array
	 * 
	 * @returns one of mage's subclasses
	 */
	@SuppressWarnings("unchecked")
	public Player upgradePlayer() {
		this.setCanUpgrade(false);
		Class<? extends Mage>[] subclasses = new Class[] { 
				Bard.class, 
				Necromancer.class 
		};

		Class<? extends Mage> subclass = 
				subclasses[(int) (Math.random() * subclasses.length)];

		try {
			return subclass.getConstructor(String.class, int.class, int.class, int.class, ArrayList.class, int.class)
					.newInstance(this.getName(), this.getMaxHealth(), this.getMinDmg(), this.getMaxDmg(), this.getInventoryActual(), this.getLevel());
		} catch (Exception e) {
			// defaults to bard if something goes wrong
			return new Bard(this.getName(), this.getMaxHealth(), this.getMinDmg(), this.getMaxDmg(), this.getInventoryActual(), this.getLevel());
		}
	}
}
