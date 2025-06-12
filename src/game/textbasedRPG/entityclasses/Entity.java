package game.textbasedRPG.entityclasses;

import java.util.HashMap;
import java.util.Map;

import game.textbasedRPG.EffectHandler;

public abstract class Entity {
  
    protected Map<String, Integer> activeEffects = new HashMap<>(); //effect name -> duration
	protected EffectHandler effectHandler = EffectHandler.getInstance();
	
	protected int health;
	protected int minDmg;
	protected int maxDmg;
	
	public Entity(int health, int minDmg, int maxDmg) {
		this.health = health;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
	}
	
    public Map<String, Integer> getActiveEffects() {
        return activeEffects;
    }
    
    /**
     * Applies the effect to the player
     * @param effectName is the name of the effect
     * @param duration is how many turns the effect lasts for
     */
    public void applyEffect(String effectName, int duration) {
        activeEffects.put(effectName, duration);
        System.out.println("The " + effectName + " effect was activated for " + duration + " turns");
    }
    
    /**
     * Updates and applies the effects in the active effects hashmap to the entity each turn and updates duration remaining
     */
    public void updateEffects() {
        for (String effect : new HashMap<>(activeEffects).keySet()) {
        	if (effectHandler.isValidEffect(effect)) { //only gets the effects for the effects in active effects that are in the effects pool
                effectHandler.getEffect(effect).accept(this);
            }
        	//updating remaining duration in turns for the effect
            int remaining = activeEffects.get(effect) - 1;
            if (remaining <= 0) {
                activeEffects.remove(effect);
                System.out.println(effect + " has worn off");
            } else {
                activeEffects.put(effect, remaining);
            }
        }
    }
    
    public int getHealth() {
		return this.health;
	}
	
	public int getMinDmg() {
		return this.minDmg;
	}
	
	public int getMaxDmg() {
		return this.maxDmg;
	}

    //Implemented differently in player and monster
    public abstract int takeDamage(int amount);
    public abstract void healDamage(int amount);
    public abstract void getStrong(int amount);
}
