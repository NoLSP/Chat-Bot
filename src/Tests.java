import java.util.Arrays;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Tests {
	@Test
	void testRandom() throws Exception {
		MyRandom rnd = new MyRandom(9);
		int result = 0;
		for (int i = 0; i < 10; i++)
			result += rnd.next();
		Assert.assertEquals(45, result);
	}

	@Test
	void testGameGreeting() throws Exception {
	Game game = new Game();
	Assert.assertEquals(Arrays.asList("Привет, я супер чат-бот!\n" + "Вот что я умею:\n" + "/play - начать игру\n"
			+ "/help - справка\n" + "/fight - начать бой"), game.step(new InputData((long) 12345, "name", "/start")).get(0).getMessage());}


	@Test
	void testGameHelp() throws Exception {
	Game game = new Game();
	Assert.assertEquals(Arrays.asList("Вот что я умею:\n" + "/play - начать игру\n" + "/help - справка\n" + "/fight - начать бой"),
			game.step(new InputData((long) 12345, "name", "/help")).get(0).getMessage());}


	@Test
	void testFightCommandWithoutPokemoneChoice() throws Exception {
	Game game = new Game();
	Assert.assertEquals(Arrays.asList("Покемон еще не выбран.\n" + game.help()), game.step(new InputData((long) 12345, "name", "/fight")).get(0).getMessage());}


	@Test
	void testPlayCommand() throws Exception {
	Game game = new Game();
	OutputData out = game.step(new InputData((long) 12345, "name", "/play")).get(0);
	Assert.assertEquals(Arrays.asList("Привет, давай начнем?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("Да, давай!", "Нет, мне это не надо"), out.getKeyboard());}


@Test
	void testDeterminerPhaseQuestions() throws Exception {
	Data data = new Data();
	Determiner dt = new Determiner(data);
	OutputData out = dt.next(new InputData((long) 12345, "name", "/play"));
	Assert.assertEquals(Arrays.asList("Привет, давай начнем?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("Да, давай!", "Нет, мне это не надо"), out.getKeyboard());

	out = dt.next(new InputData((long) 12345, "name", "100.0"));
	Assert.assertEquals(Arrays.asList("Как бы ты назвал своего ребенка?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("Джон", "Лев", "Александр", "Сергей", "Альберт", "Николай"),
			out.getKeyboard());

	out = dt.next(new InputData((long) 12345, "name", "101.0"));
	Assert.assertEquals(Arrays.asList("Ты отправился в дальнее путешествие, на каком транспорте?"),
			out.getMessage());
	Assert.assertEquals(Arrays.asList("Самолет", "Поезд", "Корабль"), out.getKeyboard());

	out = dt.next(new InputData((long) 12345, "name", "102.0"));
	Assert.assertEquals(Arrays.asList("Как часто ты смотришь на людей свысока?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("Постоянно", "Общаюсь на равных", "Никогда"), out.getKeyboard());

	out = dt.next(new InputData((long) 12345, "name", "103.0"));
	Assert.assertEquals(Arrays.asList("Тебя остановили гопники, что делать?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("Ран, Форест, ран", "Отдам все, что просят",
			"Буду обманывать и попытаюсь договориться", "Начну сыпать угрозами в ответ", "Им не собрать костей!"),
			out.getKeyboard());}


	@Test
	void testDeterminerPhaseQuestionsWithAnswerWrongQuestion() throws Exception {
	Data data = new Data();
	Determiner dt = new Determiner(data);
	OutputData out = dt.next(new InputData((long) 12345, "name", "/play"));
	out = dt.next(new InputData((long) 12345, "name", "105.0"));
	Assert.assertEquals(Arrays.asList("Отвечай на текущий вопрос:", "Привет, давай начнем?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("Да, давай!", "Нет, мне это не надо"), out.getKeyboard());
	dt.next(new InputData((long) 12345, "name", "100.0"));
	out = dt.next(new InputData((long) 12345, "name", "105.0"));
	Assert.assertEquals(Arrays.asList("Отвечай на текущий вопрос:", "Как бы ты назвал своего ребенка?"),
			out.getMessage());
	Assert.assertEquals(Arrays.asList("Джон", "Лев", "Александр", "Сергей", "Альберт", "Николай"),
			out.getKeyboard());
	dt.next(new InputData((long) 12345, "name", "101.0"));
	out = dt.next(new InputData((long) 12345, "name", "105.0"));
	Assert.assertEquals(Arrays.asList("Отвечай на текущий вопрос:",
			"Ты отправился в дальнее путешествие, на каком транспорте?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("Самолет", "Поезд", "Корабль"), out.getKeyboard());
	dt.next(new InputData((long) 12345, "name", "102.0"));
	out = dt.next(new InputData((long) 12345, "name", "105.0"));
	Assert.assertEquals(Arrays.asList("Отвечай на текущий вопрос:", "Как часто ты смотришь на людей свысока?"),
			out.getMessage());
	Assert.assertEquals(Arrays.asList("Постоянно", "Общаюсь на равных", "Никогда"), out.getKeyboard());
	dt.next(new InputData((long) 12345, "name", "103.0"));
	out = dt.next(new InputData((long) 12345, "name", "105.0"));
	Assert.assertEquals(Arrays.asList("Отвечай на текущий вопрос:", "Тебя остановили гопники, что делать?"),
			out.getMessage());
	Assert.assertEquals(Arrays.asList("Ран, Форест, ран", "Отдам все, что просят",
			"Буду обманывать и попытаюсь договориться", "Начну сыпать угрозами в ответ", "Им не собрать костей!"),
			out.getKeyboard());}


	@Test
	void testPicachuAnswers() throws Exception {
	Data data = new Data();
	Determiner dt = new Determiner(data);
	dt.next(new InputData((long) 12345, "name", "/play"));
	dt.next(new InputData((long) 12345, "name", "100.0"));
	dt.next(new InputData((long) 12345, "name", "101.5"));
	dt.next(new InputData((long) 12345, "name", "102.1"));
	dt.next(new InputData((long) 12345, "name", "103.1"));
	OutputData out = dt.next(new InputData((long) 12345, "name", "104.0"));
	Assert.assertEquals(Arrays.asList("Поздравляем, ты: Пикачу"), out.getMessage());
	Assert.assertEquals("Пикачу.jpg", out.getImage().getName());}


	/*
	 * @Test void testStartFightPhase() throws Exception { Fighter fight = new
	 * Fighter(new Pokemon("Пикачу", "info"), new Data()); OutputData out =
	 * fight.next(new InputData((long) 12345, "name", "/fight")).get(0);
	 * Assert.assertEquals(Arrays.asList("С кем желаешь сразиться?"),
	 * out.getMessage()); Assert.assertEquals(Arrays.asList("Случайный противник",
	 * "Пользователь"), out.getKeyboard());}
	 * 
	 * @Test void testSingleFightPhase() throws Exception { Fighter fight = new
	 * Fighter(new Pokemon("Пикачу", "info"), new Data()); OutputData out =
	 * fight.next(new InputData((long) 12345, "name", "/fight")).get(0); out =
	 * fight.next(new InputData((long) 12345, "name", "200.0")).get(0);
	 * Assert.assertEquals(fight.getStatus(), Status.Single);}
	 * 
	 * @Test void testStartMultiFightPhase() throws Exception { Fighter fight = new
	 * Fighter(new Pokemon("Пикачу", "info"), new Data()); OutputData out =
	 * fight.next(new InputData((long) 12345, "name", "/fight")).get(0); out =
	 * fight.next(new InputData((long) 12345, "name", "200.1")).get(0);
	 * Assert.assertEquals(fight.getStatus(), Status.Multi);}
	 * 
	 * @Test void testChoseEnemyMultiFightPhase() throws Exception { Fighter fight =
	 * new Fighter(new Pokemon("Пикачу", "info"), new Data()); OutputData out =
	 * fight.next(new InputData((long) 12345, "name", "/fight")).get(0); out =
	 * fight.next(new InputData((long) 12345, "name", "200.1")).get(0);
	 * Assert.assertEquals(out.getMessage().get(0),
	 * "С кем конкретно? (Введи user name)"); out = fight.next(new InputData((long)
	 * 12345, "name", "blablabla")).get(0);
	 * Assert.assertEquals(out.getMessage().get(0),
	 * "Этого противника еще нет в игре)");}
	 */
}
