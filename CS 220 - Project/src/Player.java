import java.util.Scanner;

import java.text.DecimalFormat;
import java.util.ArrayList;
public class Player {

	//This private node class is for the deque data structure that will be used to allow the monster to track the player down.
	private class Node<T>{
		T data;
		Node<T> nextNode;
		Node<T> previousNode;

		Node(T data){
			this.data = data;
		}

		Node(T data, Node<T> nextNode, Node<T> previousNode){
			this.data = data;
			this.nextNode = nextNode;
			this.previousNode = previousNode;
		}

		public T getData(){
			return data;
		}

		public void setData(T newData){
			data = newData;
		}

		public Node<T> getNext(){
			return nextNode;
		}

		public void setNext(Node<T> newNext){
			nextNode = newNext;
		}

		public Node<T> getPrevious(){
			return previousNode;
		}

		public void setPrevious(Node<T> newPrevious){
			previousNode = newPrevious;
		}

	}

	//Declaring variables.
	Room currentRoom;
	//Body temperature is measured in Celsius, but is also converted to Fahrenheit and displayed as that.
	double bodyTemp;
	double tempResistValue;
	Equipment chest;
	Equipment legs;
	int health;
	int maxHealth = 100;
	boolean gameOver;

	//Nodes for saving the player's path in a deque data structure. The monster will use it to track the player.
	Node<Room> back;
	Node<Room> front;

	//IMPORTANT: You can't start the game while carrying items that have weight, or else the weight system won't work properly.
	int currentCarryWeight = 0;
	int maxCarryWeight = 45;

	DecimalFormat f = new DecimalFormat("##.##");

	ArrayList<Item> inventory = new ArrayList<Item>();

	public Player(Room startRoom, int startHealth, Equipment chest, Equipment legs){
		currentRoom = startRoom;
		this.health = startHealth;
		this.chest = chest;
		this.legs = legs;
		bodyTemp = 37;
		health = 100;
		gameOver = false;

		//Setting the front and back nodes of the player's scent path for the initial turn.
		//See the addToScentPath() method below.
		Node<Room> temp = new Node<Room>(startRoom);
		back = temp;
		front = temp;

		playerTurn();
	}

	//Not all of these methods are used, but included for consistency, and also in case they are needed later.
	public boolean gameIsOver(){
		return gameOver;
	}

	public void setTempResistValue(double newTempResistValue){
		tempResistValue = newTempResistValue;
	}

	public double getTempResistValue(){
		return tempResistValue;
	}

	public Equipment getChestEquip(){
		return chest;
	}

	public void setChestEquip(Equipment newEquip){
		chest = newEquip;
	}

	public Equipment getLegsEquip(){
		return legs;
	}

	public void setLegsEquip(Equipment newEquip){
		legs = newEquip;
	}

	public double getWeight(){
		return currentCarryWeight;
	}

	public void setTempResist(double newTempResist){
		tempResistValue = newTempResist;
	}

	public Room getCurrentRoom(){
		return currentRoom;
	}

	public boolean hasItems(){
		return !inventory.isEmpty();
	}

	//The scent that the player leaves for the monster to track. To be used when the player moves from room to room.
	//This is stored in a deque data structure using the private node class above.
	public void addToScentPath(Room newRoom){
		Node<Room> temp = new Node<Room>(newRoom);
		if (back == front){
			temp.setPrevious(front);
			front.setNext(temp);
			front = temp;
			front.setPrevious(back);
		}
		if (back != front){
			temp.setPrevious(front);
			front.setNext(temp);
		}
	}
	
	//Removes and returns the back room from the deque. The monster will move to that room.
	public Room removeBack(){
		if (back != null){
			Node<Room> temp = back;
			back = null;

			return temp.getData();
		}
		//Just to ensure no errors occur in case the back room becomes null.
		else {
			return currentRoom;
		}
	}

