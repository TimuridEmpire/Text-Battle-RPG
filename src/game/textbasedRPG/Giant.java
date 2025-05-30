package game.textbasedRPG;

import java.lang.Math;

public class Giant extends Monster {
	
	private double attackModifier;
	
	public Giant(String type, int health, int minDmg, int maxDmg, int level) {
		super(type, health+((int) (Math.random()*5+5)), 
				minDmg+((int) (Math.random()*5+5)), maxDmg+((int) (Math.random()*5+5)), level);
		this.attackModifier = Math.max(Math.random()+1,1.25);
	}
	
	/**
	 * Deals damage to the player multiplied by a random multiplier
	 * @param player is the player
	 * @returns the amount of damage dealt
	 */
	public int attack(Player player) {
		//gets the damage which is calculated the same way as in the monster parent class
		int damage = (int) (Math.random()*(this.getMaxDmg()-this.getMinDmg()+1)+this.getMinDmg());
		damage = (int) (damage*(Math.max((double) this.getHealth()/this.getOriginalHealth(),0.2)));
		
		damage = player.takeDamage((int) (damage*this.attackModifier)); //gives the damage with the modifier multiplied to it
		return (int) (damage);
	}
}
