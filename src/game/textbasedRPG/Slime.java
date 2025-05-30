package game.textbasedRPG;

import java.lang.Math;

public class Slime extends Monster {
	
	private String slimeType;
	private int size = 1;
	
	public Slime(String type, int health, int minDmg, int maxDmg, int level) {
		super(type, health, minDmg, maxDmg, level);
		this.size = Math.min(level, 4);
	}
	
	public Slime(String type, int health, int minDmg, int maxDmg, int level, String slimeType) {
		super(type, health, minDmg, maxDmg, level);
		this.slimeType = slimeType;
		this.size = (int) Math.sqrt(this.getLevel());
	}
	
	public Slime(String type, int health, int minDmg, int maxDmg, int level, String slimeType, int size) {
		super(type, health, minDmg, maxDmg, level);
		this.slimeType = slimeType;
		this.size = size;
	}
	
	public void setSlimeType(String slimeType) {
		this.slimeType = slimeType;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public String getSlimeType() {
		return this.slimeType;
	}
	
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Calls the parent class method but also adds mega to the slime type name
	 */
	public void levelUp() {
		super.levelUp();
		slimeType = "mega-"+this.slimeType;
	}
	
	/**
	 * Attacks the player with the size acting as an attack modifier
	 */
	public int attack(Player player) {
		//gets the damage which is calculated the same way as in the monster parent class
		int damage = (int) (Math.random()*(this.getMaxDmg()-this.getMinDmg()+1)+this.getMinDmg());
		damage = (int) (damage*(Math.max((double) this.getHealth()/this.getOriginalHealth(),0.2)));
		
		damage = (int) (damage*(((double) this.size/2) > 1 ? (double) this.size/2 : 1));
		damage = player.takeDamage(damage); //gives the damage with the modifier multiplied to it
		return (int) (damage);
	}

	public String toString() {
		return (getIsAlive() ? "The "+this.getType()+" has "+this.getHealth()+" health left. Slime type: "+this.slimeType 
				: "The "+this.slimeType+" "+this.getType()+" is dead");
	}
}