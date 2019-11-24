import java.util.ArrayList;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class Data 
{
	private ArrayList<Task> tasks;
	//private AnswersTree answers;
	private String[] answers;
	private ArrayList<Integer> answersCount;
	private int currentQuestionNumber;
	private Pokemon result;
	private String Info = "Вот что я умею:\n"
			+ "/play - начать игру\n"
			+ "/help - справка\n"
			+ "/fight - начать бой"; 
	
	public Data()
	{
		//answers = new AnswersTree(0, 0);
		answers = new String[0];
		tasks = new ArrayList<Task>();
		currentQuestionNumber = -1;
		result = null;
		answersCount = new ArrayList<Integer>();
		try 
		{
			fillTasks();
		} 
		catch (Exception e) 
		{
			System.out.println("Exception in Data's constructor");
		}
	}
	
	private void fillTasks() throws IOException
	{
		tasks = Reader.readQuestions("Questions.txt");
		System.out.println("Questions have done");
		int ansCount = 1;
		for(Task task : tasks)
		{
			answersCount.add(task.getAnswers().size());
			ansCount = ansCount*task.getAnswers().size();
		}
		answers = Reader.readAnswers("Answers.txt", ansCount);
		System.out.println("Tree has done");
	}
	
	public Task getNextTask()
	{
		return tasks.get(++currentQuestionNumber);
	}
	
	public boolean hasTask()
	{
		return currentQuestionNumber < tasks.size() - 1;
	}
	
	public void reset()
	{
		currentQuestionNumber = -1;
		result = null;
	}
	
	public String getInfo()
	{
		return Info;
	}

	public Pair<String, File> getResult(ArrayList<Integer> userAnswers) {
		if (result != null)
			return new Pair<String, File>(result.getName(), result.getPhoto());
		String pokeName = getPokeName(userAnswers);
		if( pokeName == null)
		{
			result = getRandomPokemon();
			return new Pair<String, File>("Вот тебе рандомный: " + result.getName(), result.getPhoto());
		}
		else
		{
		
			try {
				result =  Reader.readPoke(new File("").getAbsolutePath() + "\\PokeInfo\\" + pokeName + ".txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new Pair<String, File>("Поздравляем, ты: " + result.getName(), result.getPhoto());
		}	
	}
	
	private String getPokeName(ArrayList<Integer> userAnswers) {
		//using our formul here
		int mult = 1;
		int result = 0;
		for (int i =userAnswers.size()-1; i >= 0; i-- )
		{
			result += userAnswers.get(i) * mult;
			mult = mult * answersCount.get(i);
		}
		System.out.println("PokemonIndex = " + result);
		return answers[result];
	}

	public Pokemon getRandomPokemon()
	{
		String rndPokeName = getRandomFileName(new File("").getAbsolutePath()+ "/PokeInfo/");
		try {
			return Reader.readPoke(new File("").getAbsolutePath() + "/PokeInfo/" + rndPokeName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getRandomFileName(String path)
	{
		String[] filesNames = getFileList(path);
		MyRandom rnd = new MyRandom(filesNames.length-1);
		int index = rnd.next();
		return filesNames[index];
	}	
	
	private String[] getFileList(String path)
	{
		File folder = new File(path);
		String[] files = folder.list(new FilenameFilter() {
			@Override public boolean accept(File folder, String name) {
				return name.endsWith(".txt");
		    }    
		});
		return files;
	}
	public int getCurrentQuestionNumber()
	{
		return currentQuestionNumber;
	}
	
	public Pokemon getCurrentPokemon()
	{
		return result;
	}
}