package game.textbasedRPG;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EffectHandler {
	
	private static final EffectHandler instance = new EffectHandler();
	
    private Map<String, Consumer<Entity>> totalEffects;

    public EffectHandler() {
    	totalEffects = new HashMap<>();
    	
        totalEffects.put("Burn", e -> e.takeDamage(5));
        totalEffects.put("Regeneration", e -> e.healDamage(4));
        totalEffects.put("Weaken", e -> e.getStrong(-5));
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

