package game.textbasedRPG.entityclasses.playerclasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import game.textbasedRPG.Item;
import game.textbasedRPG.Wearable;
import game.textbasedRPG.entityclasses.Entity;
import game.textbasedRPG.entityclasses.monsterclasses.Monster;

import java.lang.Math;

public class Player extends Entity {
	
	private String name;
	private ArrayList<Item> inventory;
	private int maxHealth;
	private int level = 1;
	private boolean isAlive = true;
	private boolean canUpgrade = false;
	
	public Player(String name) {
		super(100, 1, 10);
		this.name = name;
		this.maxHealth = 100;
		defaultInventory();
		setWearables(new Wearable[5]);
	}
	
	public Player(String name, int health, int minDmg, int maxDmg) {
		super((health > 0) ? health : 100, (minDmg > 0) ? minDmg : 1, (maxDmg > minDmg) ? maxDmg : minDmg+10);
		this.setDmg(minDmg, maxDmg);
		this.name = name;
		this.maxHealth = this.health;
		defaultInventory();
		setWearables(new Wearable[5]);
	}
	
	public Player(String name, ArrayList<Item> inventory) {
		super(100, 1, 10);
		this.maxHealth = 100;
		if (inventory.equals(null)) {
			defaultInventory();
		} else {
			this.inventory = inventory;
		}
		setWearables(new Wearable[5]);
	}
	
