package game.textbasedRPG;

import game.textbasedRPG.entityclasses.playerclasses.Mage;
import game.textbasedRPG.entityclasses.playerclasses.Player;

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
	 * Calls appropriate methods to add/restore a certain amount of health, strength, or mana
	 * The level of the item is denoting the percentage it will restore (multiplied by 2 for strength)
	 * @param player is the player
	 */
	public void use(Player player) {
		double potency = (this.level/10.0);
		
		System.out.println(player.getName()+" used a level "+this.level
				+" "+this.type);
		if (this.type.equals("Health Potion")) {
			player.healDamage((int) (potency*player.getMaxHealth()));
			System.out.println(player.getName()+"'s health was increased to "+player.getHealth());
		} else if (this.type.equals("Strength Potion")) {
			potency *= 2;
			player.getStrong((int) (potency*player.getMinDmg()));
			System.out.println(player.getName()+"'s strength was increased to the range of "+player.getMinDmg()+" to "+player.getMaxDmg());
		} else if (this.type.equals("Mana Potion")) {
			if (player instanceof Mage) {
				((Mage) player).restoreMana((int) (potency*((Mage) player).getMaxMana()));
				System.out.println(player.getName()+"'s mana was increased to "+((Mage) player).getMana());
			} else {
				System.out.println("Can't use a mana potion");
			}
		} else if (this.type.equals("Vitality Potion")) {
			player.healDamage((int) (potency*player.getMaxHealth()));
			potency *= 2;
			player.getStrong((int) (potency*player.getMinDmg()));
			System.out.println(player.getName()+"'s health was increased to "+player.getHealth());
			System.out.println(player.getName()+"'s strength was increased to the range of "+player.getMinDmg()+" to "+player.getMaxDmg());
		} else {
			System.out.println(player.getName() + " used an item with an unclear result");
		}
	}
}
