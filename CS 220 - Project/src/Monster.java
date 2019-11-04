import java.util.Random;
public class Monster {

	private int stunCounter = 1;
	private Player target;
	private boolean isDead;
	private Room currentRoom;

	//For the queue of rooms that allow the monster to track the player's scent.

	public Monster(Player target, Room startRoom){
		this.target = target;
		currentRoom = startRoom;
	}

	public void setIsDead(boolean status){
		isDead = status;
	}

	public boolean isDead(){
		return isDead;
	}

	public Room getCurrentRoom(){
		return currentRoom;
	}
	
	//Takes away health from the player.
	public void attackPlayer(){
		Random rand = new Random();
		int attackDamage = (rand.nextInt(25) + 1) * -1;
		target.changeHealthBy(attackDamage);
		System.out.println("The monster claws at you, dealing " + attackDamage + " HP of damage!");
	}
	
	//Runs the monster's turn.
	public void monsterTurn(){
		if (stunCounter > 0){
			//For if the monster is killed.
			if (stunCounter >= 100){
				System.out.println("All that remains of the monster is a scorch mark on the ground.");
			}
			//The monster will wake up again when the counter eventually gets back to zero.
			stunCounter--;
			//Tells the player if the monster is currently stunned.
			if (stunCounter >= 2 && !isDead && target.getCurrentRoom() == currentRoom){
				System.out.println("The monster is out cold.");
			}

			if (stunCounter == 1 && !isDead && target.getCurrentRoom() == currentRoom){
				System.out.println("The monster is waking up!");
			}

		}
		//The monster will attack the player if they are in the same room and don't move.
		if (currentRoom == target.getCurrentRoom() && !target.gameIsOver() && stunCounter == 0){
			attackPlayer();
		}
		//For if the player dies from any cause in the monster's presence.
		if (target.gameIsOver() && currentRoom == target.getCurrentRoom()){
			if (stunCounter == 0){
				System.out.println("The monster is laughing at you.");
			}
			if (stunCounter > 0){
				System.out.println("...But it will definitely be laughing at you when it wakes up!");
			}
		}
		//If the monster isn't stunned, it will try to track the player down.
		if (stunCounter == 0){
			currentRoom = target.removeBack();
			
			//Warns the player if the monster is in the room.
			if (currentRoom == target.getCurrentRoom() && !target.gameIsOver()){
				System.out.println("The monster is in this room!");
			}
		}
	}
	
	//This is a method for when bombs are used on the monster. The effect can last longer if multiple bombs are used.
	public void changeStunCounterBy(int num){
		stunCounter += num;
		if (stunCounter >= 100){
			System.out.println("The monster is consumed in an inferno!");
			isDead = true;
		}
	}

}
