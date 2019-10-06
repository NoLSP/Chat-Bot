import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class MyRandom implements Iterator<Integer>
{
	private int Min;
	private int Max;
	private Random random = new Random();
	private int currentIndex = 0;
	private List<Integer> indexes = new ArrayList<Integer>();
	
	public MyRandom(int maxValue)
	{
		Min = 0;
		Max = maxValue;
		List<Integer> oldIndexes = new ArrayList<Integer>();
		for( int i = 0; i <= maxValue; i++)
			oldIndexes.add(i);
		for( int i = 0; i <= maxValue; i++)
		{
			int rndValue = random.nextInt(Max - Min + 1) + Min;
			indexes.add(oldIndexes.get(rndValue));
			oldIndexes.remove(rndValue);
			Max--;
		}
	}
	
	@Override
	public boolean hasNext() {
		return currentIndex != indexes.size();
	}

	@Override
	public Integer next() {
		if (hasNext())
			return indexes.get(currentIndex++);	
		else
			throw new NoSuchElementException();
	}
	
	public void reset()
	{
		currentIndex = 0;
	}
}