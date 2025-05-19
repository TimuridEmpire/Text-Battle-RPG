package game.textbasedRPG;

import java.lang.Math;

public class Item {

	private String type;
	private double level;
	//note that level is used as a way of gauging strength of the potion
	//using strings as rarity works the exact same way

	public Item() {
		this.type = "Unkown Item";
		this.level = 1;
	}

	public Item(String type) {
		this.type = type;
		this.level = 1;
	}

	public Item(String type, int level) {
		this.type = type;
		this.level = level;
	}
	
	public String getType() {
		return this.type;
	}

	public String toString() {
		return (this.type + ": Level " + this.level);
	}
	
	/**
	 * Calls the appropriate methods in order to use the item
	 * @param player is the player
	 */
	public void use(Player player) {
		double potencyEffect = getPoints();
		potencyEffect *= Math.sqrt(this.level);
		
		System.out.println(player.getName()+" used a level "+this.level
				+" "+this.type);
		if (this.type.equals("Health Potion")) {
			player.healDamage((int) potencyEffect);
			System.out.println(player.getName()+"'s health was increased to "+player.getHealth());
		} else if (this.type.equals("Strength Potion")) {
			player.getStrong((int) potencyEffect);
			System.out.println(player.getName()+"'s strength was increased to the range of "+player.getMinDmg()+" to "+player.getMaxDmg());
		} else if (this.type.equals("Mana Potion")) {
			if (player.getClass().getSimpleName().equals("Mage")) {
				((Mage) player).restoreMana((int) potencyEffect);
				System.out.println(player.getName()+"'s mana was increased to "+((Mage) player).getMana());
			} else {
				System.out.println("Can't use a mana potion");
			}
		} else {
			System.out.println(player.getName() + " used an item with an unclear result");
		}
	}
	
	/**
	 * Gets the base points that the potion should award
	 * Based on the level
	 * @returns the base points that the potion should award
	 */
	public int getPoints() { //numbers for healing scale with the player
		if (this.level == 1) {
			return (int) (Math.random()*10+10);
		} else if (this.level == 2) {
			return (int) (Math.random()*10+20);
		} else if (this.level == 3) {
			return (int) (Math.random()*10+30);
		} else if (this.level == 4) {
			return (int) (Math.random()*10+50);
		} else if (this.level == 5) {
			return (int) (Math.random()*20+100);
		}
			return (int) (Math.random()*20+100); //if level exceeds 5 (which it should not anyways)
	}
}
