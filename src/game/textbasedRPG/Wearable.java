package game.textbasedRPG;

import java.lang.Math;

public class Wearable {

	private String name;
    private String type; //amulet, armor, weapon
    private double level;
    private double attackBonus;
    private double defenseBonus;
    private double healthBonus;
    
    public Wearable() {
        this.name = "Unknown Wearable";
        this.type = "armor";
        this.level = 1;
        this.attackBonus = 0.0;
        this.defenseBonus = 0.0;
        this.healthBonus = 0.0;
    }
    
    public Wearable(String name, String type) {
        this.name = name;
        this.type = type.toLowerCase();
        this.level = 1;
        setBonuses();
    }
    
    public Wearable(String name, String type, int level) {
        this.name = name;
        this.type = type.toLowerCase();
        this.level = level;
        setBonuses();
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getType() {
        return this.type;
    }
    
    public double getLevel() {
        return this.level;
    }
    
    public double getAttackBonus() {
        return this.attackBonus;
    }
    
    public double getDefenseBonus() {
        return this.defenseBonus;
    }
    
    public double getHealthBonus() {
        return this.healthBonus;
    }
    
    /**
     * Sets the percentage bonuses based on the wearable type and level
     */
    private void setBonuses() {
        double baseBonus = Math.pow(level, 1.2) * 3 + 5; // Base percentage
        double variation = Math.random() * 4 + 1; // 1-5% random variation
        
        if (this.type.equals("weapon")) {
            this.attackBonus = baseBonus + variation;
            this.defenseBonus = 0.0;
            this.healthBonus = 0.0;
        } else if (this.type.equals("armor")) {
            this.attackBonus = 0.0;
            this.defenseBonus = baseBonus + variation;
            this.healthBonus = 0.0;
        } else if (this.type.equals("amulet")) {
            this.attackBonus = 0.0;
            this.defenseBonus = 0.0;
            this.healthBonus = baseBonus + variation; //change to amulet protecting against certain effects once effects are made
        }
    }

	public String toString() {
		return this.name + " (" + this.type + "): level " + this.level + " (attack + " + this.attackBonus
				+ ", defense + " + this.defenseBonus + ", hp + " + this.healthBonus + ")";
	}
}
