package game.textbasedRPG.entityclasses.playerclasses;

import java.util.ArrayList;

import game.textbasedRPG.Item;

public class Tank extends Warrior {
	
	public Tank(String name, int health, int minDmg, int maxDmg, ArrayList<Item> inventory, int level) {
			super(name, (int) (health*(Math.random()*0.5+1.1)), minDmg, maxDmg);
			this.setInventory(inventory);
			this.setLevel(level);
	}
}
