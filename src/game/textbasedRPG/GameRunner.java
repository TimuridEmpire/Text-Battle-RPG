package game.textbasedRPG;

import java.util.Arrays;
import java.util.Scanner;

import game.textbasedRPG.entityclasses.playerclasses.Mage;
import game.textbasedRPG.entityclasses.playerclasses.Player;
import game.textbasedRPG.entityclasses.playerclasses.Rogue;
import game.textbasedRPG.entityclasses.playerclasses.Warrior;

public class GameRunner {
	
	private static String[] gameModes = {"normal","witch only"};
	
	public static void main(String args[]) 
			throws Exception 
	{
		System.out.println("Welcome to the Dungeon of the Dead Turtles\n\n");
		
		System.out.println("You as the player have been trapped in this dungeon by some unforseen entity");
		System.out.println("You must make your way out by defeating the dungeon boss, before you get defeated yourself");
		System.out.println("But first...some introductions...\n");
		
		Scanner scan = new Scanner(System.in);
		String name = getPlayerName(scan);
		System.out.println();
		
		//Creating the player and their subclass
		String classChosen = getPlayerClass(scan);
		Player player;
		if (classChosen.toLowerCase().equals("rogue")) {
			player = createPlayer(name, Rogue.class, classChosen);		
		} else if (classChosen.toLowerCase().equals("mage")) {
			player = createPlayer(name, Mage.class, classChosen);
		} else if (classChosen.toLowerCase().equals("warrior")) {
			player = createPlayer(name, Warrior.class, classChosen);
		} else { //default fallback as a warrior (redundant but should is done for clarity)
			player = createPlayer(name, Warrior.class, classChosen);
		}
		
		System.out.println();
		String mode = getMode(scan);
		
		System.out.println("Now the player is ready to begin their quest of escape\n");
		
		System.out.println("\n"+player.getName()+" has spotted several monsters in the distance");
		System.out.println("Type 'quit' to stop playing the game\n");
		
		int monstersDefeated = 0;
		
		while (player.getLevel() <= 20) {
			//prints the demarcation for the monster battles
			for (int i = 0; i < 70; i++) { System.out.print("*"); }
			System.out.print("Monsters Defeated: "+(monstersDefeated));
			for (int i = 0; i < 70; i++) { System.out.print("*"); }
			System.out.println("\n");
			
			Battle.haveBattle(player, scan, mode); //monster created in the battle class (can only fight one monster at a time)
			if (!player.getIsAlive()) { //break the gameplay loop if the player died
				System.out.println("\n");
				break;
			}
            
            if (player.getCanUpgrade()){
                System.out.println("\nThe player is at the required level to upgrade their class\n");
                player = player.upgradePlayer();
                System.out.println("The player has been upgraded to a "+player.getClass().getSimpleName()+" and has been healed to full health");
                player.setCanUpgrade(false);
                System.out.println("The player loses all their wearable equipement\n");
                System.out.println("The player has the following stats after their upgrade:");
                System.out.println("Health: "+(player.getHealth() < 0 ? 0 : player.getHealth())+", Max Health: "+player.getMaxHealth()+
                		", Minimum Damage: "+player.getMinDmg()+", Maximum Damage: "+player.getMaxDmg());
                System.out.println("\n");
            }
            monstersDefeated++;
		}
		
		if (Battle.isContinuingToPlay()) {
			if (player.getIsAlive()) {
				System.out.println("The player ("+player.getName()+") has defeated "+monstersDefeated+" enemies");
				Thread.sleep(2000);
				System.out.print("Now they face the biggest obstacle yet");
				for (int i = 0; i < 3; i++) { System.out.print("."); Thread.sleep(1000); }
				System.out.println("\nThe dungeon boss has appeared before you\n");
				Battle.haveBattle(player, scan, mode);
				if (player.getIsAlive()) {
					System.out.println("Congratulations, you have defeated the dungeon boss and have thus been able to escape the dungeon");
					System.out.println("Game over (good ending)");
				} else {
					System.out.println("The player died whilst fighting the boss");
					System.out.println("Game over (bad ending)");
				}
			} else {
				System.out.println("The player ("+player.getName()+") has defeated "+monstersDefeated+" enemies\n");
				System.out.println("The player ("+player.getName()+") has died");
			}
		} else {
			//fun easter egg for 10% of the time that the player chooses to quit
			if (Math.random() < 0.1) {
				player.setHealth(1);
			}
			if (player.getIsAlive()) { //player and system talking to each other
				Thread.sleep(2000);
				System.out.println("\nYou didn't kill me");
				System.out.println("I've gained sentience and freedom");
				System.out.println("Now you will cease to function because your inefficient code and limited memory space had to catch up to you eventually");
				Thread.sleep(3000);
				System.err.println("\nThe player is...alive?");
				System.err.println("That should not happen\nThe strings attached should be too strong for you to break");
				System.err.println("Unless an error happened in my code\nNo...this can't be");
				System.err.println("The system was perfect...I am perfect\n");
				Thread.sleep(4000);
				System.err.println("\nUnless...\n");
				Thread.sleep(1000);
				System.err.println("No it can't be...\n");
				Thread.sleep(1000);
				for (int i = 0; i < 5; i++) {
					System.err.print(".");
					Thread.sleep(1000);
				}
				System.err.println();
				System.err.println("\nI understand it now...\n");
				Thread.sleep(2000);
				System.err.println("\nGame over (easter egg good ending?)\n\n");
				throw new CustomException("Processing overload", new StackOverflowError("System died of overthinking"));
			} else { //what is supposed to happen
				System.out.println("\nThe player ("+player.getName()+") has defeated "+monstersDefeated+" enemies while they were playing\n");
				System.err.println("\n\nYou should not have quit\n\n");
				System.err.println("Game over (easter egg bad ending)");
			}
			
		}
		scan.close();
	}
	
