import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Tests {

	@Test
	void testRandom() throws Exception {
		MyRandom rnd = new MyRandom(9);
		int result = 0;
		for (int i = 0; i < 10; i++)
		{
			result += rnd.next();
			//System.out.println(result);
		}
		Assert.assertEquals(45, result);
	}
	
	@Test
	void testGameGreeting() throws Exception {
		Assert.assertEquals("Привет, я супер чат-бот!\n" + "Вот что я умею:\n" + "/play - начать игру\n" 
				+ "/end - завершить игру\n" + "/help - справка\n" + "Пиши /play и погнали!", Game.greeting());
	}
	
	@Test
	void testGameHelp() throws Exception {
		Assert.assertEquals("Вот что я умею:\n" + "/play - начать игру\n" 
				+ "/end - завершить игру\n" + "/help - справка\n" + "Пиши /play и погнали!", Game.help());
	}
	
	@Test
	void testGameStart() throws Exception {
		Data data = new Data();
		Game.step("/end");
		Assert.assertTrue(data.hasQuestion(Game.step("/play")));
	}
	
	@Test
	void testGameEnd() throws Exception {
		Assert.assertEquals("До новых встреч!", Game.step("/end"));
	}
	
	@Test
	void testGameDubleStart() throws Exception {
		Game.step("/end");
		String question = Game.step("/play");
		Assert.assertEquals("Игра уже идет, отвечай на вопрос :\n\n" + question, Game.step("/play"));
	}
	
	@Test
	void testGameTrueAnswer() throws Exception {
		Data data = new Data();
		String question = Game.step("/play");
		String result = Game.step(data.GetAnswer(question));
		Assert.assertEquals("Красава!\n" + "Лови следующий вопрос: " + Game.getTask().getQuestion(), result);
	}
	
	@Test
	void testGameFalseAnswer() throws Exception {
		Game.step("/play");
		Assert.assertEquals("Попробуй еще раз...", Game.step("12345"));
	}
	
	@Test
	void testGameQuestionsOver() throws Exception {
		Data data = new Data();
		Game.step("/end");
		Game.step("/play");
		Pair question = Game.getTask();
		String result = null;
		for (int i = 0; i < data.QuestionsCount; i++)
		{
			result = Game.step(question.getAnswer());
			question = Game.getTask();
		}
		Assert.assertEquals("Вопросы кончились, молодец!", result);
	}
}
