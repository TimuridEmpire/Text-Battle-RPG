package game.textbasedRPG;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import game.textbasedRPG.entityclasses.Entity;

public class EffectHandler {
	
	private static final EffectHandler instance = new EffectHandler();
	
    private Map<String, Consumer<Entity>> totalEffects;

    public EffectHandler() {
    	totalEffects = new HashMap<>();
    	
        totalEffects.put("Burn", e -> e.takeDamage((int) (e.getHealth()*0.2)));
        totalEffects.put("Regeneration", e -> e.healDamage((int) (e.getHealth()*0.2)));
        totalEffects.put("Weaken", e -> e.getStrong(-1 * (int) (e.getMinDmg()*0.1)));
        //add more effects
    }

    public Consumer<Entity> getEffect(String name) {
        return totalEffects.get(name);
    }

    public boolean isValidEffect(String name) {
        return totalEffects.containsKey(name);
    }

    public Map<String, Consumer<Entity>> getAllEffects() {
        return Collections.unmodifiableMap(totalEffects);
    }

	public static EffectHandler getInstance() {
		return instance;
	}
}

