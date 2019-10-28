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
		return "ѕривет, € супер чат-бот!\n" + data.getInfo();
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
					return refund("¬оспользуйс€ справкой\n" + help(), null, null);
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
	
	//—ледующий определ€ющий вопрос
	//принимает ответ пользовател€-номер варианта ответа
	//возвращает пару: вопрос с вариантами ответов и фото
	public Pair<Pair<String, ArrayList<String>>, File> getNextUnveilingQuestion(String input)
	{
		System.out.println("---Question answer processeed---");
		//input состоит из двух цифр: номер вопроса и вариант ответа
		int questionNumber = -1;
		int userAnswer = -1;
		//ќтсекаем все, кроме ответа
		try {
			System.out.println("Input = " + input);
			questionNumber = Integer.parseInt(input.substring(0, 1));
			userAnswer = Integer.parseInt(input.substring(1));
		}
		catch (Exception e) {
			return refund("Ќе знаю о чем ты, воспользуйс€ справкой: /help", null, null);
		}
		System.out.println(data.getCurrentQuestionNumber() + " " +  questionNumber);
		if (questionNumber != data.getCurrentQuestionNumber())
			return refund(data.getCurrentQuestionNumber() + "ќтвечай на текущий вопрос:\n" + currentQuestion.substring(1), currentTask.getAnswers(), null);
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
	
	//¬спомогающий метод, чтобы посто€нно не писать инициализацию пары	
	private Pair<Pair<String, ArrayList<String>>, File> refund(String message, ArrayList<String> keyboard, File photo)
	{
		return new Pair<Pair<String, ArrayList<String>>, File>(new Pair<String, ArrayList<String>>(message, keyboard), photo);
	}
	
	public Task getTask()
	{
		return currentTask;
	}
}