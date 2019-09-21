public class Data 
{
	private Pair[] Questions;
	public int QuestionsCount = 0;
	public String Info = "Вот что я умею:\n"
			+ "start - начать игру\n"
			+ "end - завершить игру\n"
			+ "help - справка\n"
			+ "Пиши старт и погнали!"; 
	
	public Data()
	{
		Questions = new Pair[2];
		for (int i = 0; i <= 1; i++)
			Questions[i] = new Pair();
		Questions[0].set("День рождения Артема?", "12 апреля");
		Questions[1].set("День рождения Никиты?", "21 декабря");
		QuestionsCount = Questions.length;
	};
	
	public Pair getQuestion(int number)
	{
		return Questions[number];
	}
}
