import java.io.File;
import java.util.ArrayList;

public class Game 
{
	private Data data;
	private boolean DeterminerPhaseContinuing;
	private boolean DeterminerPhaseComplited;
	private boolean isStarted;
	private ArrayList<Integer> userAnswers;
	private String currentQuestion;
	private Task currentTask;
	
	public Game()
	{
		data = new Data();
		DeterminerPhaseContinuing = false;
		DeterminerPhaseComplited = false;
		isStarted = false;
		userAnswers = new ArrayList<Integer>();
		currentQuestion = "";
		currentTask = new Task();
	}
	
	public String greeting()
	{
		return "������, � ����� ���-���!\n" + data.getInfo();
	}
	
	public String help()
	{
		return data.getInfo();
	}
	
	public Pair<Pair<String, ArrayList<String>>, File> step(String input)
	{
		switch (input)
		{
			case ("/start"):
				if (isStarted)
					return refund(greeting(), null, null);
				else
				{
					isStarted = true;
					return refund("������������ ��������\n" + help(), null, null);
				}
			case ("/play"):
				data.reset();
				currentTask = data.getNextTask();
				currentQuestion = currentTask.getQuestion();
				userAnswers = new ArrayList<Integer>();
				DeterminerPhaseContinuing = true;
				return refund(currentQuestion, currentTask.getAnswers(), null);
			case ("/help"):
				return refund(help(), null, null);
		}
		if (DeterminerPhaseContinuing)
			return getNextUnveilingQuestion(input);
		//default
		return refund("I don't know what you mean\n" + help(), null, null);
	}
	
	//��������� ������������ ������
	//��������� ����� ������������-����� �������� ������
	//���������� ����: ������ � ���������� ������� � ����
	public Pair<Pair<String, ArrayList<String>>, File> getNextUnveilingQuestion(String input)
	{
		System.out.println("---Question answer processeed---");
		//input ������� �� ���� ����: ����� ������� � ������� ������
		int questionNumber = -1;
		int userAnswer = -1;
		//�������� ���, ����� ������
		try {
			System.out.println("Input = " + input);
			questionNumber = Integer.parseInt(input.substring(0, 1));
			userAnswer = Integer.parseInt(input.substring(1));
		}
		catch (Exception e) {
			return refund("�� ���� � ��� ��, ������������ ��������: /help", null, null);
		}
		System.out.println(data.getCurrentQuestionNumber() + " " +  questionNumber);
		if (questionNumber != data.getCurrentQuestionNumber())
			return refund(data.getCurrentQuestionNumber() + "������� �� ������� ������:\n" + currentQuestion.substring(1), currentTask.getAnswers(), null);
		userAnswers.add(userAnswer);
		System.out.println("User have answered " + questionNumber + " question. User answer = " + userAnswer);
		if(data.hasTask())
		{
			currentTask = data.getNextTask();
			currentQuestion = currentTask.getQuestion();
			return refund(currentQuestion, currentTask.getAnswers(), null);
		}
		else
		{
			DeterminerPhaseContinuing = false;
			DeterminerPhaseComplited = true;
			return getResult();
		}
	}
	
	private Pair<Pair<String, ArrayList<String>>, File> getResult() {
		Pair<String, File> result = data.getResult(userAnswers);
		return new Pair<Pair<String, ArrayList<String>>, File>(new Pair<String, ArrayList<String>>(result.getItem1(), null), result.getItem2());
	}
	
	//������������ �����, ����� ��������� �� ������ ������������� ����	
	private Pair<Pair<String, ArrayList<String>>, File> refund(String message, ArrayList<String> keyboard, File photo)
	{
		return new Pair<Pair<String, ArrayList<String>>, File>(new Pair<String, ArrayList<String>>(message, keyboard), photo);
	}
	
	public Task getTask()
	{
		return currentTask;
	}
}