package game.textbasedRPG;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

public class Player {
	
	private String name;
	private int health;
	private Item[] inventory;
	private ArrayList<Item> advancedInventory;
	private int maxHealth;
	private int minDmg;
	private int maxDmg;
	private int level = 1;
	private boolean isAlive = true;
	
	public Player(String name) {
		this.name = name;
		this.health = this.maxHealth = 100;
		this.minDmg = 1;
		this.maxDmg = 10;
		defaultInventory();
	}
	
	public Player(String name, int health, int minDmg, int maxDmg) {
		this.name = name;
		this.health = (health > 0) ? health : 100;
		this.maxHealth = this.health;
		this.minDmg = (minDmg > 0) ? minDmg : 1;
		this.maxDmg = (maxDmg > this.minDmg) ? maxDmg : this.minDmg+10;
		//defaultInventory();
		defaultAdvancedInventory();
	}
	
	public Player(String name, Item[] inventory) {
		this.health = this.maxHealth = 100;
		this.minDmg = 1;
		this.maxDmg = 10;
		if (inventory.equals(null)) {
			defaultInventory();
		} else {
			this.inventory = inventory;
		}
	}
	
	/**
	 * The default inventory that the player starts out with
	 */
	public void defaultInventory() {
		inventory = new Item[5];
		inventory[0] = new Item("Health Potion",1);
		inventory[1] = new Item("Health Potion",2);
		inventory[2] = new Item("Strength Potion",1);
		inventory[3] = new Item("Strength Potion",2);
		inventory[4] = new Item("Mana Potion", 1);
	}
	
	/**
	 * The default advanced inventory that the player starts out with
	 */
	public void defaultAdvancedInventory() {
		advancedInventory = new ArrayList<Item>();
		advancedInventory.add(new Item("Health Potion",1));
		advancedInventory.add(new Item("Health Potion",2));
		advancedInventory.add(new Item("Strength Potion",1));
		advancedInventory.add(new Item("Strength Potion",2));
		advancedInventory.add(new Item("Mana Potion",1));
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	public int getMinDmg() {
		return this.minDmg;
	}
	
	public int getMaxDmg() {
		return this.maxDmg;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public boolean getIsAlive() {
		this.isAlive = (this.health) > 0 ? true : false;
		return this.isAlive;
	}
	
	public void setHealth(int health) {
		this.health = (health <= this.maxHealth) ? health : this.maxHealth;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth > this.health ? maxHealth : this.health;
	}
	
	public void setDmg(int minDmg, int maxDmg) {
		this.minDmg = Math.max(1, minDmg);
		this.maxDmg = Math.max(this.minDmg,maxDmg);
	}
	
	/* Won't use since we need to increase the level by one every time
	 * public void setLevel(int level) { this.level = level; }
	 */
	
	/**
	 * Levels up the player based on the monster that they defeated
	 * @param monsterLevel is the level of the monster that they defeated
	 */
	public void levelUp(int monsterLevel) {
        double dmgScale = (this.level == 1) ? 1.1 : 1+Math.log10(this.level)/3.0;
        double hpScale = (this.level == 1) ? 1.1 : 1+Math.log10(this.level)/2.5;
        
        this.setDmg((int) (minDmg*dmgScale), (int) (maxDmg*dmgScale));
        this.setMaxHealth((int) (this.getMaxHealth()*hpScale));
        this.setHealth((int) ((this.health*hpScale)+(monsterLevel*5)));
        System.out.println("Health, Max Health, Min Damage, and Max Damage have all increased");
        this.level++;
	}

	
	/**
	 * Deals damage to the monster
	 * @param monster is the monster that the player attacks
	 * @returns the amount of attack damage that was dealt
	 */
	public int attack(Monster monster) {
		int damage = (int) (Math.random()*(this.maxDmg-this.minDmg+1)+this.minDmg);
		damage = monster.takeDamage(damage);
		return damage;
	}
	
	/**
	 * Has the player take damage and returns the damage
	 * @param damage is the original damage that the player receives
	 * @returns the damage that the player receives
	 */
	public int takeDamage(int damage) {
		this.setHealth(this.health-damage);
		return damage;
	}
	
	/**
	 * Uses the item at the inventory index and sets it to null afterword
	 * @param index
	 */
	public void useItem(int index) {
		if (this.inventory == null) {
			try {
				this.advancedInventory.get(index).use(this);
				this.advancedInventory.remove(index);
			} catch(Exception e) {
				System.out.println("Invalid Selection - Missed Turn");
			}
		} else {
			try {
				this.inventory[index].use(this);
				this.inventory[index] = null;
			} catch(Exception e) {
				System.out.println("Invalid Selection - Missed Turn");
			}
		}
	}
	
	/**
	 * Heals the damage of the player by a set amount
	 * @param healAmount is the amount that the player is healed by
	 */
	public void healDamage(int healAmount) {
		this.health = (this.health+healAmount > this.maxHealth) ?
				this.maxHealth : 
					this.health+healAmount;
	}
	
	/**
	 * Increases the player's min and max damage
	 * @param plusAmount is the amount that the player's strength increases by
	 */
	public void getStrong(int plusAmount) {
		this.setDmg(this.minDmg+plusAmount, this.maxDmg+plusAmount);
	}
	
	/**
	 * Gets the entire inventory of the player
	 * Is different depending on if it is an array or an array list
	 * @returns the array or array list as a string
	 */
	public String getInventory() {
		if (this.inventory == null) {
			if (this.advancedInventory.size() > 0) {
				return (this.advancedInventory.toString());
			} 
			return "No Items";
		}
		return (Arrays.toString(this.inventory));
	}
	
	/**
	 * Gets the specific inventory item
	 * Is different depending on if it is an array or an array list
	 * @param index is the index of the item
	 * @returns the string form of the item
	 */
	public String getInventoryItem(int index) {
		if (this.inventory == null) {
			try {
				return (this.inventory[index].getType());
			} catch (Exception e) {
				return "no item";
			}
		} else {
			try {
				return (this.advancedInventory.get(index).getType());
			} catch (Exception e) {
				return "no item";
			}
		}
	}
	
	/**
	 * Adds an item to the array or array list depending on which is being used
	 * @param item is the item being added
	 */
	public void recieveItem(Item item) {
		if (this.inventory == null) { //simply adding the item
			this.advancedInventory.add(item);
		} else { //putting the item at the first available spot
			boolean putItem = false;
			for (int i = 0; i < this.inventory.length; i++) {
				if (this.inventory[i].equals(null)) {
					this.inventory[i] = item;
					putItem = true;
					break;
				}
			}
			if (!putItem) {
				System.out.println("No space available to put item");
			}
		}
	}
	
	public String toString() {
		return (this.health > 0) ? "The "+this.name+" has "+this.health+" left" :
			this.name+" is dead";
	}
}
