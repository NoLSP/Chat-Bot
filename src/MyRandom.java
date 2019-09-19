import java.util.Random;

public class MyRandom 
{
	private static int min = 1;
	private static int max = 1;
	private static int diff = max - min;
	private static Random random = new Random();
	
	public static int getRandomNumber()
	{
		return random.nextInt(diff + 1) + min;
	}
}
