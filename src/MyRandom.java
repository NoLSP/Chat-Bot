import java.util.Random;

public class MyRandom 
{
	private int Min;
	public int Max;
	private int diff;
	private Random random = new Random();
	private boolean[] completeValues;
	private int completeValuesCount = 0;
	public boolean CanGetValue = true;
	
	public MyRandom(int maxValue)
	{
		Min = 0;
		Max = maxValue;
		diff = Max - Min;
		completeValues = new boolean[Max + 1];
	}
	
	public int getRandomNumber()
	{
		int nextValue = 0;
		while (completeValues[nextValue])
		{
			nextValue = random.nextInt(diff + 1) + Min;
			System.out.println(nextValue);
		}
		completeValues[nextValue] = true;
		completeValuesCount += 1;
		if (completeValuesCount > Max)
			CanGetValue = false;
		return nextValue;	
	}
}
