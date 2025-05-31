package game.textbasedRPG;

public class Ghost extends Monster {
	
	private double dodgeChance;
	
	public Ghost(String type, int health, int minDmg, int maxDmg, int level) {
		super(type, health, (int) (minDmg/1.2), (int) (maxDmg/1.2), level);
		this.dodgeChance = Math.min(Math.random(),0.25);
	}
	
	public Ghost(String type, int health, int minDmg, int maxDmg, int level, double dodgeChance) {
		super(type, health, minDmg, maxDmg, level);
		this.dodgeChance = dodgeChance;
	}

	public double getDodgeChance() {
		return dodgeChance;
	}

	public void setDodgeChance(double dodgeChance) {
		this.dodgeChance = Math.min(dodgeChance, 0.30); //capping dodge chance
	}
	
	/**
	 * Has the same level up as its parent class yet also increases its attack modifier as well
	 */
	public void levelUp() {
		super.levelUp();
		this.setDodgeChance(this.dodgeChance*1.05);
	}
	
	/**
	 * Decreases the health of the monster by a set amount
	 * Has the chance to dodge the attack
	 * @param damage is the damage dealt to the monster
	 * @returns the damage dealt
	 */
	public int takeDamage(int damage) {
		if (Math.random() < this.dodgeChance) {
			System.out.println("The "+this.getType()+" dodged the attack by phasing out of our reality temporarily");
			return 0;
		}
        this.setHealth((this.getHealth()-damage) < 0 ? 0 : (this.getHealth()-damage));
        return damage;
	}
	
	public String toString() {
		return (getIsAlive() ? "The "+this.getType()+" with "+this.dodgeChance+" chance of dodging has "
				+this.getHealth()+" health left" : "The "+this.getType()+" is dead");
	}
}
