import java.util.Arrays;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Tests {
	  @Test void testRandom() throws Exception { MyRandom rnd = new MyRandom(9);
	  int result = 0; 
	  for (int i = 0; i < 10; i++) 
		  result += rnd.next(); 
	  Assert.assertEquals(45, result); 
	  }
	  
	  @Test void testGameGreeting() throws Exception { Game game = new Game();
	  Assert.assertEquals(Arrays.asList("Привет, я супер чат-бот!\n" + "Вот что я умею:\n"
				+ "/play - начать игру\n"
				+ "/help - справка\n"
				+ "/fight - начать бой"), game.step("/start").getMessage()); }
	  
	  @Test void testGameHelp() throws Exception { Game game = new Game();
	  Assert.assertEquals("Вот что я умею:\n" + "/play - начать игру\n" +
	  "/help - справка\n" + "/fight - начать бой",
	  game.help()); }
	  
	  @Test void testFightCommandWithoutPokemoneChoice() throws Exception { 
		  Game game = new Game();
		  Assert.assertEquals(Arrays.asList("Покемон еще не выбран.\n" + game.help()),
		  game.step("/fight").getMessage()); 
	  }
	  
	  @Test void testPlayCommand() throws Exception { 
		  Game game = new Game();
		  OutputData out = game.step("/play");
		  Assert.assertEquals(Arrays.asList("Привет, давай начнем?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("Да, давай!", "Нет, мне это не надо"),
				  out.getKeyboard());
	  }
	  
	  @Test void testDeterminerPhaseQuestions() throws Exception { 
		  Game game = new Game();
		  OutputData out = game.step("/play");
		  Assert.assertEquals(Arrays.asList("Привет, давай начнем?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("Да, давай!", "Нет, мне это не надо"),
				  out.getKeyboard());
		  
		  out = game.step("00");
		  Assert.assertEquals(Arrays.asList("Как бы ты назвал своего ребенка?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("Джон", "Лев", "Александр", "Сергей", "Альберт", "Николай"),
				  out.getKeyboard());
		  
		  out = game.step("10");
		  Assert.assertEquals(Arrays.asList("Ты отправился в дальнее путешествие, на каком транспорте?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("Самолет", "Поезд", "Корабль"),
				  out.getKeyboard());
		  
		  out = game.step("20");
		  Assert.assertEquals(Arrays.asList("Как часто ты смотришь на людей свысока?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("Постоянно", "Общаюсь на равных", "Никогда"),
				  out.getKeyboard());
		  
		  out = game.step("30");
		  Assert.assertEquals(Arrays.asList("Тебя остановили гопники, что делать?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("Ран, Форест, ран", "Отдам все, что просят", "Буду обманывать и попытаюсь договориться",
				  "Начну сыпать угрозами в ответ", "Им не собрать костей!"),
				  out.getKeyboard());
	  }
	  
	  @Test void testDeterminerPhaseQuestionsWithAnswerWrongQuestion() throws Exception { 
		  Game game = new Game();
		  OutputData out = game.step("/play");
		  out = game.step("10");
		  Assert.assertEquals(Arrays.asList("Отвечай на текущий вопрос:", "Привет, давай начнем?"),
				  out.getMessage());
				  Assert.assertEquals(Arrays.asList("Да, давай!", "Нет, мне это не надо"),
						  out.getKeyboard());
		  game.step("00");	  
		  out = game.step("50");
		  Assert.assertEquals(Arrays.asList("Отвечай на текущий вопрос:","Как бы ты назвал своего ребенка?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("Джон", "Лев", "Александр", "Сергей", "Альберт", "Николай"),
				  out.getKeyboard());
		  game.step("10");
		  out = game.step("00");
		  Assert.assertEquals(Arrays.asList("Отвечай на текущий вопрос:", "Ты отправился в дальнее путешествие, на каком транспорте?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("Самолет", "Поезд", "Корабль"),
				  out.getKeyboard());
		  game.step("20");
		  out = game.step("20");
		  Assert.assertEquals(Arrays.asList("Отвечай на текущий вопрос:","Как часто ты смотришь на людей свысока?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("Постоянно", "Общаюсь на равных", "Никогда"),
				  out.getKeyboard());
		  game.step("30");
		  out = game.step("30");
		  Assert.assertEquals(Arrays.asList("Отвечай на текущий вопрос:", "Тебя остановили гопники, что делать?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("Ран, Форест, ран", "Отдам все, что просят", "Буду обманывать и попытаюсь договориться",
				  "Начну сыпать угрозами в ответ", "Им не собрать костей!"),
				  out.getKeyboard());
	  }
	  
	  @Test void testPicachuAnswers() throws Exception { 
		  Game game = new Game();
		  game.step("/play");
		  game.step("00");
		  game.step("15");
		  game.step("21");
		  game.step("31");
		  OutputData out = game.step("41");
		  Assert.assertEquals(Arrays.asList("Поздравляем, ты: Пикачу"),
		  out.getMessage());
		  Assert.assertEquals("Пикачу.jpg", out.getImage().getName());
	  }
	  
	  @Test void testStartFightPhase() throws Exception { 
		  Game game = new Game();
		  game.step("/play");
		  game.step("00");
		  game.step("15");
		  game.step("21");
		  game.step("31");
		  game.step("41");
		  OutputData out = game.step("/fight");
		  Assert.assertEquals(Arrays.asList("С кем желаешь сразиться?"), out.getMessage());
		  Assert.assertEquals(Arrays.asList("Случайный противник", "Пользователь"), out.getKeyboard());
	  }
	  
	  @Test void testFightWithRandomEnemy() throws Exception { 
		  Game game = new Game();
		  game.step("/play");
		  game.step("00");
		  game.step("15");
		  game.step("21");
		  game.step("31");
		  game.step("41");
		  game.step("/fight");
		  OutputData out = game.step("00");
		  Assert.assertEquals("Твой противник:", out.getMessage().get(0).substring(0, 15));
		  Assert.assertEquals(Arrays.asList("Да", "Нет"), out.getKeyboard());
	  }
}
