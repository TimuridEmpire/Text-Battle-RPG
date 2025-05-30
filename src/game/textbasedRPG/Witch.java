package game.textbasedRPG;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Witch extends Monster {

	// 2D array full of all the possible attacks/effects that the witch can do
	private String[][] effects = { { "Water Whip", "Wind Lash", "PTSD" }, // level 1
			{ "Shockwave Blast", "Fireball", "Poison Breath", "Searing Ash" }, // level 2
			{ "Acid Splash", "Frost Touch", "Thunder Clap", "Poisonous Cloud" }, // level 3
			{ "Acidic Flame Breath", "Flame Spears", "Poison Arrows", "Magnetic Tightening", "Ice Needle Barrage" }, // level
																														// 4
			{ "Tectonic Slam", "Ice Spike", "Life Steal", "Venomous Strike", "Stone Pillars" }, // level 5
			{ "Reconstructing Pain", "Plague", "Diamond Rain", "Decay", "Curse of Weakness", "Radiation Burst" }, // level
																													// 6
			{ "Corrupt", "Lightning Strike", "Spirit Spear", "Hadouken", "Dark Magic Pulse", "Mind Fragmentation" }, // level
																														// 7
			{ "Blood Drain", "Wither", "Burning Meteor", "Unholy Wrath" }, // level 8
			{ "Kamehameha", "Soul Drain", "Demonic Judgement", "Meteor Shower", "Apocalyptic Fire" }, // level 9
			{ "Hell's Curse", "Super Nova", "Shadow Retribution", "Idle Transfiguration", "Oblivion Ray", "Calculus" }, // level
																														// 10
			{ "Domain Expansion: Malevolent Shrine", "Hollow Purple", "Cursed Divine Punishment", "Reality Shatter",
					"Cosmic Imposion" } }; // level 11

	private Map<String, Consumer<Player>> effectActions; // creates a hashmap with a String key and a consumer value of
															// the player object

	public Witch(String type) {
		super(type);
		initializeEffects();
	}

	public Witch(String type, int health, int minDmg, int maxDmg, int level) {
		super(type, health, minDmg, maxDmg, level);
		initializeEffects();
	}

	/**
	 * Initializes and puts all of the effects/attacks into the hashmap that was
	 * created earlier Each attack does certain (a) thing(s) to the player
	 */
	private void initializeEffects() {
		effectActions = new HashMap<>();

		// Level 1 effects
		effectActions.put("Water Whip", (Player player) -> attack(player, 1, 5));
		effectActions.put("Wind Lash", (Player player) -> attack(player, 1, ((int) Math.random() * 2) + 2));
		effectActions.put("PTSD", (Player player) -> debuffDamage(player, ((int) Math.random() * 2) + 2));

		// Level 2 effects
		effectActions.put("Shockwave Blast", (Player player) -> attack(player, 1, 5));
		effectActions.put("Fireball", (Player player) -> attack(player, 1.1, 10));
		effectActions.put("Poison Breath", (Player player) -> {
			attack(player, 1, 5);
			debuffDamage(player, ((int) Math.random() * 4) + 2);
		});
		effectActions.put("Searing Ash", (Player player) -> attack(player, 1.1, 10));

		// Level 3 effects
		effectActions.put("Acid Splash", (Player player) -> attack(player, 1.1, 10));
		effectActions.put("Frost Touch", (Player player) -> attack(player, 1.1, 5));
		effectActions.put("Thunder Clap", (Player player) -> attack(player, 1, 10));
		effectActions.put("Poisonous Cloud", (Player player) -> {
			attack(player, 1.1, 10);
			debuffDamage(player, ((int) Math.random() * 2) + 4);
		});

		// Level 4 effects
		effectActions.put("Acidic Flame Breath", (Player player) -> attack(player, 1.2, 15));
		effectActions.put("Flame Spears", (Player player) -> attack(player, 1.2, 15));
		effectActions.put("Poison Arrows", (Player player) -> {
			attack(player, 1.1, 15);
			debuffDamage(player, ((int) Math.random() * 3) + 4);
		});
		effectActions.put("Magnetic Tightening",
				(Player player) -> debuffDamage(player, ((int) Math.random() * 5) + 12));
		effectActions.put("Ice Needle Barrage", (Player player) -> attack(player, 1.2, 20));

		// Level 5 effects
		effectActions.put("Tectonic Slam", (Player player) -> attack(player, 1.2, 25));
		effectActions.put("Ice Spike", (Player player) -> attack(player, 1.2, 25));
		effectActions.put("Life Steal", (Player player) -> {
			attack(player, 1.1, 15);
			debuffMaxHealth(player, ((int) Math.random() * 5) + 16);
			this.setHealth(this.getHealth() + 20);
			System.out.println("The witch's health was increased to " + this.getHealth());
		});
		effectActions.put("Venomous Strike", (Player player) -> {
			attack(player, 1.2, 15);
			debuffDamage(player, ((int) Math.random() * 2) + 4);
		});
		effectActions.put("Stone Pillars", (Player player) -> {
			attack(player, 1.1, 25);
			debuffDamage(player, ((int) Math.random() * 2) + 4);
		});

		// Level 6 effects
		effectActions.put("Reconstructing Pain", (Player player) -> {
			attack(player, 1.1, 10);
			debuffMaxHealth(player, ((int) Math.random() * 5) + 16);
			debuffDamage(player, ((int) Math.random() * 3) + 8);
		});
		effectActions.put("Plague", (Player player) -> attack(player, 1.2, 10));
		effectActions.put("Diamond Rain", (Player player) -> attack(player, 1.2, 20));
		effectActions.put("Decay", (Player player) -> {
			attack(player, 1.2, 10);
			debuffMaxHealth(player, ((int) Math.random() * 5) + 26);
		});
		effectActions.put("Curse of Weakness", (Player player) -> {
			attack(player, 1.1, 10);
			debuffMaxHealth(player, ((int) Math.random() * 6) + 8);
			debuffDamage(player, ((int) Math.random() * 6) + 12);
		});
		effectActions.put("Radiation Burst", (Player player) -> attack(player, 1.2, 20));

		// Level 7 effects
		effectActions.put("Corrupt", (Player player) -> debuffDamage(player, 25));
		effectActions.put("Lightning Strike", (Player player) -> attack(player, 1.1, 25));
		effectActions.put("Spirit Spear", (Player player) -> attack(player, 1.2, 20));
		effectActions.put("Hadouken", (Player player) -> attack(player, 1.2, 25));
		effectActions.put("Dark Magic Pulse", (Player player) -> attack(player, 1.2, 25));
		effectActions.put("Mind Fragmentation", (Player player) -> {
			attack(player, 1.1, 20);
			debuffMaxHealth(player, ((int) Math.random() * 6) + 26);
		});

		// Level 8 effects
		effectActions.put("Blood Drain", (Player player) -> {
			attack(player, 1.1, 30);
			debuffMaxHealth(player, ((int) Math.random() * 8) + 28);
		});
		effectActions.put("Wither", (Player player) -> {
			attack(player, 1.1, 35);
			debuffMaxHealth(player, ((int) Math.random() * 8) + 30);
			debuffDamage(player, ((int) Math.random() * 5) + 8);
		});
		effectActions.put("Burning Meteor", (Player player) -> attack(player, 1.2, 35));
		effectActions.put("Unholy Wrath", (Player player) -> {
			attack(player, 1.1, 40);
			debuffDamage(player, ((int) Math.random() * 5) + 12);
		});

		// Level 9 effects
		effectActions.put("Kamehameha", (Player player) -> attack(player, 1.2, 40));
		effectActions.put("Soul Drain", (Player player) -> {
			attack(player, 1.3, 35);
			debuffMaxHealth(player, ((int) Math.random() * 6) + 27);
			debuffDamage(player, ((int) Math.random() * 8) + 12);
		});
		effectActions.put("Demonic Judgement", (Player player) -> {
			attack(player, 1.1, 45);
			debuffMaxHealth(player, ((int) Math.random() * 6) + 27);
		});
		effectActions.put("Meteor Shower", (Player player) -> attack(player, 1.1, 45));
		effectActions.put("Apocalyptic Fire", (Player player) -> attack(player, 1.1, 50));

		// Level 10 effects
		effectActions.put("Hell's Curse", (Player player) -> {
			attack(player, 1.2, 55);
			debuffMaxHealth(player, ((int) Math.random() * 7) + 29);
		});
		effectActions.put("Super Nova", (Player player) -> attack(player, 1.2, 60));
		effectActions.put("Shadow Retribution", (Player player) -> {
			attack(player, 1.1, 65);
			debuffDamage(player, ((int) Math.random() * 5) + 28);
		});
		effectActions.put("Idle Transfiguration", (Player player) -> {
			// Implement random roll for survival
			double survivalChance = Math.random();
			if (survivalChance <= 0.7) { // 70% chance to get damaged a lot
				if (survivalChance <= 0.65) {
					attack(player, 1.75 - ((double) player.getLevel() / 10), 70);
				} else { // 5% chance of instant death
					player.setHealth(0); // instant kill
				}
			} else {
				System.out.println(player.getName()
						+ "'s soul and sense of self was stronger than the witch's amateur grasp of this power");
				System.out.println("Yet some damage to " + player.getName() + "'s soul was still done");
				attack(player, 1.2, 20); // slight damage (compared to what could've been)
			}
		});
		effectActions.put("Oblivion Ray", (Player player) -> attack(player, 1.1, 70));
		effectActions.put("Calculus", (Player player) -> {
			System.out.println(
					player.getName() + " had to solve a paper full of increasingly difficult calculus questions");
			System.out.println(
					"Their head started to hurt as the questions got more difficult and it permanently damaged them");
			attack(player, 1.1, 20 + (player.getLevel() * 2));
			debuffMaxHealth(player, ((int) Math.random() * 11) + 40);
			debuffDamage(player, ((int) Math.random() * 6) + 17);
		});

		// Level 11 effects
		effectActions.put("Domain Expansion: Malevolent Shrine", (Player player) -> {
			System.out.println("Domain Expansion: Malevolent Shrine\n");
			if (player.getLevel() < 10) {
				attack(player, 10, player.getMaxDmg());
				System.out.println(player.getName() + " has been affected by the cleave effect\n");
			} else if (player.getLevel() == 10) {
				attack(player, 1.1, 30);
				System.out.println(player.getName()
						+ " has been affected by the cleave effect but was able to withstand it to some degree\n");
			} else {
				System.out.println("You walk through Sukuna's domain without a scratch\n"
						+ "You take no damage");
			}
		});
		effectActions.put("Hollow Purple", (Player player) -> {
			attack(player, Double.POSITIVE_INFINITY, Integer.MAX_VALUE);
			System.out.println("\n" + player.getName() + " was erased from existence\n");
		});
		effectActions.put("Cursed Divine Punishment", (Player player) -> {
			attack(player, 1.2, 80);
			debuffMaxHealth(player, player.getMaxHealth() / 2);
		});
		effectActions.put("Reality Shatter", (Player player) -> {
			attack(player, 1.5, 90);
			debuffMaxHealth(player, ((int) Math.random() * 6) + 47);
		});
		effectActions.put("Cosmic Implosion", (Player player) -> attack(player, 1.5, 100));
	}

	/**
	 * Applies the effects/attacks the player
	 * 
	 * @param player is the player
	 * @returns the name of the effect
	 */
	public String applyEffect(Player player) {
		String effectChosen = "";
		try {
			effectChosen = this.effects[this.getLevel()
					- 1][(int) (Math.random() * this.effects[this.getLevel() - 1].length)]; // chooses the effect
																							// randomly based on level
		} catch (Exception e) {
			System.out.println("Add more effects");
		}

		if (effectActions.containsKey(effectChosen)) { // apply the effect if it exists
			System.out.println("The witch used " + effectChosen + " on " + player.getName());
			effectActions.get(effectChosen).accept(player);
		} else {
			attack(player, 1.5, 20);
		}
		return effectChosen;
	}

	/**
	 * Attacks the player with the modifiers of multiplier and increaseDamage
	 * 
	 * @param player         is the player
	 * @param multiplier     is the damage multiplier
	 * @param increaseDamage is the amount that the damage is increased after the
	 *                       multiplier is in effect
	 * @returns the total damage dealt to the player
	 */
	public int attack(Player player, double multiplier, int increaseDamage) {
		int damage = (int) (Math.random() * (this.getMaxDmg() - this.getMinDmg() + 1) + this.getMinDmg());
		damage *= multiplier;
		damage += increaseDamage;
		damage = (int) (damage * (Math.max((double) this.getHealth() / this.getOriginalHealth(), 0.2)));
		damage = player.takeDamage(damage);
		if (Double.isInfinite(multiplier)) {
			System.out.println(player.getName() + " took ∞ damage");
			damage = Integer.MAX_VALUE;
		} else {
			System.out.println(player.getName() + " took " + damage + " damage");
		}
		return damage;
	}

	/**
	 * Debuffs the maximum amount of health that the player can have
	 * 
	 * @param player is the player
	 * @param debuff is the debuff amount
	 */
	public void debuffMaxHealth(Player player, int debuff) {
		player.setMaxHealth(player.getMaxHealth() - debuff);
		System.out.println(player.getName() + "'s max health has been debuffed by " + debuff);
	}

	/**
	 * Debuffs the amount of damage that the player can do
	 * 
	 * @param player is the player
	 * @param debuff is the amount of damage that is debuffed
	 */
	public void debuffDamage(Player player, int debuff) {
		player.setDmg(player.getMinDmg() - debuff, player.getMaxDmg() - debuff);
		System.out.println(player.getName() + "'s damage has been debuffed by " + debuff);
	}

	/**
	 * Gets the effects that the witch can do based on their level
	 * 
	 * @returns the list of effects
	 */
	public String[] getEffects() {
		return (this.effects[this.getLevel() - 1]);
	}
}
