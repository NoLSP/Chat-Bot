import java.util.ArrayList;

public class Task {
	private String question;
	private ArrayList<String> answers;
	
	public Task()
	{
		question = "";
		answers = new ArrayList<String>();
	}
	
	public Task(String quest, ArrayList<String> answs)
	{
		question = quest;
		answers = answs;
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	public ArrayList<String> getAnswers()
	{
		return answers;
	}
}
