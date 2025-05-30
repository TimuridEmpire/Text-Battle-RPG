package game.textbasedRPG;

public class Zombie extends Monster {
	
	private double attackModifier = 1.0;
	
	public Zombie(String type, int health, int minDmg, int maxDmg, int level) {
		super(type, health, minDmg, maxDmg, level);
		this.attackModifier = Math.min(Math.random()+1,1.25);
	}
	
	public Zombie(String type, int health, int minDmg, int maxDmg, int level, double attackModifier) {
		super(type, health, minDmg, maxDmg, level);
		this.attackModifier = attackModifier;
	}

	public double getAttackModifier() {
		return attackModifier;
	}

	public void setAttackModifier(double attackModifier) {
		this.attackModifier = attackModifier;
	}
	
	/**
	 * Has the same level up as its parent class yet also increases its attack modifier as well
	 */
	public void levelUp() {
		super.levelUp();
		this.attackModifier*=1.05;
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
		
		damage = player.takeDamage((int) (damage*this.attackModifier)); //gives the damage with the modifier multiplied to it
		return (int) (damage);
	}
	
	public String toString() {
		return (getIsAlive() ? "The "+this.getType()+" with "+this.attackModifier+" attack modifier has "
				+this.getHealth()+" health left" : "The "+this.getType()+" is dead");
	}
	
}