	/**
	 * The default advanced inventory that the player starts out with
	 */
	public void defaultInventory() {
		inventory = new ArrayList<Item>();
		inventory.add(new Item("Health Potion",1));
		inventory.add(new Item("Health Potion",1));
		inventory.add(new Item("Health Potion",2));
		inventory.add(new Item("Strength Potion",1));
		inventory.add(new Item("Strength Potion",2));
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
	
	public boolean getCanUpgrade() {
		return canUpgrade;
	}
	
	public Wearable[] getWearables() {
		return wearables;
	}

	public void setWearables(Wearable[] wearables) {
		this.wearables = wearables;
	}
	
	public void setHealth(int health) {
		this.health = (health <= this.maxHealth) ? ((health > 0) ? health : 0) : this.maxHealth;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth > this.health ? maxHealth : this.health;
	}
	
	public void setDmg(int minDmg, int maxDmg) {
		maxDmg = Math.max(maxDmg, minDmg); //max damage is maximum of min and max damage passed as parameters regardless of order
		this.maxDmg = Math.max(maxDmg, 10); //setting max damage to at least be 10
		this.minDmg = (int) Math.max(this.maxDmg*0.75, minDmg); //ensures min dmg is always relatively close to max dmg no matter what
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public void setCanUpgrade(boolean canUpgrade) {
		this.canUpgrade = canUpgrade;
	}
	
	public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}
	
	/* Won't use since we need to increase the level by one every time
	 * public void setLevel(int level) { this.level = level; }
	 */
	
	/**
	 * Levels up the player based on the monster that they defeated
	 * @param monsterLevel is the level of the monster that they defeated
	 */
	public void levelUp(int monsterLevel) {
		//damage and hp scaling (damage scaling slows done after 10 levels)
        double dmgScale = (this.level == 1) ? 1.1 : (this.level > 10) ? 1+Math.log10(this.level)/3.5 : 1+Math.log10(this.level)/3.0;
        double hpScale = (this.level == 1) ? 1.1 : (this.level <= 10) ? 1+Math.log10(this.level)/2.75 : 1+Math.log10(this.level)/3.25;
        
        this.setDmg((int) (minDmg*dmgScale), (int) (maxDmg*dmgScale));
        this.setMaxHealth((int) (this.getMaxHealth()*hpScale));
        
        int healPoints = (int) ((this.health*hpScale) + (monsterLevel*2)) - this.health;
        this.healDamage(healPoints);
        System.out.println("Health has been increased by "+healPoints+" points");
        System.out.println("Health, Max Health, Min Damage, and Max Damage have all increased");
       
        this.level++;
        
        if (this.level == 5) {
        	this.setCanUpgrade(true);
        }
        
	}
	
	/**
	 * Deals damage to the monster
	 * @param monster is the monster that the player attacks
	 * @returns the amount of attack damage that was dealt
	 */
	public int attack(Monster monster) {
		int damage = (int) (Math.random()*(this.maxDmg-this.minDmg+1)+this.minDmg);
		damage = wearableBonusApplier("attack", damage);
		damage = monster.takeDamage(damage);
		return damage;
	}
	
	@Override
	/**
	 * Has the player take damage and returns the damage
	 * @param damage is the original damage that the player receives
	 * @returns the damage that the player receives
	 */
	public int takeDamage(int damage) {
	    damage = wearableBonusApplier("defense", damage);
	    this.setHealth(this.health - damage);
	    return damage;
	}
	
	/**
	 * Applies the bonuses that the player has
	 * @param bonusType is the type of bonus (either attack or defense)
	 * @param damage is the base damage before bonus (either giving or receiving)
	 * @return the damage after bonuses are applied to it
	 */
	public int wearableBonusApplier(String bonusType, int damage) {
		double bonus = 0.0;
		for (int i = 0; i < this.wearables.length; i++) {
			if (this.getWearable(i) != null) {
				if (bonusType.equalsIgnoreCase("attack")) { //attack bonus
					bonus += this.getWearable(i).getAttackBonus();
				} else { //defense reduction
					bonus += this.getWearable(i).getDefenseBonus();
				}
		    }
		}
		
		//Cap bonus at a certain number to avoid adding too big of a bonus
	    double cap = 50.0;
	    bonus = Math.min(cap, bonus);
		
	    if (bonus == cap) {
	    	System.out.println("Max "+bonusType+" bonus of 50% was reached");
	    }
		if (Arrays.stream(this.wearables).anyMatch(Objects::nonNull)) {
			System.out.println(bonusType.substring(0,1).toUpperCase()+bonusType.substring(1)+" was increased by "+bonus+" percent");
		}
		if (bonusType.equalsIgnoreCase("attack")) { 
			damage = (int) (damage * (1.0 + bonus / 100.0)); //attack bonus
		} else {
			damage = (int) (damage * (1.0 - bonus / 100.0)); //defense reduction
		    damage = Math.max(damage, 1); // Always take at least 1 damage
		}
		return damage;
	}
	
	@Override
	/**
	 * Heals the damage of the player by a set amount
	 * @param healAmount is the amount that the player is healed by
	 */
	public void healDamage(int healAmount) {
		this.health = Math.min(this.maxHealth, Math.max(0,this.health+healAmount));
	}
	
	@Override
	/**
	 * Increases the player's min and max damage
	 * @param plusAmount is the amount that the player's strength increases by
	 */
	public void getStrong(int plusAmount) {
		this.setDmg(this.minDmg+plusAmount, this.maxDmg+plusAmount);
	}
	
	/**
	 * Upgrades the player class
	 * @returns the player itself since this method is meant to be overridden
	 */
	public Player upgradePlayer() {
		this.setCanUpgrade(false);
		return this;
	}
	
	/**
	 * Uses the item at the inventory index and removes it afterword
	 * @param index
	 */
	public void useItem(int index) {
		try {
			this.inventory.get(index).use(this);
			this.inventory.remove(index);
		} catch(Exception e) {
			System.out.println("Invalid Selection - Missed Turn");
		}
	}
	
	/**
	 * Gets the entire inventory of the player
	 * @returns the array list as a string
	 */
	public String getInventory() {
		if (this.inventory.size() > 0) {
			return (this.inventory.toString());
		} 
		return "No Items";
}
	
	public ArrayList<Item> getInventoryActual(){
		return this.inventory;
	}
	
	/**
	 * Gets the specific inventory item
	 * @param index is the index of the item
	 * @returns the string form of the item
	 */
	public String getInventoryItem(int index) {
		try {
			return (this.inventory.get(index).getType());
		} catch (Exception e) {
			return "no item";
		}
	}
	
	/**
	 * Adds an item to the array list
	 * @param item is the item being added
	 */
	public void recieveItem(Item item) {
		this.inventory.add(item);
	}
	
	/**
	 * Adds a wearable to the first available slot in the equipment array
	 * @param wearable the wearable item to add
	 */
	public void receiveWearable(Wearable wearable) {
	    boolean placed = false;
	    for (int i = 0; i < this.wearables.length; i++) {
	        if (this.wearables[i] == null) {
	            this.wearables[i] = wearable;
	            placed = true;
	            break;
	        }
	    }
	    if (!placed) {
	        System.out.println("No space available for wearable equipment");
	    }
	}

	/**
	 * Gets a specific wearable from the equipment array
	 * @param index the slot index (0-4)
	 * @return the wearable at that slot, or null if empty/invalid index
	 */
	public Wearable getWearable(int index) {
	    try {
	        return this.wearables[index];
	    } catch (Exception e) {
	        return null;
	    }
	}
	
	public String toString() {
		return (this.health > 0) ? "The "+this.name+" has "+this.health+" left" :
			this.name+" is dead";
	}
}