	//For if the player is attacked or healed. This method also checks if the player is dead or has gone over their maximum health.	
	public void changeHealthBy(int change){
		health += change;
		if (health > maxHealth){
			health = maxHealth;
		}
		if (health <= 0){
			gameOver = true;
		}
	}
	
	//A simple method that changes the player's body temperature. This is usually invoked by consumable items.
	public void changeBodyTempBy(double change){
		bodyTemp += change;
	}

	public void showItems(){
		//If the player is not carrying any items, they are notified.
		if (!hasItems()){
			System.out.println("You aren't carrying anything right now.");
		}

		//If there are items in the player's inventory, they are listed and numbered.
		//This is so if the player wants to inspect an item, they can do so through typing a number rather than the whole item name.
		if (hasItems()){
			double lbs;
			System.out.println("You are carrying these objects:");
			System.out.println("");
			for (int count = 0; count < inventory.size(); count++){
				lbs = inventory.get(count).getWeight() * 2.2;
				System.out.println("	" + (count + 1) + ".) " + inventory.get(count).getName() + " - " + inventory.get(count).getWeight() + " kg. (" + f.format(lbs) + " lbs.)");
				System.out.println("");
			}
			System.out.println("	" + "Total weight: " + currentCarryWeight + "kg /" + maxCarryWeight + "kg.");
			System.out.println("");
		}
	}

	//The player can't directly see their temperature, they can only tell how they currently feel.
	//This method will print out messages that will give the player an idea of how dangerous their temperature situation is.
	public void getBodyTempFeeling(){

		//If statements were required here because switch case doesn't work with doubles.
		//The measurements must be doubles in order for the temperature system to be fair.

		//Being colder than 33 degrees is instantly fatal.
		if (bodyTemp < 34 && bodyTemp >= 33){
			System.out.println("TEMPERATURE: Freezing!!! (DANGER!)");
		}
		if (bodyTemp < 35 && bodyTemp >= 34){
			System.out.println("Temperature: Bitterly Cold!");
		}
		if (bodyTemp < 36 && bodyTemp >= 35){
			System.out.println("Temperature: Cold.");
		}
		if (bodyTemp < 36.5 && bodyTemp >= 36){
			System.out.println("Temperature: Chilly.");
		}
		if (bodyTemp >= 36.5 && bodyTemp <= 37.5){
			System.out.println("Temperature: Comfortable");
		}
		if (bodyTemp > 37.5 && bodyTemp < 38){
			System.out.println("Temperature: Warm.");
		}
		if (bodyTemp >= 38 && bodyTemp < 39){
			System.out.println("Temperature: Hot.");
		}
		if (bodyTemp >= 39 && bodyTemp < 40){
			System.out.println("Temperature: Sweltering!");
		}
		if (bodyTemp >= 40 && bodyTemp <= 41){
			System.out.println("TEMPERATURE: Scorching!!! (DANGER!)");
		}
		//Being hotter than 41 degrees is instantly fatal. (See doTemperatureEffects() method)
	}

