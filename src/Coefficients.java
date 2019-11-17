
public class Coefficients {
	private double forHealth;
	private double forSpeed;
	private double forIntellect;
	private double forStrength;
	private double forDefence;
	
	public Coefficients(double[] input)
	{
		forHealth = input[0];
		forSpeed = input[1];
		forIntellect = input[2];
		forStrength = input[3];
		forDefence = input[4];		
	}
	
	public Coefficients(double forHealth, double forSpeed, double forIntellect, double forStrength, double forDefence)
	{
		this.forHealth = forHealth;
		this.forSpeed = forSpeed;
		this.forIntellect = forIntellect;
		this.forStrength = forStrength;
		this.forDefence = forDefence;		
	}
	
	public double[] getArray()
	{
		return new double[] {forHealth, forSpeed, forIntellect, forStrength, forDefence};
	}
	
	public double forHealth()
	{
		return forHealth;
	}
	
	public double forSpeed()
	{
		return forSpeed;
	}
	
	public double forIntellect()
	{
		return forIntellect;
	}
	
	public double forStrength()
	{
		return forStrength;
	}
	
	public double forDefence()
	{
		return forDefence;
	}
}
