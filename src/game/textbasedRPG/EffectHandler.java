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

    	// health debuffs
    	totalEffects.put("Burn", e -> {
    	    int dmg = (int) (e.getHealth() * 0.1);
    	    System.out.println("Burn did " + dmg + " damage");
    	    e.takeDamage(dmg);
    	});
    	totalEffects.put("Poison", e -> {
    	    int dmg = (int) (e.getHealth() * 0.2);
    	    System.out.println("Poison did " + dmg + " damage");
    	    e.takeDamage(dmg);
    	});
    	totalEffects.put("Frostbite", e -> {
    	    int dmg = (int) (e.getHealth() * 0.05);
    	    System.out.println("Frostbite did " + dmg + " damage");
    	    e.takeDamage(dmg);
    	});

    	// health buffs
    	totalEffects.put("Regeneration", e -> {
    	    int heal = (int) (e.getHealth() * 0.1);
    	    System.out.println("Regeneration healed " + heal + " health");
    	    e.healDamage(heal);
    	});

    	// damage debuffs
    	totalEffects.put("Weaken", e -> {
    	    int reduction = (int) (e.getMinDmg() * 0.1);
    	    System.out.println("Weaken reduced damage by " + reduction);
    	    e.getStrong(-1 * reduction);
    	});
    	totalEffects.put("Fatigue", e -> {
    	    int reduction = (int) (e.getMinDmg() * 0.05);
    	    System.out.println("Fatigue reduced damage by " + reduction);
    	    e.getStrong(-1 * reduction);
    	});

    	// damage buffs
    	totalEffects.put("Rage", e -> {
    	    int boost = (int) (e.getMinDmg() * 0.1);
    	    System.out.println("Rage increased damage by " + boost);
    	    e.getStrong(boost);
    	});
    	totalEffects.put("Precision", e -> {
    	    int boost = (int) (e.getMinDmg() * 0.15);
    	    System.out.println("Precision increased damage by " + boost);
    	    e.getStrong(boost);
    	});

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