	//Runs the effects of the player's natural body heating/cooling processes and the effects of the room's temperature on the player's temperature.
	public void doTemperatureEffects(){

		double result = 0;


		//The player's body will always try to stay at 37 degrees on its own, unless the player is too cold.
		//If the player's temperature drops below 35, their body will stop warming on its own due to hypothermia.
		//
		//This system is mostly geared towards dealing with the heat, which is why the player
		//can withstand being cold unrealistically well. A player could discover they can cool themselves far below
		//their normal body temperature intentionally to last longer in the heat.
		if (bodyTemp < 37 && bodyTemp >= 35){
			if (37 - bodyTemp < 0.01){
				result = 37 - bodyTemp;
			}
			else{
				result = 0.01;
			}
		}
		if (bodyTemp > 37){
			if (37 - bodyTemp >= -0.1){
				result = 37 - bodyTemp;
			}
			else{
				result = -0.1;
			}
		}

		//These if statements are for rooms that are in the "safe range" (Between 10 to 38 degrees C).
		if (currentRoom.getTemperature() > 10 && currentRoom.getTemperature() < 38 && bodyTemp > 37){
			result = (result + (currentRoom.getTemperature() * -0.02));
		}
		if (currentRoom.getTemperature() > 10 && currentRoom.getTemperature() < 38 && bodyTemp < 37){
			result = (result + (currentRoom.getTemperature() * 0.02));
		}

		//These if statements are for rooms that are too cold or too hot (10 C or less, or 38 C or greater).
		if (currentRoom.getTemperature() <= 10){
			result = (result + (currentRoom.getTemperature() * -0.01));
		}
		if (currentRoom.getTemperature() >= 38){
			result = (result + (currentRoom.getTemperature() * 0.005));
		}


		//Checks whether to multiply the result by the resistance percentage.
		if (tempResistValue != 0){
			result = result * tempResistValue;
		}

		bodyTemp += result;

		//If statements to check if the player is at a fatal temperature.
		if (bodyTemp > 41){
			System.out.println("You're toast!");
			changeHealthBy(-10000);
		}
		if (bodyTemp < 33){
			System.out.println("You've turned into an ice sculpture!");
			changeHealthBy(-10000);
		}

	}

