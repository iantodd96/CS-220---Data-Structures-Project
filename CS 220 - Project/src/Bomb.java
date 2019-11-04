
public class Bomb extends Item{

	Monster target;
	double roomTempChange;
	int stunValue;

	public Bomb(String name, String description, double weight, Monster target, double roomTempChange, int stunValue) {
		super(name, description, weight);
		this.target = target;
		this.roomTempChange = roomTempChange;
		this.stunValue = stunValue;
	}

	public Monster getTarget(){
		return target;
	}
	
	public double getRoomTempChange(){
		return roomTempChange;
	}

	public int getStunValue(){
		return stunValue;
	}

	//These methods are for the "use" command. They simply state what the item's type is and is not
	//so the item will be used in the proper manner, preventing the game from crashing.

	//Since this method is true, the item will be used as a bomb that can stun or even kill the monster.
	//The special monster killing bomb is found in the deepest part of the caves.
	public boolean isBomb(){
		return true;
	}

	public boolean isEquipment(){
		return false;
	}

	public boolean isConsumable(){
		return false;
	}
}
