public class Driver {

	public static void main(String[] args) {
		
		//Opening message.
		System.out.println("********************************************************************************************************************** \n"
				+ "You begin your descent into the cave. Pay attention to your thermometer, it gets hotter the deeper you go. \n"
				+ "You'll need to use whatever you can find to help yourself endure the intense heat. \n"
				+ "If this is your first time playing, try typing \"help\" and \"instructions\" for some useful information on how to play. \n"
				+ "Good luck! \n"
				+ "********************************************************************************************************************** \n");
		
		//Creating rooms.
		Room debrisRoom = new Room("There's all sorts of junk lying around in this area. Destroyed furniture, piles of decomposing \n"
				+ "papers, and assorted trinkets are lying around.", 24);
		Room hotRoom = new Room("This part of the cave is practically a sauna. There's hot steam shooting from some cracks in the floor!", 50)
		
		Room fungiSpring = new Room("You see a small spring on the west wall of the cave. It would be nice if it wasn't the perfect place for all the \n"
				+ "strange fungi growing here. There are two passages heading to the left and right here.", 26);
		Room deadEndItems = new Room("It's a dead end.", 19);
		
		Room startRoom = new Room(deadEndItems, fungiSpring, null, "You're standing in the entrance to the cave. You can go left or right from here.", 18);
		Room monsterStart = new Room("Room that can't be accessed once left. For the monster to start in.", 18);	
		
		//Creating items.
		Item rock = new Item("Huge Rock", "A very big and heavy rock. It's useless.", 45);
		Item globe = new Item("Old Globe", "It's a globe. It must be old, because the U.S.S.R. is on it. \n"
				+ "There's no real reason to carry this around.", 1);
		Item record = new Item ("Saturday Night Fever Soundtrack", "What's something like this doing here? Maybe the monster likes disco.", 0.5);
		Consumable iceBomb = new Consumable("Ice Bomb", "It can freeze things in their tracks and permanently cool the area you're in. \n Just don't use too many in the same room...", 1, 1, 0);
		Consumable zapBomb = new Consumable("Electric Bomb", "Acting like a tazer, it can temporarily paralyze the target.", 1, 1, 0);
		Equipment tShirt = new Equipment("T-Shirt", "It's just a plain T-Shirt. It doesn't affect your resistance to temperature.", "chest", 0, 0);
		Equipment tankTop = new Equipment("Tank Top", "This tank top will keep you a little bit cooler than a T-shirt.", "chest", 0.2, 0);
		Equipment athleticShirt = new Equipment("CoolMaster 9000", "An athletic shirt with outstanding performance. It's the best of the best for keeping cool. (Equippable)", "chest", 0, 0.60);
		Equipment jeans = new Equipment("Blue Jeans", "A blue pair of denim pants that never seem to go out of style. They don't affect your resistance to temperature.", "legs", 0.45, 0);
		Equipment shorts = new Equipment("Shorts", "This pair of shorts will keep you cooler than denim pants.", "legs", 0.2, 0);
		Equipment shortShorts = new Equipment("Short Shorts", "You'll definitely stay a lot cooler in these leggy shorts. They're the best for beating the heat.", "legs", 0.1, 0.5);
		Consumable instaFreeze = new Consumable("Insta-Freeze Chocolate Bar", "Eating it when you aren't burning up is likely to freeze you.", 0.5, 0, -6);
		
		//Setting links between rooms.
		deadEndItems.setBack(startRoom);
		fungiSpring.setBack(startRoom);
		
		//Adding items to rooms.
		deadEndItems.addItem(rock);
		deadEndItems.addItem(iceBomb);
		deadEndItems.addItem(shortShorts);
		deadEndItems.addItem(instaFreeze);
		
		//Creating player(s).
		Player player = new Player(startRoom, tShirt, jeans);
		Monster monster = new Monster(player, monsterStart);
		
		//Running game.
		while (!player.gameIsOver()){
			System.out.println("");
			player.playerTurn();
			monster.monsterTurn();
		}
		
		if (player.gameIsOver()){
			System.out.println("R.I.P.");
		}
		
	}

}
