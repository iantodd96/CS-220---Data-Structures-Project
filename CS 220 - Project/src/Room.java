import java.text.DecimalFormat;
import java.util.ArrayList;

public class Room {
	
	//Declaring variables.
	Room left;
	Room right;
	Room back;
	String description;
	double temperature;
	ArrayList<Item> contents = new ArrayList<Item>();
	
	DecimalFormat f = new DecimalFormat("##.##");

	//A simple room constructor that only specifies the temperature.
	//Links can be made later with setters.
	public Room(String description, double temperature){
		this.description = description;
		this.temperature = temperature;
	}

	//A more complex Room constructor that can declare a south, north, west room, and east room, as well as the temperature (measured in Celsius) and contents.
	//The caves the player explores are designed as a graph data structure with three directions (back, left, and right).
	public Room(Room left, Room right, Room back, String description, double temperature){
		this.left = left;
		this.right = right;
		this.back = back;
		this.description = description;
		this.temperature = temperature;
	}

	//Item related methods.

	//Adds an item to the room.
	public void addItem(Item newItem){
		contents.add(newItem);
	}

	public void removeItem(int index){
		contents.remove(index);
	}
	
	//Gets the item at the specified index.
	public Item getItem(int index){
		return contents.get(index);
	}
	
	public int getNumberOfItems(){
		return contents.size();
	}

	//To be used by other classes to determine if there are items.
	public boolean hasItems(){
		if (!contents.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	//This is used to alert the player if there are items in their current room.
	public void alertItems(){
		if (hasItems()){
			System.out.println("There are some things you could take in this location.");
			System.out.println("");
		}
	}

	//Gets a description of the current room. This should be run when the player enters a room.
	public String getDescription(){
		return description;
	}
	
	//Graph related methods.

	//This method isn't used, but could be for shifting rooms around.
	public void setLeft(Room newLeft){
		left = newLeft;
	}
	
	public Room getLeft(){
		return left;
	}

	//This method isn't used, but could be for shifting rooms around.
	public void setRight(Room newRight){
		right = newRight;
	}
	
	public Room getRight(){
		return right;
	}
	
	public void setBack(Room newBack){
		back = newBack;
	}
	
	public Room getBack(){
		return back;
	}

	public double getTemperature(){
		return temperature;
	}

	//Sets a new temperature for the room. This can be caused by the usage of an Ice Bomb.
	public void setTemperature(double newTemperature){
		temperature = newTemperature;
	}

	//This lists all items in the room.
	public void showItems(){
		//If there are no items in the room, the player is notified.
		if (contents.isEmpty()){
			System.out.println("There is nothing of interest in this area.");
		}

		//If there are items in the room, they are listed and numbered. This is so if the player wants to take an item,
		//they can do so through typing a number rather than the whole item name.
		if (!contents.isEmpty()){
			double lbs;
			System.out.println("You see the following objects in this area:");
			System.out.println("");
			for (int count = 0; count < contents.size(); count++){
				lbs = contents.get(count).getWeight() * 2.2;
				System.out.print(count + 1 + ".) "); 
				System.out.println(contents.get(count).getName() + " - " + contents.get(count).getWeight() + " kg. (" + f.format(lbs) + " lbs.)");
			}
			System.out.println("");
		}
	}

}
