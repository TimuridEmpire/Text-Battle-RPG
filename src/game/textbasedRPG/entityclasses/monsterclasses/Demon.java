package game.textbasedRPG.entityclasses.monsterclasses;

import game.textbasedRPG.entityclasses.playerclasses.Player;

public class Demon extends Monster {
	
	private double attackModifier;
	private double healingChance;
	private double healingMultiplier;
	private double vitalSlashChance;
	
	public Demon(String type, int health, int minDmg, int maxDmg, int level) {
		super(type, health, minDmg, maxDmg, level);
		this.attackModifier = Math.min(Math.random()+1,1.5);
		this.setHealingChance(Math.min(Math.random(), 0.5));
		this.setHealingMultiplier(Math.min(Math.random()+1,2));
		this.setVitalSlashChance(Math.min(Math.random()+1,1.2));
	}
	
	public Demon(String type, int health, int minDmg, int maxDmg, int level, double attackModifier) {
		super(type, health, minDmg, maxDmg, level);
		this.attackModifier = attackModifier;
		this.setHealingChance(Math.min(Math.random(), 0.5));
		this.setHealingMultiplier(Math.min(Math.random()+1,2));
		this.setVitalSlashChance(Math.min(Math.random()+1,1.2));
	}

	public double getAttackModifier() {
		return attackModifier;
	}

	public void setAttackModifier(double attackModifier) {
		this.attackModifier = attackModifier;
	}
	
	public double getHealingChance() {
		return healingChance;
	}

	public void setHealingChance(double healingChance) {
		this.healingChance = healingChance;
	}

	public double getHealingMultiplier() {
		return healingMultiplier;
	}

	public void setHealingMultiplier(double healingMultiplier) {
		this.healingMultiplier = healingMultiplier;
	}
	
	public double getVitalSlashChance() {
		return vitalSlashChance;
	}

	public void setVitalSlashChance(double vitalSlashChance) {
		this.vitalSlashChance = vitalSlashChance;
	}
	
	/**
	 * Has the same level up as its parent class yet also increases its attack modifier as well
	 */
	public void levelUp() {
		super.levelUp();
		this.attackModifier*=1.1;
		this.healingChance*=1.1;
		this.healingMultiplier*=1.1;
		this.vitalSlashChance*=1.1;
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
		damage = (int) (damage*(Math.max((double) this.getHealth()/this.getOriginalHealth(),0.2)));
		
		damage *= this.attackModifier;
		if (Math.random() < this.vitalSlashChance) {
			damage *= Math.max(Math.random()+1.1, 1.5); //gives 1.1-1.5 times the damage as an added multiplier
		}
		
		damage = player.takeDamage((int) damage); //gives the damage with the modifier multiplied to it
		
		//gives the demon back some health
		if (Math.random() < this.healingChance) {
			int heal = (int) (damage*0.25*this.healingMultiplier);
			System.out.println("The "+this.getType()+" used "+this.getType()+"ic healing");
			System.out.println("The "+this.getType()+" healed "+heal+" health");
			this.setHealth(heal);
		}
		
		return (int) (damage);
	}
	
	public String toString() {
		return (getIsAlive() ? "The "+this.getType()+" with "+this.attackModifier+" attack modifier has "
				+this.getHealth()+" health left" : "The "+this.getType()+" is dead");
	}
}
