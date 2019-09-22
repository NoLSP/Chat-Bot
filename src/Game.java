public final class Game 
{
	private Data data = new Data();
	private MyRandom random = new MyRandom(data.QuestionsCount - 1);
	public boolean isPlay = true;
	private boolean isStarted = false;
	private Pair question;
	
	public String greeting()
	{
		return "������, � ����� ���-���! \n" + data.Info;
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
				return "���� ��� ����, ������� �� ������ :\n"
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
			return "�� ����� ������!";
		}
		else if (input.equals(question.getAnswer()))
		{
			if (!random.CanGetValue)
			{
				isPlay = false;
				return "������� ���������, �������!";	
			}
			question = data.getQuestion(random.getRandomNumber());
			return "�������!\n"
					+ "���� ��������� ������:\n"
					+ question.getQuestion();
			
		}
		else return "�������� ��� ���...";	
	}
}