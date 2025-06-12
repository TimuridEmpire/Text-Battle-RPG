package game.textbasedRPG.entityclasses.monsterclasses;

import game.textbasedRPG.entityclasses.playerclasses.Player;

public class Dragon extends Monster {
	
	private double attackModifier;
	private double fireBreathingChance;
	
	public Dragon(String type, int health, int minDmg, int maxDmg, int level) {
		super(type, health, minDmg, (int) (maxDmg*(Math.random()+1)), level);
		this.attackModifier = Math.min(Math.random()+1,2.0);
		this.fireBreathingChance = Math.min(Math.random(),0.2);
	}
	
	public Dragon(String type, int health, int minDmg, int maxDmg, int level, double attackModifier) {
		super(type, health, minDmg, (int) (maxDmg*(Math.random()+1)), level);
		this.attackModifier = attackModifier;
		this.fireBreathingChance = Math.min(Math.random(),0.2);
	}

	public double getAttackModifier() {
		return attackModifier;
	}

	public void setAttackModifier(double attackModifier) {
		this.attackModifier = attackModifier;
	}
	
	public double getFireBreathingChance() {
		return fireBreathingChance;
	}

	public void setFireBreathingChance(double fireBreathingChance) {
		this.fireBreathingChance = fireBreathingChance;
	}
	
	/**
	 * Has the same level up as its parent class yet also increases its attack modifier as well
	 */
	public void levelUp() {
		super.levelUp();
		this.attackModifier*=1.1;
	}
	
	/**
	 * Deals damage to the player with the attack modifier
	 * @param player is the player
	 * @returns the amount of damage dealt to the player
	 */
	public int attack(Player player) {
		//gets the damage which is calculated the same way as in the monster parent class
		//could do int damage = super.attack(player); but that won't work with the way that the blocking for warrior works
		int damage = (int) (Math.random()*(this.getMaxDmg()-this.getMinDmg()+1)+this.getMinDmg());
		damage = (int) (damage*(Math.max((double) this.getHealth()/this.getOriginalHealth(),0.5)));
		
		if (Math.random() > this.fireBreathingChance) {
			int fireDamage = damage/2;
			player.takeDamage(fireDamage); //burns the player with fire effect
			System.out.println("The "+this.getType()+" used fire breathing to burn "+player.getName());
			System.out.println("The fire damage caused "+fireDamage+" damage");
		}
		
		damage = (int) (player.takeDamage((int) (damage*this.attackModifier))); //gives the damage with the modifier multiplied to it
		return damage;
	}
	
	public String toString() {
		return (getIsAlive() ? "The "+this.getType()+" with "+this.attackModifier+" attack modifier has "
				+this.getHealth()+" health left" : "The "+this.getType()+" is dead");
	}
}
