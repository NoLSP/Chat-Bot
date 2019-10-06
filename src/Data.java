import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Data 
{
	public List<Pair> Tasks = new ArrayList<Pair>();
	public int QuestionsCount = 0;
	public String Info = "Вот что я умею:\n"
			+ "/play - начать игру\n"
			+ "/end - завершить игру\n"
			+ "/help - справка\n"
			+ "Пиши /play и погнали!"; 
	
	public Data()
	{
		try 
		{
			fillTasks();
		} 
		catch (IOException e) 
		{
			System.out.println("Exception in Data's constructor");
		}
	}
	
	private void fillTasks() throws IOException
	{
		String dirPath = new File("").getAbsolutePath();
		File file = new File(dirPath, "Data.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String str = reader.readLine();
		while(null != str)
		{
			Pair task = new Pair();
			String[] splited = str.split("\\*");
			task.setQuestion(splited[0]);
			task.setAnswer(splited[1]);
			Tasks.add(task);
			str = reader.readLine();
		}
		reader.close();
		QuestionsCount = Tasks.size();
	}
	
	public Pair getQuestion(int number)
	{
		return Tasks.get(number);
	}
	
	//only for tests
	public boolean hasQuestion(String q)
	{
		for (int i = 0; i < QuestionsCount; i++)
		{
			if(q.equals(Tasks.get(i).getQuestion()))
				return true;
		}
		return false;
	}
	
	public String GetAnswer(String q)
	{
		for (int i = 0; i < QuestionsCount; i++)
		{
			if(q.equals(Tasks.get(i).getQuestion()))
				return Tasks.get(i).getAnswer();
		}
		return "incorrect question";
	}
}