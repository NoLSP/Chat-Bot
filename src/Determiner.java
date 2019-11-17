import java.io.File;
import java.util.ArrayList;

public class Determiner {
	private Data data;
	private ArrayList<Integer> userAnswers;
	private String currentQuestion;
	private Task currentTask;
	private boolean DeterminerPhaseComplited;
	
	public Determiner(Data dt)
	{
		data = dt;
		userAnswers = new ArrayList<Integer>();
		currentQuestion = "";
		currentTask = new Task();
		DeterminerPhaseComplited = false;
	}
	
	public boolean isComplited()
	{
		return DeterminerPhaseComplited;
	}
	
	public OutputData next(String input)
	{
		if ("/play".equals(input))
		{
			DeterminerPhaseComplited = false;
			userAnswers = new ArrayList<Integer>();
			currentTask = data.getNextTask();
			currentQuestion = currentTask.getQuestion();
			return new OutputData(currentQuestion, currentTask.getAnswers());
		}
		else
			return getNextQuestion(input);
	}
	
	private OutputData getNextQuestion(String input)
	{
		System.out.println("---Question answer processeed---");
		//input состоит из двух цифр: номер вопроса и вариант ответа
		int questionNumber = -1;
		int userAnswer = -1;
		//Отсекаем все, кроме ответа
		try {
			System.out.println("Input = " + input);
			questionNumber = Integer.parseInt(input.substring(0, 1));
			userAnswer = Integer.parseInt(input.substring(1));
		}
		catch (Exception e) {
			return new OutputData("Не знаю о чем ты, воспользуйся справкой: /help");
		}
		System.out.println(data.getCurrentQuestionNumber() + " " +  questionNumber);
		if (questionNumber != data.getCurrentQuestionNumber())
			return new OutputData(data.getCurrentQuestionNumber() + "Отвечай на текущий вопрос:\n" + currentQuestion.substring(1), currentTask.getAnswers());
		userAnswers.add(userAnswer);
		System.out.println("User have answered " + questionNumber + " question. User answer = " + userAnswer);
		if(data.hasTask())
		{
			currentTask = data.getNextTask();
			currentQuestion = currentTask.getQuestion();
			return new OutputData(currentQuestion, currentTask.getAnswers());
		}
		else
		{
			DeterminerPhaseComplited = true;
			return getResult();
		}
	}
	
	private OutputData getResult() {
		Pair<String, File> result = data.getResult(userAnswers);
		return new OutputData(result.getItem1(), null, result.getItem2());
	}
}
