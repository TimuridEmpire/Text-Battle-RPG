package game.textbasedRPG.entityclasses.playerclasses;

import java.util.ArrayList;

import game.textbasedRPG.Item;

public class Brawler extends Warrior {
	
	public Brawler(String name, int health, int minDmg, int maxDmg, ArrayList<Item> inventory, int level) {
		super(name, health, 
				(int) (minDmg*(Math.random()*0.5+1.1)), 
				(int) (maxDmg*(Math.random()*0.5+1.1)));
		this.setInventory(inventory);
		this.setLevel(level);
}
}
