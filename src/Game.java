import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Game 
{
	private Data data = new Data();
	private MyRandom random = new MyRandom(data.QuestionsCount - 1);
	private boolean isPlay = true;
	private boolean isStarted = false;
	private Pair task;
	private enum commands {start, end, help};
	
	public List<String> greeting()
	{
		List<String> result = new ArrayList<String>();
		result.add("������, � ����� ���-���!");
		for (int i = 0; i < data.Info.size(); i++)
		{
			String item = data.Info.get(i);
			result.add(item);
		}	
		return result;
	}
	
	public List<String> help()
	{
		return data.Info;
	}
	
	public boolean getGameStatus()
	{
		return isPlay;
	}
	
	public Pair getTask()
	{
		return task;
	}
	
	public List<String> step(String input) throws Exception
	{
		if (input.equals(commands.start.name()))
		{
			if(isStarted) 
			{
				return Arrays.asList("���� ��� ����, ������� �� ������ :", task.getQuestion());
			}
			else
			{
				task = data.getQuestion(random.getRandomNumber());
				isStarted = true;
				return Arrays.asList(task.getQuestion());	
			}		
		}
		else if(input.equals(commands.help.name()))
		{
			return data.Info;
		}
		else if(input.equals(commands.end.name()))
		{
			isPlay = false;
			return Arrays.asList("�� ����� ������!");
		}
		else if (input.equals(task.getAnswer()))
		{
			if (!random.CanGetValue())
			{
				isPlay = false;
				return Arrays.asList("������� ���������, �������!");	
			}
			task = data.getQuestion(random.getRandomNumber());
			return Arrays.asList("�������!", "���� ��������� ������:", task.getQuestion());
			
		}
		else return Arrays.asList("�������� ��� ���...");	
	}
}