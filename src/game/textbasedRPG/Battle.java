package game.textbasedRPG;

import java.util.Scanner;

import game.textbasedRPG.entityclasses.monsterclasses.Monster;
import game.textbasedRPG.entityclasses.monsterclasses.Witch;
import game.textbasedRPG.entityclasses.playerclasses.Mage;
import game.textbasedRPG.entityclasses.playerclasses.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Battle {
	
	private static boolean continuePlaying = true;
	
	/**
	 * The battle
	 * @param player is the player
	 * @param scan is the scanner
	 * @throws denotes the exceptions that could be thrown
	 */
	public static void haveBattle(Player player, Scanner scan, String mode) 
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
	{
		Monster monster = createRandomMonster(player, mode);
		battleLogic(player, monster, scan);
	}
	
	
	
	/**
	 * Dynamically creates a random new monster
	 * @returns the monster that is created
	 * @throws exceptions are all based on if the class that we are trying to access exists and has a valid constructor
	 */
	public static Monster createRandomMonster(Player player, String mode) 
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
	{
		String[][] monsterTypes = {{"Zombie","Slime","Witch","Giant","Ghost"},
				{"Dragon", "Troll","Demon"}};
		Random randomChoice = new Random();
		String packageName = "game.textbasedRPG.entityclasses.monsterclasses.";
		String randomMonster = packageName;
		
		 if (mode.equalsIgnoreCase("witch")) { //fun and chaotic mode
				randomMonster = packageName+"Witch";
		} else { //normal mode
			ArrayList<String> allMonsters = new ArrayList<>();
		    int maxMonsterLevel = Math.min((int) player.getLevel()/10, monsterTypes.length-1);
		    
		    // Add monsters based on levels of the game that the player is in which is determined by their level
		    for (int i = 0; i <= maxMonsterLevel; i++) {
		        allMonsters.addAll(Arrays.asList(monsterTypes[i]));
		    }
		    //gets a random monster type from the list of monster types
		    String chosen = allMonsters.get(randomChoice.nextInt(allMonsters.size()));
		    randomMonster = packageName+chosen;
		}
		 
		try {
			Class<?> monsterClass = Class.forName(randomMonster); //gets the class of the monster chosen
			Constructor<?> constructor = monsterClass.getConstructor(
					String.class, int.class, int.class, int.class, int.class); //gets the constructor of the monster chosen
			
			/* Monster level percentages
			 * Player level - 1 = 20%
			 * Player level = 50%
			 * Player level + 1 = 25%
			 * Player level + 2 = 2.5%
			 * Player level + 3 = 2.5%
			 */
			double rand = Math.random();
	        int monsterLevel = player.getLevel() == 1 ? (int)(Math.random()*2+1) : rand < 0.5 ? 
	        		player.getLevel() : rand < 0.7 ? Math.max(player.getLevel()-1,1) 
	        		: rand < 0.95 ? player.getLevel()+1 : player.getLevel() + (int)(Math.random()*2+2); // +2 or +3
	        
	        //monster hp scales with player damage
	        double avgPlayerDmg = (player.getMinDmg() + player.getMaxDmg()) / 2.0;
		    //random factor to vary monster hp slightly
		    double randomFactor = Math.random() * 0.35 + 0.8;  // range: 0.8 to 1.15
	
		    //scale difficulty based on player vs monster level
		    double statScalar;
		    int levelDiff = player.getLevel() - monsterLevel;
		    if (levelDiff >= 0) {
		        statScalar = 1.0 + 0.01 * levelDiff;
		    } else {
		        statScalar = 1.0 + 0.1 * levelDiff;
		        statScalar = Math.max(0.7, statScalar);
		    }
		    
		    //monster should survive about targetRounds number of hits
		    int targetRounds = 4;
		    int monsterHP = (int) (avgPlayerDmg * targetRounds * randomFactor * statScalar);
	
		    //setting monster damage to be around 20-30% of average player health
		    double avgMonsterDmg = ((player.getHealth() + player.getMaxHealth()) / 2.0) * (Math.random()*10+20)/100.0;
		    int monsterMinDmg = Math.max(1, (int) (avgMonsterDmg * 0.8));
		    int monsterMaxDmg = Math.max(monsterMinDmg + 1, (int) (avgMonsterDmg * 1.1));

	        //creates the monster with randomized (but balanced) stats
	        return (Monster) constructor.newInstance(randomMonster.substring(packageName.length()), monsterHP, 
	        		monsterMinDmg, monsterMaxDmg, monsterLevel);
		} catch (ClassNotFoundException e) {
			System.out.println("Monser class not found");
			e.printStackTrace();
		}
		return new Monster("Unknown Abomination Monster");
		
	}
	
	/**
	 * Validity check for the input of the player for what action they chose
	 * @param input is the player's input as a string
	 * @returns if the action that the player chose is a valid action
	 */
	public static boolean validityCheck(String input) {
		try {
			int intInput = Integer.parseInt(input);
			return (intInput >= 0); // && intInput <= 5 (if using array of length 5)
		} catch (Exception e) {
			if (input.toLowerCase().equals("quit")) {
				continuePlaying = false;
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Is a battle between the player and the monster
	 * @param player is the player
	 * @param monster is the random monster that was generated before
	 * @param scan is the scanner
	 */
	public static void battleLogic(Player player, Monster monster, Scanner scan) {
		int round = 1;
		System.out.println("\n"+player.getName()+" has encountered a level "+monster.getLevel()+" "+monster.getType()+"\n");
		while (player.getIsAlive() && monster.getIsAlive()) { //gameplay loop for battle terminates once either the player or monster are dead
			//prints the demarcation for the rounds
			for (int i = 0; i < 40; i++) { System.out.print("*"); }
			System.out.print("Round "+round);
			for (int i = 0; i < 40; i++) { System.out.print("*"); }
			System.out.println("\n");
			
			//applies the player's and monster's effects at the start of the round
			if (!player.getActiveEffects().isEmpty()) {
				System.out.println("The player has the following effects:");
				player.getActiveEffects();
				System.out.println();
				player.updateEffects();
			}
			if (!monster.getActiveEffects().isEmpty()) {
				System.out.println("The "+monster.getType()+" has the following effects:");
				monster.getActiveEffects();
				System.out.println();
				monster.updateEffects();
			}
			System.out.println();
			
			playerTurn(player, monster, scan);
			if (monster.getHealth() <= 0) { //breaking the loop if the player's attack killed the monster
				monster.setIsAlive(false);
				break;
			} else if (!continuePlaying) {
				break;
			}
			monsterTurn(player, monster);
			
			//mages receive mana back every round
			if (player instanceof Mage) {
				((Mage) player).restoreMana(((Mage) player).getMana());
			}
			
			System.out.println();
			round++;
		}
		if (!continuePlaying) {
			System.err.println("The player has chosen to stop playing");
			System.err.println("The player has died as a consequence");
			player.setHealth(0);
			System.err.println("\nPlayer hp is now "+player.getHealth());
		} else {
			//print the results once either the player or the monster has died
			System.out.println();
			if (player.getIsAlive()) {
				System.out.println(player.getName()+" has defeated the "+monster.getType());
				
				Item loot = getLoot(player);
				player.recieveItem(loot);
				System.out.println(player.getName()+" has been rewarded with a "+loot);
				
				//20% chance when at least one wearable is null
				 if (Math.random() < 0.2 && Arrays.stream(player.getWearables()).anyMatch(Objects::isNull)) {
					 Wearable newWearable = getWearable(player);
					 player.receiveWearable(newWearable); 
				 }
				
				 //original player and monster levels before leveling up the player
				 int monsterLevel = monster.getLevel();
				 int playerLevel = player.getLevel();

				 //calculates how many more levels the monster has compared to the player's old level and levels up the player that much
				 //only levels up the player if the monster is higher level than it
				 System.out.println();
				 int extraLevels = monsterLevel - playerLevel; 
				 if (extraLevels > 0) {
				     for (int i = 0; i < extraLevels; i++) {
				         player.levelUp(monsterLevel);
				         System.out.println("The player has leveled up\n");
				     }
				 }
				 System.out.println("\n");
				
				int healAmount = (int) (Math.random()*(player.getMaxHealth()*0.05+1)+player.getMaxHealth()*0.05); //random number 5-10% of max player health
				player.healDamage(healAmount); //heals the player
				System.out.println(player.getName()+" has been rewarded "+healAmount+" extra points of health");
				
				if (player.getLevel() > playerLevel) { //if the player leveled up
					System.out.println("\nThe player is now level "+player.getLevel());
				}	
				System.out.println("The player has the following stats after the battle:");
	            System.out.println("Health: "+(player.getHealth() < 0 ? 0 : player.getHealth())+", Max Health: "+player.getMaxHealth()+
	            		", Minimum Damage: "+player.getMinDmg()+", Maximum Damage: "+player.getMaxDmg()+"\n");
			} else {
				System.out.println("The "+monster.getType()+" has defeated "+player.getName());
			}
		}
	}

	/**
	 * The player's turn
	 * @param player is the player
	 * @param monster is the monster that the player is fighting against
	 * @param scan is the scanner
	 */
	public static void playerTurn(Player player, Monster monster, Scanner scan) {
		//player choice
		System.out.println("Player inventory: "+player.getInventory());
		System.out.println("Player wearables: "+Arrays.toString(player.getWearables()));
		System.out.print("Type an inventory slot (1-n) to use the item in that slot or 0 to attack: \n");
		String userInput = scan.next();
		System.out.println("\n");
		int action = 0;
		if (!validityCheck(userInput)) { //if not a valid choice
			System.out.println("Invalid choice - Choosing attack for the player");
		} else {
			if (continuePlaying) {
				action = Integer.parseInt(userInput);
			}
		}
		//applying the action that the player chose
		int damage = 0;
		if (continuePlaying) {
			if (action == 0) {
				damage = player.attack(monster);
				System.out.println(player.getName()+" attacks the "+monster.getType()+" for "+damage+" damage");
				System.out.println("The "+monster.getType()+" has "+((monster.getHealth() < 0) ? 0 : monster.getHealth())+" health left\n");
			} else {
				player.useItem(action-1);
			}
		} else {
			System.err.println("You are quitting?\nSo be it\n");
		}
	}
	
	/**
	 * The monster's turn
	 * @param player is the player
	 * @param monster is the monster that the player is fighting against
	 */
	public static void monsterTurn(Player player, Monster monster) {
		System.out.println();
		int damage = 0;
		
		if (Math.random() < 0.1) {
			monster.levelUp();
			System.out.println("The "+monster.getType()+" has randomly leveled up");
			System.out.println("The "+monster.getType()+" is now level "+monster.getLevel()+"\n");
		}
		
		//setting monster equal to witch given the witch's different method of attack
		if (monster instanceof Witch) { //does the attack specific to the witch
            Witch witchMonster = (Witch) monster; 
            witchMonster.applyEffect(player);
            
            System.out.println("The player has the following stats after the attack:");
            System.out.println("Health: "+(player.getHealth() < 0 ? 0 : player.getHealth())+", Max Health: "+player.getMaxHealth()+
            		", Minimum Damage: "+player.getMinDmg()+", Maximum Damage: "+player.getMaxDmg());
        } else { //does a regular attack if the monster is not a witch
            damage = monster.attack(player);
            System.out.println(monster.getType()+" attacks "+player.getName()+" for "+damage+" damage");
			System.out.println(player.getName()+" has "+(player.getHealth() < 0 ? 0 : player.getHealth())+" health left");
        }
	}
	
	public static Item getLoot(Player player) {
		double levelRoll = Math.random();
		//changed the percentage chances of getting each level for balancing reasons
		int level = 0;
		if (levelRoll <= 0.05) { 
			level = (int) (Math.random()*2+9); //legendary
		} else if (levelRoll <= 0.2) {
			level = (int) (Math.random()*2+7); //epic
		} else if (levelRoll <= 0.4) {
			level = (int) (Math.random()*2+5); //greater
		} else if (levelRoll <= 0.7) {
			level = (int) (Math.random()*2+3); //basic
		} else {
			level = (int) (Math.random()*2+1); //lesser
		}
		
		String type = "";
		double typeRoll = Math.random();
		if (player instanceof Mage) {
			if (typeRoll <= 0.2) {
				type = "Mana Potion";
			}
		}
		if (typeRoll <= 0.4 && typeRoll > 0.2) {
			type = "Strength Potion";
		} else if (typeRoll > 0.4 && typeRoll < 0.5) {
			type = "Vitality Potion";
		} else if (typeRoll >= 0.5 && typeRoll < 0.6) {
			type = "Rune Stone";
		}
		
		//if rune stone, then chooses random buff and applies it to the player
		if (type.equals("Rune Stone")) {
			//30% vitality, 35% strength, 35% health
			type = Math.random() > 0.7 ? "Vitality Potion" : 
				Math.random() > 0.5 ? "Strength Potion" : "Health Potion";
			System.out.println(player.getName()+" recived a "+type.substring(0,type.length()-7).toLowerCase()+" rune stone");
			System.out.println("The "+type.substring(0,type.length()-7).toLowerCase()+" rune stone granted the player extra "+type.substring(0,type.length()-7).toLowerCase());
			player.recieveItem(new Item(type, level));
			player.useItem(player.getInventoryActual().size()-1);
			type = "";
		}
		
		if (type.length() == 0) {
			type = "Health Potion";
		}
		
		return new Item(type,level);
	}
	
	private static Wearable getWearable(Player player) {
		double levelRoll = Math.random();
		//changed the percentage chances of getting each level for balancing reasons
		int level = 0;
		if (levelRoll <= 0.05) { 
			level = (int) (Math.random()*2+9); //legendary
		} else if (levelRoll <= 0.2) {
			level = (int) (Math.random()*2+7); //epic
		} else if (levelRoll <= 0.4) {
			level = (int) (Math.random()*2+5); //greater
		} else if (levelRoll <= 0.7) {
			level = (int) (Math.random()*2+3); //basic
		} else {
			level = (int) (Math.random()*2+1); //lesser
		}
		
		String type = "";
		double typeRoll = Math.random();
		if (typeRoll >= 0.6) {
			type = "armor";
		} else if (typeRoll >= 0.2) {
			type = "weapon";
		} else {
			type = "amulet";
		}
		
		String name = "";
		if (type.equals("armor")) {
			name = "Adventurer's Armor Set";
		} else if (type.equals("weapon")) {
			name = "Knight's Sword";
		} else if (type.equals("amulet")) {
			name = "Eye of the Turtle Amulet";
		} else {
			name = "Unkown Wearable";
		}
		
		return new Wearable(name, type, level);
	}
	
	/**
	 * @returns the user's choice to continue to play
	 */
	public static boolean isContinuingToPlay() {
		return Battle.continuePlaying;
	}
}
