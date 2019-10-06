public final class Game 
{
	private static Data data = new Data();
	private static MyRandom random = new MyRandom(data.QuestionsCount - 1);
	private static boolean isPlay = false;
	private static Pair task;
	//private enum commands {start, end, help};
	
	public static String greeting()
	{
		return "Привет, я супер чат-бот!\n" + data.Info;
	}
	
	public static String help()
	{
		return data.Info;
	}
	
	public static Pair getTask()
	{
		return task;
	}
	
	public static String step(String input) throws Exception
	{
		switch (input)
		{
			case ("/start"):
				return greeting();
			case ("/play"):
				if(isPlay) 
					return "Игра уже идет, отвечай на вопрос :\n\n" + task.getQuestion();
				else
				{
					task = data.getQuestion(random.next());
					isPlay = true;
					return task.getQuestion();	
				}
			case ("/help"):
				return data.Info;
			case ("/end"):
				isPlay = false;
				random.reset();
				return "До новых встреч!";	
		}
		if (input.equals(task.getAnswer()))
		{
			if (!random.hasNext())
			{
				isPlay = false;
				return "Вопросы кончились, молодец!";	
			}
			task = data.getQuestion(random.next());
			return "Красава!\n" + "Лови следующий вопрос: " + task.getQuestion();
			
		}
		else return "Попробуй еще раз...";	
	}
}