	//This is a very large chunk of code. Tries to match the player's input to a known command using a switch statement.
	public void playerTurn(){

		//At the start of each turn, a description of the room will be displayed, as well as an alert showing if the room has any items.
		System.out.println(currentRoom.getDescription());
		System.out.println("");
		currentRoom.alertItems();
		
		double farenheit = ((currentRoom.getTemperature() * 9/5) + 32);
		//Since temperature is an important part of the game, the player always needs to know how their body temperature feels and the room's temperature.
		System.out.println("Thermometer: " + currentRoom.getTemperature() + " °C " + "(" + f.format(farenheit) + " °F)");
		System.out.print("HP: " + health + " // ");
		getBodyTempFeeling();
		System.out.println("");



		//Declaring variables.
		boolean turnCompleted = false;

		while (turnCompleted == false){

			if (health <= 0 || bodyTemp < 33 || bodyTemp > 41){
				turnCompleted = true;
			}

			Scanner scan = new Scanner(System.in);
			String input;

			System.out.println("");
			System.out.println("What would you like to do next? Type \"help\" for a list of all available commands.");

			input = scan.nextLine();
			System.out.println("");

			//Checks to see if the user's input matches one of the commands.
			switch (input){
			case ("help"):{ System.out.println("Available commands: \n"
					+ "left, right, back - Moves you in the given direction. (Ends your turn.) \n"
					+ "look - View the contents of the room you're in. (Does not end your turn.) \n"
					+ "take - Take an item from the room you're currently in. (Ends your turn.) \n"
					+ "inventory - View the contents of your inventory. (Does not end your turn.)\n"
					+ "use - Uses an item. How it is used depends on what type of item it is. (Ends your turn.)\n"
					+ "self - Get more detailed information about your current status. (Does not end your turn.) \n"
					+ "wait - Do nothing. (Ends your turn, of course!) \n"
					+ "quit - Give up all of your progress and exit the game.");
			break;
			}
			// Cases for movement commands.
			case ("left"):{
				if (currentRoom.getLeft() == null){
					System.out.println("You can't go left from here.");
					System.out.println("");
				}
				if (currentRoom.getLeft() != null){
					currentRoom = currentRoom.getLeft();
					addToScentPath(currentRoom);
					System.out.println("You go left.");

					turnCompleted = true;
				}
				break;
			}
			case ("right"):{
				if (currentRoom.getRight() == null){
					System.out.println("There's no way to go right from this area.");
					System.out.println("");
				}
				if (currentRoom.getRight() != null){
					currentRoom = currentRoom.getRight();
					addToScentPath(currentRoom);
					System.out.println("You go right.");
					turnCompleted = true;
				}
				break;
			}
			case ("back"):{
				if (currentRoom.getBack() == null){
					System.out.println("You can't go back from this area.");
					System.out.println("");
				}
				if (currentRoom.getBack() != null){
					currentRoom = currentRoom.getBack();
					addToScentPath(currentRoom);
					System.out.println("You go back.");

					turnCompleted = true;
				}
				break;
			}

			//Command for looking at a room's contents.
			case ("look"):{
				System.out.println(currentRoom.getDescription());
				System.out.println("");
				if (currentRoom.hasItems()){
					currentRoom.showItems();
				}
				if (!currentRoom.hasItems()){
					System.out.println("You don't notice any items you could take around here.");
				}
				break;
			}

			//Command for taking an item from your current room.
			case ("take"):{
				//If the room has no items, this message is displayed.
				if (!currentRoom.hasItems()){
					System.out.println("There's nothing useful around here that you can take.");
				}
				//If the current room has items, the player is allowed to take one.
				if (currentRoom.hasItems()){
					currentRoom.showItems();
					try{
						System.out.println("Enter the list number of the item you'd like to take, or enter 0 to cancel");
						int num = scan.nextInt();
						if (num <= 0){
							System.out.println("Okay, never mind.");
							break;
						}
						if (num > currentRoom.getNumberOfItems()){
							System.out.println("That number is invalid.");
							break;
						}
						if ((currentCarryWeight + currentRoom.getItem(num - 1).getWeight()) > maxCarryWeight){
							System.out.println("That item is too heavy to add to your inventory! You'll need to drop some things to take that with you.");
							break;
						}
						if (num > 0 && num - 1 < currentRoom.getNumberOfItems()){
							inventory.add(currentRoom.getItem(num - 1));
							currentCarryWeight += currentRoom.getItem(num -1).getWeight();
							currentRoom.removeItem(num - 1);
							System.out.println("You took the " + inventory.get(inventory.size() - 1).getName() + ".");
							System.out.println("You are now carrying " + currentCarryWeight + " kg " + "/ " + maxCarryWeight + " kg.");
							turnCompleted = true;
						}
					}
					catch(Exception e){
						System.out.println("That input is invalid! The only valid characters you can use are 0-9.");
					}
				}
				break;
			}

			//Allows the player to read the contents of their inventory and get descriptions of their items.
			case ("inventory"):{
				if (inventory.isEmpty()){
					System.out.println("You aren't carrying anything right now.");
				}
				if (!inventory.isEmpty()){
					System.out.println("You are currently carrying these items:");
					this.showItems();
					boolean done = false;
					while(done == false){
						System.out.println("If you would like a description of one of these items, enter its list number. Otherwise type 0 to exit.");

						//If the input is not an integer, the error will be caught.
						try{
							int num = scan.nextInt();

							//For exiting the menu.
							if (num <= 0){
								System.out.println("Okay, exiting the inventory menu.");
								done = true;
							}
							//Gets the description of the item.
							if (num > 0 && num - 1 < inventory.size()){
								System.out.println(inventory.get(num - 1).getDescription());
								System.out.println("");
							}
							if (num - 1 >= inventory.size()){
								System.out.println("That number is invalid.");
							}
						}
						catch(Exception e){
							System.out.println("That input is invalid! The only valid characters you can use are 0-9.");
						}
					}
				}
				break;
			}

			//Allows the player to choose an item from their inventory to drop.
			case ("drop"):{
				//For if the player is not holding anything.
				if (inventory.isEmpty()){
					System.out.println("You don't have anything to drop!");
				}
				if (!inventory.isEmpty()){
					//If the player enters a non-integer, the error will be caught.
					try{
						this.showItems();
						System.out.println("Please enter the list number of the item you want to drop, or enter 0 to cancel.");
						int item = scan.nextInt();
						if (item <= 0){
							System.out.println("Okay, never mind.");
						}
						if (item > inventory.size()){
							System.out.println("That number is invalid.");
						}
						if (item > 0 && item - 1 < inventory.size()){
							currentRoom.addItem(inventory.get(item - 1));
							currentCarryWeight -= inventory.get(item - 1).getWeight();
							inventory.remove(item - 1);
							System.out.println("You dropped the " + currentRoom.getItem(currentRoom.getNumberOfItems() - 1).getName() + ".");
							System.out.println("You are now carrying " + currentCarryWeight + "kg/" + maxCarryWeight + "kg.");
							turnCompleted = true;
						}
					}
					catch(Exception e){
						System.out.println("That input is invalid! The only valid characters you can use are 0-9.");
					}
				}
				break;
			}

			//Allows the user to use an item in their inventory. Uses the object in a certain way depending on what type of item it is.
			//The type of the item is determined by the boolean values that state whether or not an item is a bomb, consumable, or equipment item.
			//If it is none of these, the item can't be used in any way at all.
			case ("use"):{
				Equipment selectedEquipment = null;
				Consumable selectedConsumable = null;
				Bomb selectedBomb = null;
				this.showItems();
				if (!inventory.isEmpty()){
					try{
						System.out.println("From your inventory, enter the list number of the item you want to use, or enter 0 to cancel.");
						int num = scan.nextInt();

						//Cancels the command.
						if (num <= 0){
							System.out.println("Okay, never mind.");
						}

						//If the number is valid, the item can be used.
						if (num > 0 && num <= inventory.size()){

							//For equipment items.
							if(inventory.get(num - 1).isEquipment()){
								selectedEquipment = (Equipment) inventory.get(num - 1);

								//Switch statement for checking what slot to put the equipment item onto.
								switch(selectedEquipment.getSlot()){

								case("chest"):{
									//Swapping the currently equipped item for the new one.
									inventory.add(this.getChestEquip());
									this.setChestEquip(selectedEquipment);
									inventory.remove(selectedEquipment);

									//Recalculating temperature resistance value.
									this.setTempResistValue(getLegsEquip().getTempResistValue() + getChestEquip().getTempResistValue());

									System.out.println("You put the " + selectedEquipment.getName() + " on.");
									turnCompleted = true;
									break;
								}
								case("legs"):{
									//Swapping the currently equipped item for the new one.
									inventory.add(this.getLegsEquip());
									this.setLegsEquip(selectedEquipment);
									inventory.remove(selectedEquipment);

									//Recalculating temperature resistance value.
									this.setTempResistValue(getLegsEquip().getTempResistValue() + getChestEquip().getTempResistValue());

									System.out.println("You put the " + selectedEquipment.getName() + " on.");
									turnCompleted = true;
									break;
								}
								default:{
									System.out.println("Oops, it looks like there was an error with this item. It can't be equipped.");
									break;
								}
								}
							}

							//For consuming an item.
							else if (inventory.get(num - 1).isConsumable()){
								selectedConsumable = (Consumable) inventory.get(num - 1);
								//Applying the item's health and temperature changes to the player's health and body temperature.
								changeHealthBy(selectedConsumable.getHealthChange());
								changeBodyTempBy(selectedConsumable.getTemperatureChange());
								inventory.remove(selectedConsumable);
								//For if the player accidentally kills themselves using a consumable.
								if (health <= 0 || bodyTemp > 41 || bodyTemp < 33){
									System.out.println("Oh, using that right now wasn't a very good idea...");
								}

								turnCompleted = true;
							}
							
							else if (inventory.get(num - 1).isBomb()){
								selectedBomb = (Bomb) inventory.get(num - 1);
								
								//In the case of bombs that lower the room's temperature, the player may end up wanting to
								//Use them just to cool the room down. Therefore, the monster doesn't actually need to be in the room
								//For the bomb to be usable.
								if (selectedBomb.getRoomTempChange() < 0){
									//If the monster is found to be in the room, it will be stunned.
									if (selectedBomb.getTarget().getCurrentRoom() == currentRoom){
										System.out.println("The " + selectedBomb.getName() + " explodes!");
										selectedBomb.getTarget().changeStunCounterBy(selectedBomb.getStunValue());
										//If the bomb actually has a stun value of more than zero, this message will be printed.
										if (selectedBomb.getStunValue() > 0){
											System.out.println("The monster is stunned!");
										}
									}
									
									//The bomb will lower the room's temperature, and a message will be printed to convey this fact to the player.
									currentRoom.setTemperature(currentRoom.getTemperature() + selectedBomb.getRoomTempChange());
									System.out.println("It got colder in here!");
									//Now that the bomb is used up, it has to be deleted from the player's inventory.
									inventory.remove(selectedBomb);
								}
								
								//For bombs that either have no effect on or raise the room's temperature, the player won't be able to waste their
								//bombs when the monster isn't in the room.
								if (selectedBomb.getRoomTempChange() >= 0){
									//This tells the player that they have to wait for the monster to arrive in order to use the item.
									if (currentRoom != selectedBomb.getTarget().getCurrentRoom()){
										System.out.println("If you want to use the " + selectedBomb.getName() + ", try waiting for the monster to be in the same room as you.");
									}
									//If the monster is in the current room, this code is allowed to execute.
									else{
										System.out.println("The " + selectedBomb.getName() + " explodes!");
										//Applying the stun value to the monster.
										selectedBomb.getTarget().changeStunCounterBy(selectedBomb.getStunValue());
										//This message will not print if the stun value is 100 or more. This is for the special monster killing weapon at the end.
										if (selectedBomb.getStunValue() > 0 && selectedBomb.getStunValue() < 100){
											System.out.println("The monster is stunned!");
										}
										//The room temperature is changed, and a message is printed if the change was greater than zero.
										currentRoom.setTemperature(currentRoom.getTemperature() + selectedBomb.getRoomTempChange());
										if (selectedBomb.getRoomTempChange() > 0){
											System.out.println("It got warmer in here!");
										}
										//Removing the bomb now that it has been used up.
										inventory.remove(selectedBomb);
										turnCompleted = true;
									}
								}
							}
							
							//For if the item has no use.
							else if (!inventory.get(num - 1).isBomb() && !inventory.get(num - 1).isConsumable() && !inventory.get(num - 1).isEquipment()){
								System.out.println("You can't use the " + inventory.get(num - 1).getName() + ".");
							}

						}
						else if (num > inventory.size()){
							System.out.println("That number is invalid.");
						}
					}
					catch(Exception e){
						System.out.println("That input is invalid! The only valid characters you can use are 0-9.");
					}
				}
				break;
			}

			//Gives the player more detailed information about themselves.
			case("self"):{
				System.out.println("Torso: " + getChestEquip().getName() + " // Legs: " + getLegsEquip().getName());
				System.out.println("Your current health is " + health + ".");
				getBodyTempFeeling();
				break;
			}

			//For doing nothing. Helpful if the player needs to warm up/cool down.
			case("wait"):{
				System.out.println("You do nothing.");
				turnCompleted = true;
				break;
			}

			//For quitting the game.
			case ("quit"):{
				System.out.println("If you decide to quit, you will forfeit all of your progress and end the game. \nDo you really want to do this? (Type 1 to end the game, or 0 to cancel)");
				try{
					int num = scan.nextInt();

					if (num == 1){
						System.out.println("Okay, goodbye!");
						gameOver = true;
						turnCompleted = true;
					}
					else{
						System.out.println("All right, continuing the game.");
					}
				}
				catch(Exception e){
					System.out.println("That input is invalid! The only valid characters you can use are 0-9.");
				}
				break;
			}

			//If the input matches none of these cases, nothing will happen and the player gets an error message.
			default:{
				System.out.println("");
				System.out.println("Sorry, that command wasn't recognized. Please try again.");
				break;
			}
			}
		}
		//Once the player's turn is over, temperature effects are added, as are some lines to clearly separate turns from each other for readability in the console.
		doTemperatureEffects();
		System.out.println("");
		System.out.println("********************************************************************************************");
		System.out.println("********************************************************************************************");
	}

}
