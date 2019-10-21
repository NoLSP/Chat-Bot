import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

public class Data 
{
	private ArrayList<Task> tasks;
	//private AnswersTree answers;
	private String[] answers;
	private ArrayList<Integer> answersCount;
	private int currentQuestionNumber;
	private Pair<String, File> result;
	private String Info = "Вот что я умею:\n"
			+ "/play - начать игру\n"
			+ "/help - справка\n"
			+ "Пиши /play и погнали!"; 
	
	public Data()
	{
		//answers = new AnswersTree(0, 0);
		answers = new String[0];
		tasks = new ArrayList<Task>();
		currentQuestionNumber = 0;
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
		int ansCount = 1;
		String dirPath = new File("").getAbsolutePath();
		File file = new File(dirPath, "Questions.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String input = reader.readLine();
		while(null != input)
		{
			String[] str = input.split("\\*");
			ArrayList<String> answers = new ArrayList<String>();
			for (String i : str[1].split("\\|"))
				answers.add(i);
			tasks.add(new Task(str[0], answers));
			ansCount = ansCount*answers.size();
			answersCount.add(answers.size());
			input = reader.readLine();
		}
		reader.close();
		System.out.println("Questions have done");
		file = new File(dirPath, "Answers.txt");
		reader = new BufferedReader(new FileReader(file));
		answers = new String[ansCount];
		input = reader.readLine();
		while(null != input)
		{
			String[] str = input.split("\\*");
			String answerValue = str[0];
			for (String i : str[1].split("\\|"))
				answers[Integer.parseInt(i)] = answerValue;
			input = reader.readLine();
		}
		reader.close();
		System.out.println("Tree has done");
		
	}
	
	public Task getNextTask()
	{
		return tasks.get(currentQuestionNumber++);
	}
	
	public boolean hasTask()
	{
		return currentQuestionNumber < tasks.size();
	}
	
	public void reset()
	{
		currentQuestionNumber = 0;
		result = null;
	}
	
	public String getInfo()
	{
		return Info;
	}

	public Pair<String, File> getResult(ArrayList<Integer> userAnswers) {
		if (result != null)
			return result;
		String pokeName = getPokeName(userAnswers);
		if( pokeName == null)
		{
			result = getRandomPokemon();
			return result;
		}
		else
		{
			result =  new Pair<String, File>("Поздравляем, ты: " + pokeName, 
							new File(new File("").getAbsolutePath()+ "/pictures/" + pokeName + ".jpg"));
			return result;
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

	private Pair<String, File> getRandomPokemon() 
	{
		File rndPhoto = getRandomPhoto(new File("").getAbsolutePath()+ "/pictures/");
		String pokemonName = rndPhoto.getName().substring(0, rndPhoto.getName().length()-4);
		return new Pair<String, File>("К сожалению не существует покемонов, подходящих тебе\nВот тебе рандомный: " + pokemonName, 
						rndPhoto);
	}

	private File getRandomPhoto(String path)
	{
		String[] filesNames = getPhotoNameList(path);
		MyRandom rnd = new MyRandom(filesNames.length-1);
		int index = rnd.next();
		return new File(path + filesNames[index]);
	}	
	
	private String[] getPhotoNameList(String path)
	{
		File folder = new File(path);
		String[] files = folder.list(new FilenameFilter() {
			@Override public boolean accept(File folder, String name) {
				return name.endsWith(".jpg");
		    }    
		});
		return files;
	}
}