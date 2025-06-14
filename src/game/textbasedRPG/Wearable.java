package game.textbasedRPG;

import java.lang.Math;

public class Wearable {

	private String name;
    private String type; //amulet, armor, weapon
    private double level;
    private double attackBonus;
    private double defenseBonus;
    private String protectionFrom;
    private String[] effectsAmuletProtectsFrom = {"poison", "burn", "weaken"};
    
    public Wearable() {
        this.name = "Unknown Wearable";
        this.type = "armor";
        this.level = 1;
        this.attackBonus = 0.0;
        this.defenseBonus = 0.0;
        this.protectionFrom = "";
    }
    
    public Wearable(String name, String type) {
        this.name = name;
        this.type = type.toLowerCase();
        this.level = 1;
        this.protectionFrom = "";
        setBonuses();
    }
    
    public Wearable(String name, String type, int level) {
        this.name = name;
        this.type = type.toLowerCase();
        this.level = level;
        this.protectionFrom = "";
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
    
    public String getProtectionFrom() {
    	return this.protectionFrom;
    }
    
    public String[] getEffectsAmuletProtectsFrom() {
		return effectsAmuletProtectsFrom;
	}

	public void setEffectsAmuletProtectsFrom(String[] effectsAmuletProtectsFrom) {
		this.effectsAmuletProtectsFrom = effectsAmuletProtectsFrom;
	}
    
    /**
     * Sets the percentage bonuses based on the wearable type and level
     */
    private void setBonuses() {
        double baseBonus = (this.level/40.0)*100.0; // Base percentage (max 25% and min 2.5% for level 10 and level 1 respectively)
        double variation = Math.random() * 4 + 1; // 1-5% random variation
        baseBonus = Math.round(baseBonus);
        variation = Math.round(variation);
        
        if (this.type.equals("weapon")) {
            this.attackBonus = baseBonus + variation;
            this.defenseBonus = 0.0;
        } else if (this.type.equals("armor")) {
            this.attackBonus = 0.0;
            this.defenseBonus = baseBonus + variation;

        } else if (this.type.equals("amulet")) {
            this.attackBonus = baseBonus + variation;
            this.defenseBonus = baseBonus + variation;
            this.protectionFrom = this.effectsAmuletProtectsFrom[(int) (Math.random()*
                                                                 this.effectsAmuletProtectsFrom.length)]; 
        }
    }

	public String toString() {
		if (this.type.equals("amulet")) {
			return this.name + " (" + this.type + "): level " + this.level + " (attack + " + this.attackBonus 
					+ ", defense + " + this.defenseBonus + ") -> protection from: "+this.protectionFrom;
		}
		return this.name + " (" + this.type + "): level " + this.level + " (attack + " + this.attackBonus 
		+ ", defense + " + this.defenseBonus + ")";
	}
}
