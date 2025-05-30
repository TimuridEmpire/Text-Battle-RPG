package game.textbasedRPG;

import java.util.Scanner;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Battle {
	
	private static boolean continuePlaying = true;
	
	/**
	 * The 
	 * @param player is the player
	 * @param scan is the scanner
	 * @throws denotes the exceptions that could be thrown
	 */
	public static void haveBattle(Player player, Scanner scan, String mode) 
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
	{
		Monster monster = createRandomMonster(player, mode);
		startBattle(player, monster, scan);
	}
	
	
	
	/**
	 * Dynamically creates a random new monster
	 * @returns the monster that is created
	 * @throws exceptions are all based on if the class that we are trying to access exists and has a valid constructor
	 */
	public static Monster createRandomMonster(Player player, String mode) 
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
	{
		String[][] monsterTypes = {{"Zombie","Slime","Witch","Giant"},
				{"Dragon", "Troll","Demon"}};
		Random randomChoice = new Random();
		String packageName = "game.textbasedRPG.";
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
	        
	        /* level 1 = 50%, level 2 = 30%, level 3 = 14%, level 4 = 4.8%, level 5 = 1.2% for a player of level 1 
			 * level 1 = 30%, level 2 = 35%, level 3 = 21%, level 4 = 11.2%, level 5 = 2.8% for a player of level 2 
			 * level 1 = 20%, level 2 = 32%, level 3 = 24%, level 4 = 16.8%, level 5 = 7.2% for a player of level 3
			 * level 1 = 0%, level 2 = 20%, level 3 = 32%, level 4 = 28%, level 5 = 19.2% for a player of level 4-5  
			 * level 1 = 0%, level 2 = 10%, level 3 = 27%, level 4 = 37.8%, level 5 = 25.2% for a player of level 6 
			 * level 1 = 0%, level 2 = 0%, level 3 = 30%, level 4 = 35%, level 5 = 35% for a player of level 7 
			 * level 1 = 0%, level 2 = 0%, level 3 = 20%, level 4 = 40%, level 5 = 40% for a player of level 8 
			 * level 1 = 0%, level 2 = 0%, level 3 = 10%, level 4 = 36%, level 5 = 54% for a player of level 9-11 
			 * level 1 = 0%, level 2 = 0%, level 3 = 0%, level 4 = 40%, level 5 = 60% for a player of level 12
			 * level 1 = 0%, level 2 = 0%, level 3 = 0%, level 4 = 30%, level 5 = 70% for a player of level 13-15
			 * level 1 = 0%, level 2 = 0%, level 3 = 0%, level 4 = 20%, level 5 = 80% for a player of level 16-20
			 */ //alternate method of monster level scaling
			/* int monsterLevel = ((int) (Math.random()*10+1) > (int) (5*Math.sqrt(player.getLevel())) ? 1 : 
				(int) (Math.random()*10+1) > (int) (4*Math.sqrt(player.getLevel())) ? 2 : 
					(int) (Math.random()*10+1) > (3*Math.sqrt(player.getLevel())) ? 3 : (int) (Math.random()*10+1) > (2*Math.sqrt(player.getLevel())) ? 4 : 5);
					*/
	        
	        //average battle should last around 5 rounds
	        
	        //monster hp scales with player damage
	        double randomFactor = Math.random()*0.25+0.75; // 0.75–1
	        int monsterHP = (int) Math.random()*10+11; //from 10-20
	        if (player.getLevel() > 1) {
	        	monsterHP = (int) (player.getMinDmg()*(player.getLevel()*0.75)*randomFactor); //fights scale in time as game progresses
	        	monsterHP *= player.getLevel() >= 10 ? 0.75 : 1; //correction for the hp with the higher levels
	        }
	        

	        //damage is average of current and max health of the player divided by an arbitrary factor
	        double avgMonsterDmg = (player.getMaxHealth()+player.getHealth()/2.0)/7.5;
	        
	        //both hp and damage are adjusted for monster level relative to player level (however it is more forgiving if monster is more powerful)
	        double statScalar = Math.min((((double) player.getLevel())/monsterLevel), 1.1);
	        monsterHP *= statScalar > 1 ? statScalar : statScalar*0.7;
	        avgMonsterDmg *= statScalar > 1 ? statScalar : statScalar*0.8;

	        //Giving the monster slight randomness
	        double dmgVariance = .2; //20% variance
	        int monsterMinDmg = (int) (avgMonsterDmg*(1.0-dmgVariance));
	        int monsterMaxDmg = (int) (avgMonsterDmg*(1.0+dmgVariance));
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
	public static void startBattle(Player player, Monster monster, Scanner scan) {
		int round = 1;
		System.out.println("\n"+player.getName()+" has encountered a level "+monster.getLevel()+" "+monster.getType()+"\n");
		while (player.getIsAlive() && monster.getIsAlive()) { //gameplay loop for battle terminates once either the player or monster are dead
			//prints the demarcation for the rounds
			for (int i = 0; i < 40; i++) { System.out.print("*"); }
			System.out.print("Round "+round);
			for (int i = 0; i < 40; i++) { System.out.print("*"); }
			System.out.println("\n");
			
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
			//print the results once someone has died
			System.out.println();
			if (player.getIsAlive()) {
				System.out.println(player.getName()+" has defeated the "+monster.getType());
				
				Item loot = getLoot(player);
				player.recieveItem(loot);
				System.out.println(player.getName()+" has been rewarded with a "+loot);
				
				int healAmount = (int) (Math.random()*10+11); //random number between 10 and 20 inclusive
				
				player.levelUp(monster.getLevel());
				System.out.println("The player has leveled up");
				
				player.healDamage(healAmount); //heals the player
				System.out.println(player.getName()+" has been rewarded "+healAmount+" extra points of health");
				
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
			System.out.println("The "+monster.getType()+" has seemingly randomly leveled up\n");
			monster.levelUp();
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
			System.out.println(player.getName()+" recived a "+type+" rune stone");
			System.out.println("The "+type+" rune stone granted the player extra "+type.substring(0,type.length()-7).toLowerCase());
			player.recieveItem(new Item(type, level));
			player.useItem(player.getInventoryActual().size()-1);
			type = "";
		}
		
		if (type.length() == 0) {
			type = "Health Potion";
		}
		
		return new Item(type,level);
	}
	
	/**
	 * @returns the user's choice to continue to play
	 */
	public static boolean isContinuingToPlay() {
		return Battle.continuePlaying;
	}
}
