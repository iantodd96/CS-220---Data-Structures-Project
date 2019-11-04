
public class  Consumable extends Item{
	
	int healthChange;
	double temperatureChange;
	
	//A consumable is any item meant to be used on the player.
	public Consumable(String name, String description, double weight, int healthChange, double temperatureChange) {
		super(name, description, weight);
		this.healthChange = healthChange;
		this.temperatureChange = temperatureChange;
	}
	
	public int getHealthChange(){
		return healthChange;
	}
	
	public double getTemperatureChange(){
		return temperatureChange;
	}
	
	//Since this message is true, the item will be consumed when used by the player, changing their body temperature and/or or healing them.
	public boolean isConsumable(){
		return true;
	}
	
	public boolean isEquipment(){
		return false;
	}

	public boolean isBomb(){
		return false;
	}
}