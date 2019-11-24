import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class Reader{

	public static String[] readAnswers(String fileName,int ansCount) throws IOException {
		 String[] result = new String[ansCount];
		 File file = new File(fileName);
		 BufferedReader reader = new BufferedReader(new FileReader(file));
		 String input = reader.readLine();
		 while(null != input)
		 {
			 String[] str = input.split("\\*");
			 String answerValue = str[0];
			 for (String i : str[1].split("\\|"))
				 result[Integer.parseInt(i)] = answerValue;
			 input = reader.readLine();
		 }
		 reader.close();
		 return result;
	}
	
	
	public static ArrayList<Task> readQuestions(String fileName) throws IOException{
		 ArrayList<Task> tasks=new ArrayList<Task>();
		 File file = new File(fileName);
		 BufferedReader reader = new BufferedReader(new FileReader(file));
		 String input = reader.readLine();
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
		 return tasks;
	}
	
	public static String  ReadToken(String fileName){
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String input = null;
		try {
			input = reader.readLine();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
	
	public static Pokemon readPoke(String filePath) throws IOException
	{
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String input = reader.readLine();
		String[] str = input.split("\\*");
		Pokemon poke = new Pokemon(file.getName().substring(0, file.getName().length() - 4), str[0]);
		String[] chcs = str[1].split("\\|");
		int[] characteristics = new int[chcs.length];
		for(int i = 0; i < chcs.length; i++)
		{
			characteristics[i] = Integer.parseInt(chcs[i]);
		}
		poke.setCharasteristics(characteristics);
		String[] skillsPaths = str[2].split("\\|");
		ArrayList<Skill> skills = new ArrayList<Skill>();
		for(int i = 0; i < skillsPaths.length; i++)
		{
			try {
				skills.add(readSkill(new File("").getAbsolutePath() + "\\PokeInfo\\Skills\\" + skillsPaths[i]+ ".txt"));
			} catch (IOException e) {
				System.out.println("Can't read skill file: " + skillsPaths[i]);
			}
		}
		poke.setSkills(skills);
		poke.setPhoto(new File("PokeInfo/pictures/" + file.getName().substring(0, file.getName().length() - 4) + ".jpg"));
		reader.close();
		return poke;
	}
	
	private static Skill readSkill(String filePath) throws IOException
	{
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String input = reader.readLine();
		String[] str = input.split("\\*");
		Skill skill = new Skill(file.getName().substring(0, file.getName().length() - 4), str[0], Integer.parseInt(str[2]));
		skill.setCoefficients(str[1]);
		reader.close();
		return skill;
	}

}
