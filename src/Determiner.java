import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Determiner {
	private Data data;
	private ArrayList<Integer> userAnswers;
	private String currentQuestion;
	private Task currentTask;
	private boolean DeterminerPhaseComplited;
	
	private static final int DETERMINER_PHASE_CODE = 100;
	
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
	
	public OutputData next(InputData input)
	{
		if ("/play".equals(input.getData()))
		{
			data.reset();
			DeterminerPhaseComplited = false;
			userAnswers = new ArrayList<Integer>();
			currentTask = data.getNextTask();
			currentQuestion = currentTask.getQuestion();
			return new OutputData(input.getChatId(), DETERMINER_PHASE_CODE + data.getCurrentQuestionNumber(), Arrays.asList(currentQuestion), currentTask.getAnswers());
		}
		else
			return getNextQuestion(input);
	}
	
	private OutputData getNextQuestion(InputData input)
	{
		System.out.println("---Question answer processeed---");
		//input состоит из двух цифр: номер вопроса и вариант ответа
		int questionNumber = -1;
		int userAnswer = -1;
		//Отсекаем все, кроме ответа
		try {
			System.out.println("Input = " + input);
			questionNumber = Integer.parseInt(input.getData().substring(0, input.getData().indexOf('.')));
			userAnswer = Integer.parseInt(input.getData().substring(input.getData().indexOf('.') + 1));
		}
		catch (Exception e) {
			return new OutputData(input.getChatId(), "Не знаю о чем ты, воспользуйся справкой: /help");
		}
		System.out.println(data.getCurrentQuestionNumber() + " " +  questionNumber);
		if (questionNumber != DETERMINER_PHASE_CODE + data.getCurrentQuestionNumber())
			return new OutputData(input.getChatId(), DETERMINER_PHASE_CODE + data.getCurrentQuestionNumber(), Arrays.asList("Отвечай на текущий вопрос:", currentQuestion), currentTask.getAnswers());
		userAnswers.add(userAnswer);
		System.out.println("User have answered " + questionNumber + " question. User answer = " + userAnswer);
		if(data.hasTask())
		{
			currentTask = data.getNextTask();
			currentQuestion = currentTask.getQuestion();
			return new OutputData(input.getChatId(), DETERMINER_PHASE_CODE + data.getCurrentQuestionNumber(), Arrays.asList(currentQuestion), currentTask.getAnswers());
		}
		else
		{
			DeterminerPhaseComplited = true;
			return getResult(input.getChatId());
		}
	}
	
	private OutputData getResult(Long chatId) {
		Pair<String, File> result = data.getResult(userAnswers);
		return new OutputData(chatId, Arrays.asList(result.getItem1()), result.getItem2());
	}
}
