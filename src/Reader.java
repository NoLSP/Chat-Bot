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

}
