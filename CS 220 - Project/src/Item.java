
public class Item {

	//Declaring variables
	String name;
	String description;
	double weight;
	
	//Item constructor. Contains the name, description, and property of the item.
	//Weight is measured in kilograms.
	public Item(String name, String description, double weight){
		this.name = name;
		this.description = description;
		this.weight = weight;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public double getWeight(){
		return weight;
	}
	
	//These methods are for the "use" command. They simply state what the item's type is and is not
	//so the item will be used in the proper manner, preventing the game from crashing.
	//However, since this is just a generic item, the use command will state that the item can't be used.
	
	public boolean isConsumable(){
		return false;
	}
	
	public boolean isEquipment(){
		return false;
	}
	
	public boolean isBomb(){
		return false;
	}
	
}
