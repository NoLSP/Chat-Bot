
public class Characteristics {
	private int health;
	private int speed;
	private int intellect;
	private int strength;
	private int defence;
	
	public Characteristics(int[] input)
	{
		health = input[0];
		speed = input[1];
		intellect = input[2];
		strength = input[3];
		defence = input[4];		
	}
	public Characteristics(int health, int speed, int intellect, int strength, int defence)
	{
		this.health = health;
		this.speed = speed;
		this.intellect = intellect;
		this.strength = strength;
		this.defence = defence;		
	}
	
	public int[] getArray()
	{
		return new int[] {health, speed, intellect, strength, defence};
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public void reduceHealth(int damage)
	{
		this.health = this.health - damage;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public int getIntellect()
	{
		return intellect;
	}
	
	public int getStrength()
	{
		return strength;
	}
	
	public int getDefence()
	{
		return defence;
	}
}
