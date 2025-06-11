package game.textbasedRPG;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
  
    protected Map<String, Integer> activeEffects = new HashMap<>(); //effect name -> duration
	protected EffectHandler effectHandler = EffectHandler.getInstance();
	
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
        	if (effectHandler.isValidEffect(effect)) {
                effectHandler.getEffect(effect).accept(this);
            }
            int remaining = activeEffects.get(effect) - 1;
            if (remaining <= 0) {
                activeEffects.remove(effect);
                System.out.println(effect + " has worn off");
            } else {
                activeEffects.put(effect, remaining);
            }
        }
    }

    //Implemented differently in player and monster
    public abstract int takeDamage(int amount);
    public abstract void healDamage(int amount);
    public abstract void getStrong(int amount);
}
