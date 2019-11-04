public class Driver {

	// !!!! This project has been updated since the presentation. Minor errors have been corrected, a few more rooms
	//	//have been added, a win condition has been added, and bombs are properly implemented and can stun the monster.
	//	//Otherwise it is largely the same as it was on the 5th of May. !!!!
	public static void main(String[] args) {

		//Opening message.
		System.out.println("************************************************************************************************************************* \n"
				+ "You begin your descent into the cave. Pay attention to your thermometer, it gets hotter the deeper you go. \n"
				+ "You'll need to use whatever you can find to help yourself endure the intense heat. \n"
				+ "If this is your first time playing, try typing \"help\" for some useful information on how to play. \n"
				+ "Your mission is to find the deepest part of the caves, find the legendary monster-killing weapon, and destroy the monster! \n"
				+ "Good luck! \n"
				+ "************************************************************************************************************************* \n");

		//Creating rooms. These are just meant to test features for the most part. A win condition has now been implemented.
		//Ideally, the caves would be much, much larger, making the temperature system matter a lot more.
		//For the sake of testing this project, the game is short and some mechanics are altered to be easier to test.
		Room shrine = new Room("Looks like you've reached the deepest part of the cave... This room is decorated with all sorts of disco products. \n"
						+ "It's so hot in here you might get disco fever.", 58);
		
		Room debrisRoom = new Room("There's all sorts of junk on the floor of this part of the cave. There could be something useful lying around.", 24);
		Room hotRoom = new Room(null, shrine, null, "This room is very hot! Looks like you can only go right from here...", 48);

		Room fungiSpring = new Room(hotRoom, debrisRoom, null, "It's noticeably warmer in this area. \n"
				+ "It feels like the source is on the left, but you can also try going to the right.", 21);
		Room deadEndItems = new Room("It's a dead end.", 19);

		Room startRoom = new Room(deadEndItems, fungiSpring, null, "You're standing in the entrance to the cave. You can go left or right from here.", 18);
		Room monsterStart = new Room("Room that can't be accessed once left. For the monster to start in.", 18);
		
		//Creating the player's starting clothing. If the player starts without clothing the program will end up crashing when the player tries to equip an item.
		Equipment tShirt = new Equipment("T-Shirt", "It's just a plain T-Shirt. It doesn't affect your resistance to temperature.", "chest", 0, 0);
		Equipment jeans = new Equipment("Blue Jeans", "A blue pair of denim pants that never seem to go out of style. They don't affect your resistance to temperature.", "legs", 0.45, 0);
		//Creating player and monster.
		Player player = new Player(startRoom, 50, tShirt, jeans);
		Monster monster = new Monster(player, monsterStart);

		//Creating items.
		Item rock = new Item("Rock", "A heavy rock. Just dead weight.", 45);
		Item globe = new Item("Old Globe", "It's a globe. It must be fairly old, because the U.S.S.R. is on the map. \n"
				+ "It wouldn't be very useful to carry this old thing around.", 1);
		Bomb discoRecord = new Bomb("Disco Movie Soundtrack", "It's a record containing the soundtrack to a 1970s disco movie. The front cover depicts a man in a white suit dancing on a disco floor, pointing towards the ceiling. \n"
				+ "Three other men in white suits are framed in a picture behind him. Most monsters aren't particularly fond of disco.", 1, monster, 0, 100);
		Bomb iceBomb = new Bomb("Ice Bomb", "It can freeze things in their tracks and permanently cool the area you're in. \n Just don't use too many in the same room...", 3, monster, -10, 4);
		Bomb zapBomb = new Bomb("Electric Bomb", "Acting like a tazer, it can temporarily paralyze the target.", 1, monster, 0, 4);
		//An unused item that would be more useful as a mid-level upgrade to the starting shirt in a longer level.
		Equipment tankTop = new Equipment("Tank Top", "This tank top will keep you a little bit cooler than a T-shirt.", "chest", 0.2, 0);
		Equipment athleticShirt = new Equipment("CoolMaster 9000", "An athletic shirt with outstanding performance. It's the best of the best for keeping cool.", "chest", 0.1, 0.60);
		//An unused item that would be more useful as a mid-level upgrade to the starting pants in a longer level.
		Equipment shorts = new Equipment("Shorts", "This pair of shorts will keep you cooler than denim pants.", "legs", 0.2, 0);
		Equipment shortShorts = new Equipment("Short Shorts", "You'll definitely stay a lot cooler in these leggy shorts. They're the best for beating the heat.", "legs", 0.1, 0.5);
		Consumable instaFreeze = new Consumable("Insta-Freeze Chocolate Bar", "The label reads, \"ONLY USE IN CASE OF CRITICAL HEATSTROKE!\"", 0.5, 0, -6);
		Consumable firstAidKit = new Consumable("First Aid Kit", "A handy dandy first aid kit with all the fancy bells and whistles a first aid kit needs. You can only use it once, but it completely heals you.", 0, 99999, 0);
		Consumable icePack = new Consumable("Ice Pack", "A one-time use instant ice pack that uses an endothermic chemical reaction to temporarily become ice cold. It heals you and cools you down a lot.", 0.1, 30, -2);

		//Setting links between rooms.
		shrine.setBack(hotRoom);
		
		hotRoom.setBack(fungiSpring);
		debrisRoom.setBack(fungiSpring);
		
		deadEndItems.setBack(startRoom);
		fungiSpring.setBack(startRoom);

		//Adding items to rooms.
		deadEndItems.addItem(rock);
		deadEndItems.addItem(iceBomb);
		deadEndItems.addItem(shortShorts);
		deadEndItems.addItem(instaFreeze);
		deadEndItems.addItem(firstAidKit);
		
		fungiSpring.addItem(zapBomb);
		deadEndItems.addItem(icePack);
		
		debrisRoom.addItem(globe);
		debrisRoom.addItem(athleticShirt);
		
		shrine.addItem(discoRecord);
		
		//Running game. If you would like to test features without the monster bothering you, simply comment out the code.
		//Keep in mind that the game can't be won while the monster is disabled!
		while (!player.gameIsOver() && !monster.isDead()){
			System.out.println("");
			player.playerTurn();
			//monster.monsterTurn();
		}
		
		//These if statements check whether the player won or lost, and print the proper message depending on the situation.
		if (player.gameIsOver()){
			System.out.println("R.I.P.");
		}
		if (monster.isDead()){
			System.out.println("Congratulations, you won!");
		}
	}

}
