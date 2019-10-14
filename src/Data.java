import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;

public class Data 
{
	private ArrayList<Task> tasks;
	private AnswersTree answers;
	private int currentQuestionNumber;
	private String Info = "Вот что я умею:\n"
			+ "/play - начать игру\n"
			+ "/help - справка\n"
			+ "/reset - перезапуск\n"
			+ "Пиши /play и погнали!"; 
	
	public Data()
	{
		answers = new AnswersTree(0, 0);
		tasks = new ArrayList<Task>();
		currentQuestionNumber = 0;
		try 
		{
			fillTasks();
		} 
		catch (Exception e) 
		{
			System.out.println("Exception in Data's constructor");
		}
	}
	
	private void fillTasks() throws NumberFormatException, Exception
	{
		String dirPath = new File("").getAbsolutePath();
		File file = new File(dirPath, "Answers.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String input = reader.readLine();
		while(null != input)
		{
			String[] str = input.split("\\*");
			String answerValue = str[0];
			fillTree(answers, str[1], 1, answerValue);
			input = reader.readLine();
		}
		reader.close();
		System.out.println("Tree has done");
		file = new File(dirPath, "Questions.txt");
		reader = new BufferedReader(new FileReader(file));
		input = reader.readLine();
		while(null != input)
		{
			String[] str = input.split("\\*");
			ArrayList<String> answers = new ArrayList<String>();
			for (String i : str[1].split("\\|"))
				answers.add(i);
			tasks.add(new Task(str[0], answers));
			input = reader.readLine();
		}
		reader.close();
		System.out.println("Questions have done");
	}
	
	private void fillTree(AnswersTree tree, String str, int questionNumber, String answer) throws NumberFormatException, Exception
	{
		if (str.equals("")) 
		{	
			tree.putAnswer(answer);
			return;
		}
		String[] arrstr = str.split("\\|");
		char[] pStr = arrstr[0].toCharArray();
		for (char i : pStr)
		{
			if (tree.containsChild(Character.getNumericValue(i)-1))
				fillTree(tree.getChildWithValue(Character.getNumericValue(i)-1), str.substring(str.indexOf('|')+1), questionNumber+1, answer);
			else
			{
				AnswersTree newItem = new AnswersTree(questionNumber, Character.getNumericValue(i)-1);
				tree.add(newItem);
				int ind = str.indexOf("|")+1;
				if (ind == 0) ind = str.length();
				fillTree(newItem, str.substring(ind), questionNumber+1, answer);
			}
		}
	}
	
	public String getResult(ArrayList<Integer> answs) throws Exception
	{
		AnswersTree tree = answers;
		while(!tree.isFinal())
		{
			if(!tree.hasChild(answs.get(tree.getAnswerNumber()))) 
				return "К сожалению не существует покемонов, подходящих тебе\nПопробуй еще раз";
			tree = tree.getChildWithValue(answs.get(tree.getAnswerNumber()));
		}
		ArrayList<String> results = tree.getAnswers();
		MyRandom rnd = new MyRandom(results.size()-1);
		return "Поздравляем, ты: " + results.get(rnd.next());
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
	}
	
	public String getInfo()
	{
		return Info;
	}

	public Pair getResult(Long chatId, ArrayList<Integer> userAnswers) {
		AnswersTree tree = answers;
		while(!tree.isFinal())
		{
			if(!tree.hasChild(userAnswers.get(tree.getAnswerNumber()))) 
			{
				File rndPhoto = getRandomPhoto(new File("").getAbsolutePath()+ "/pictures/");
				String pokemonName = rndPhoto.getName().substring(0, rndPhoto.getName().length()-5);
				try {
					return new Pair (new SendMessage().setChatId(chatId).setText("К сожалению не существует покемонов, подходящих тебе\nВот тебе рандомный: " + pokemonName), 
									 new SendPhoto().setChatId(chatId).setPhoto(pokemonName, new FileInputStream(rndPhoto)));
				} catch (FileNotFoundException e) {
					System.out.println("Exception with photo");
					e.printStackTrace();
				}
			}
			try {
				tree = tree.getChildWithValue(userAnswers.get(tree.getAnswerNumber()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ArrayList<String> results = tree.getAnswers();
		MyRandom rnd = new MyRandom(results.size()-1);
		String pokemonName = results.get(rnd.next());
		SendPhoto photo;
		try {
			photo = new SendPhoto().setChatId(chatId).setPhoto("Поздравляем, ты: " + pokemonName, 
									new FileInputStream(new File(new File("").getAbsolutePath()+ "/pictures/" + pokemonName + ".jpg")));
			return new Pair(new SendMessage().setChatId(chatId).setText("Поздравляем, ты: " + pokemonName), photo);
		} catch (FileNotFoundException e) {
			System.out.println("Exception with photo");
			e.printStackTrace();
		}
		return null;	
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