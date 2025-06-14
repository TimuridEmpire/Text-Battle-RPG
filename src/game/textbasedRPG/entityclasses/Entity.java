package game.textbasedRPG.entityclasses;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import game.textbasedRPG.EffectHandler;
import game.textbasedRPG.Wearable;

public abstract class Entity {
  
    protected Map<String, Integer> activeEffects = new HashMap<>(); //effect name -> duration
	protected EffectHandler effectHandler = EffectHandler.getInstance();
	
	protected int health;
	protected int minDmg;
	protected int maxDmg;
	protected Wearable[] wearables;
	
	public Entity(int health, int minDmg, int maxDmg) {
		this.health = health;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.wearables = new Wearable[5];
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
        activeEffects.put(effectName.substring(0,1).toUpperCase()+effectName.substring(1).toLowerCase(),
        		duration);
        System.out.println("The " + effectName + " effect was activated for " + duration + " turns");
    }
    
    /**
     * Updates and applies the effects in the active effects hashmap to the entity each turn and updates duration remaining
     */
    public void updateEffects() {
        for (String effect : new HashMap<>(activeEffects).keySet()) {
        	if (effectHandler.isValidEffect(effect)) { //only gets the effects for the effects in active effects that are in the effects pool
        		if (Arrays.stream(this.wearables).filter(Objects::nonNull) //if no wearable has protection from the effect
        		        .noneMatch(w -> w.getProtectionFrom().equals(effect))) {
        		    effectHandler.getEffect(effect).accept(this);
        		} else {
        			System.out.println("One or more of the amulets protected against the "+effect+" effect");
        		}

            }
        	//updating remaining duration in turns for the effect
            int remaining = activeEffects.get(effect) - 1;
            if (remaining <= 0) {
                activeEffects.remove(effect);
                System.out.println(effect + " has worn off");
            } else {
            	System.out.println(effect+" has "+remaining+" turns remaining");
                activeEffects.put(effect, remaining);
            }
        }
        System.out.println(this.health+" health points are left");
        System.out.println("Min dmg: "+this.minDmg+", Max dmg: "+this.maxDmg);
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
	
	public EffectHandler getEventHandler() {
		return this.effectHandler;
	}

    //Implemented differently in player and monster
    public abstract int takeDamage(int amount);
    public abstract void healDamage(int amount);
    public abstract void getStrong(int amount);
}
