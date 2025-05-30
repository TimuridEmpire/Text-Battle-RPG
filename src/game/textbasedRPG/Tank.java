package game.textbasedRPG;

import java.util.ArrayList;

public class Tank extends Warrior {
	
	public Tank(String name, int health, int minDmg, int maxDmg, ArrayList<Item> inventory, int level) {
			super(name, (int) (health*(Math.random()*0.5+1.1)), minDmg, maxDmg);
			this.setAdvancedInventory(inventory);
			this.setLevel(level);
	}
}
