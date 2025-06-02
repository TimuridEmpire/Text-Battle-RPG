package game.textbasedRPG;

import java.util.ArrayList;
import java.lang.Math;

public class Player {
	
	private String name;
	private int health;
	private ArrayList<Item> inventory;
	private Wearable[] wearables;
	private int maxHealth;
	private int minDmg;
	private int maxDmg;
	private int level = 1;
	private boolean isAlive = true;
	private boolean canUpgrade = false;
	
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
		defaultInventory();
		setWearables(new Wearable[5]);
	}
	
	public Player(String name, ArrayList<Item> inventory) {
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
	 * The default advanced inventory that the player starts out with
	 */
	public void defaultInventory() {
		inventory = new ArrayList<Item>();
		inventory.add(new Item("Health Potion",1));
		inventory.add(new Item("Health Potion",2));
		inventory.add(new Item("Strength Potion",1));
		inventory.add(new Item("Strength Potion",2));
		inventory.add(new Item("Mana Potion",1));
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
		this.health = (health <= this.maxHealth) ? health : this.maxHealth;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth > this.health ? maxHealth : this.health;
	}
	
	public void setDmg(int minDmg, int maxDmg) {
		this.minDmg = Math.max(1, minDmg);
		this.maxDmg = Math.max(this.minDmg,maxDmg);
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
        double dmgScale = (this.level == 1) ? 1.1 : 1+Math.log10(this.level)/3.0;
        double hpScale = (this.level == 1) ? 1.1 : 1+Math.log10(this.level)/2.5;
        
        this.setDmg((int) (minDmg*dmgScale), (int) (maxDmg*dmgScale));
        this.setMaxHealth((int) (this.getMaxHealth()*hpScale));
        
        int totalHealth = (int) ((this.health*hpScale) + (monsterLevel*5));
        int healPoints = totalHealth - this.health;
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
		double attackBonus = 0.0;
		for (int i = 0; i < this.wearables.length; i++) {
			if (this.getWearable(i) != null) {
				attackBonus += this.getWearable(i).getAttackBonus();
		    }
		}
		damage = (int) (damage * (1.0 + attackBonus / 100.0));
		damage = monster.takeDamage(damage);
		return damage;
	}
	
	/**
	 * Has the player take damage and returns the damage
	 * @param damage is the original damage that the player receives
	 * @returns the damage that the player receives
	 */
	public int takeDamage(int damage) {
		double defenseBonus = 0.0;
	    for (int i = 0; i < this.wearables.length; i++) {
	        if (this.getWearable(i) != null) {
	            defenseBonus += this.getWearable(i).getDefenseBonus();
	        }
	    }
	    
	    //Cap defense reduction at a certain number to avoid taking too little damage
	    double maxReduction = 60.0;
	    double reduction = Math.min(defenseBonus, maxReduction);
	    
	    System.out.println("Damage was reduced by "+reduction+" points");
	    
	    int finalDamage = (int)(damage * (1.0 - reduction / 100.0));
	    finalDamage = Math.max(finalDamage, 1); // Always take at least 1 damage
	    
	    this.setHealth(this.health - finalDamage);
	    return finalDamage;
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