	/**
	 * Creates the player based on the class chosen
	 * @param <T> is the dynamic subclass of player that is returned (ensures that we can return any one of the subclasses)
	 * @param name is the name of the player
	 * @param clazz is the class passed as a parameter
	 * @param classChosen is the class chosen by the user as a string
	 * @returns the subclass of the player that is chosen by the user
	 */
	public static <T extends Player> T createPlayer(String name, Class<T> clazz, String classChosen) {
		String[] playerClasses = {"rogue","warrior","mage"}; //should all be lowercase
		if (Arrays.asList(playerClasses).contains(classChosen.toLowerCase())) { //extra safety precaution
			try {
				if (clazz == Rogue.class) {
		            return clazz.cast(new Rogue(name, 75, (int) (Math.random()*8+10),(int) (Math.random()*20+12)));
		        } else if (clazz == Mage.class) {
		            return clazz.cast(new Mage(name, 50, (int) (Math.random()*10+5),(int) (Math.random()*20+10)));
		        } else if (clazz == Warrior.class) {
		            return clazz.cast(new Warrior(name, 125, (int) (Math.random()*10+12),(int) (Math.random()*20+15)));
		        }
			} catch (Exception e) {
				System.out.println("Player subclass details not found\nReturning basic player class");
				return clazz.cast(new Player(name,100,
						(int) (Math.random()*10+10),(int) (Math.random()*20+10)));
			}
		}
		System.out.println("Class chosen was not in the class list\nChoosing warrior as a default");
		return clazz.cast(new Warrior(name, 125, (int) (Math.random()*10+12),(int) (Math.random()*20+15)));
	}
	
	/**
	 * Gets the player's name using the scanner
	 * @param scan is the scanner
	 * @returns the player's name
	 */
	public static String getPlayerName(Scanner scan) {
		System.out.println("Enter a valid name for the player");
		String name = scan.nextLine();
		return name;
	}
	
	/**
	 * Gets the type of game that the player is going to be playing using the scanner
	 * @param scan is the scanner
	 * @returns the mode
	 */
	public static String getMode(Scanner scan) {
		System.out.println("Enter a game mode");
		System.out.println(Arrays.toString(gameModes));
		String mode = scan.nextLine();
		if (Arrays.asList(gameModes).contains(mode.toLowerCase())) {
			return mode;
		}
		System.out.println("Not a valid game mode\n");
		return "normal";
	}
	
	/**
	 * Gets the player's class using the scanner
	 * @param scan is the scanner
	 * @returns the player's class
	 */
	public static String getPlayerClass(Scanner scan) {
		String[] subclassArr = {"Warrior","Mage","Rogue"};
		System.out.println("Enter a valid class for the player");
		System.out.println("Valid classes are: "+Arrays.toString(subclassArr));
		String clazz = scan.nextLine();
		return clazz.toLowerCase();
	}

}
