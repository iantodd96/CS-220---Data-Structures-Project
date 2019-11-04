
public class Equipment extends Item{

	double tempResistValue;
	String slot;
	
	//Creates an item the player can equip. Equipment items can be swapped freely.
	public Equipment(String name, String description, String slot, double weight, double tempResistValue) {
		super(name, description, weight);
		this.tempResistValue = tempResistValue;
		this.slot = slot;
	}
	
	public double getTempResistValue(){
		return tempResistValue;
	}
	
	public String getSlot(){
		return slot;
	}
	
	public boolean isConsumable(){
		return false;
	}
	
	//When the player types in the "use" command, this method will return as true, thus causing the item to be equipped on their body.
	public boolean isEquipment(){
		return true;
	}
	
	public boolean isBomb(){
		return false;
	}
}