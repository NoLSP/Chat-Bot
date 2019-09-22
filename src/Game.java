public final class Game 
{
	private Data data = new Data();
	private MyRandom random = new MyRandom(data.QuestionsCount - 1);
	public boolean isPlay = true;
	private boolean isStarted = false;
	private Pair question;
	
	public String greeting()
	{
		return "Привет, я супер чат-бот! \n" + data.Info;
	}
	
	public String help()
	{
		return data.Info;
	}
	
	public String step(String input) throws Exception
	{
		if (input.equals("start"))
		{
			if(isStarted) 
				return "Игра уже идет, отвечай на вопрос :\n"
						+ question.getQuestion();
			else
			{
				question = data.getQuestion(random.getRandomNumber());
				isStarted = true;
				return question.getQuestion();	
			}		
		}
		else if(input.equals("help"))
		{
			return help();
		}
		else if(input.equals("end"))
		{
			isPlay = false;
			return "До новых встреч!";
		}
		else if (input.equals(question.getAnswer()))
		{
			if (!random.CanGetValue)
			{
				isPlay = false;
				return "Вопросы кончились, молодец!";	
			}
			question = data.getQuestion(random.getRandomNumber());
			return "Красава!\n"
					+ "Лови следующий вопрос:\n"
					+ question.getQuestion();
			
		}
		else return "Попробуй еще раз...";	
	}
}