import java.util.ArrayList;

public class AnswersTree {
	private ArrayList<AnswersTree> childs;
	private int answerNumber;
	private int value;
	private ArrayList<String> answers;
	private boolean isFinal;
	
	public AnswersTree(int ansNumber, int vl)
	{
		childs = new ArrayList<AnswersTree>();
		answerNumber = ansNumber;
		value = vl;
		isFinal = false;
		answers = new ArrayList<String>();
	}
	
	public boolean isFinal()
	{
		return isFinal;
	}
	
	public void add(AnswersTree child)
	{
		childs.add(child);
	}
	
	public void putAnswer(String ans)
	{
		answers.add(ans);
		isFinal = true;
	}
	
	public int getAnswerNumber()
	{
		return answerNumber;
	}
	
	public AnswersTree getChild(int index)
	{
		return childs.get(index);
	}
	
	public int getValue()
	{
		return value;
	}
	
	public boolean containsChild(int vl)
	{
		for(AnswersTree ch : childs)
			if (ch.getValue() == vl) return true;
		return false;
	}
	
	public AnswersTree getChildWithValue(int value) throws Exception
	{
		for(AnswersTree ch : childs)
			if (ch.getValue() == value) return ch;
		throw new Exception();
	}
	
	public boolean hasChild(int value)
	{
		for(AnswersTree ch : childs)
			if (ch.getValue() == value) return true;
		return false;
	}
	
	public ArrayList<String> getAnswers()
	{
		return answers;
	}
	
	public String getResult(ArrayList<Integer> userAnswers)
	{
		//TO DO...
		return new String();
	}
}
