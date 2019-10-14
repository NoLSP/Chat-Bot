//import org.junit.Assert;
//import org.junit.jupiter.api.Test;

class Tests {

	/*
	 * @Test void testRandom() throws Exception { MyRandom rnd = new MyRandom(9);
	 * int result = 0; for (int i = 0; i < 10; i++) { result += rnd.next();
	 * //System.out.println(result); } Assert.assertEquals(45, result); }
	 * 
	 * @Test void testGameGreeting() throws Exception { Game game = new Game();
	 * Assert.assertEquals("Привет, я супер чат-бот!\n" + "Вот что я умею:\n" +
	 * "/play - начать игру\n" + "/end - завершить игру\n" + "/help - справка\n" +
	 * "Пиши /play и погнали!", game.greeting()); }
	 * 
	 * @Test void testGameHelp() throws Exception { Game game = new Game();
	 * Assert.assertEquals("Вот что я умею:\n" + "/play - начать игру\n" +
	 * "/end - завершить игру\n" + "/help - справка\n" + "Пиши /play и погнали!",
	 * game.help()); }
	 */
	
	/*
	 * @Test void testGameStart() throws Exception { Game game = new Game(); Data
	 * data = new Data(); game.step("/end");
	 * Assert.assertTrue(data.hasQuestion(game.step("/play"))); }
	 * 
	 * @Test void testGameEnd() throws Exception { Game game = new Game();
	 * Assert.assertEquals("До новых встреч!", game.step("/end")); }
	 * 
	 * @Test void testGameDubleStart() throws Exception { Game game = new Game();
	 * String question = game.step("/play");
	 * Assert.assertEquals("Игра уже идет, отвечай на вопрос :\n\n" + question,
	 * game.step("/play")); }
	 * 
	 * @Test void testGameTrueAnswer() throws Exception { Data data = new Data();
	 * Game game = new Game(); String question = game.step("/play"); String result =
	 * game.step(data.GetAnswer(question)); Assert.assertEquals("Красава!\n" +
	 * "Лови следующий вопрос: " + game.getTask().getQuestion(), result); }
	 * 
	 * @Test void testGameFalseAnswer() throws Exception { Game game = new Game();
	 * game.step("/play"); Assert.assertEquals("Попробуй еще раз...",
	 * game.step("12345")); }
	 * 
	 * @Test void testGameQuestionsOver() throws Exception { Data data = new Data();
	 * Game game = new Game(); game.step("/play"); Pair question = game.getTask();
	 * String result = null; for (int i = 0; i < data.QuestionsCount; i++) { result
	 * = game.step(question.getAnswer()); question = game.getTask(); }
	 * Assert.assertEquals("Вопросы кончились, молодец!", result); }
	 */
}
