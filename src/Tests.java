import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Tests {

	@Test
	void testRandom() throws Exception {
		MyRandom rnd = new MyRandom(9);
		int result = 0;
		for (int i = 0; i < 10; i++)
		{
			result += rnd.getRandomNumber();
			//System.out.println(result);
		}
		Assert.assertEquals(45, result);
	}
	
	@Test
	void testGameGreeting() throws Exception {
		Game game = new Game();
		
		Assert.assertEquals(Arrays.asList("������, � ����� ���-���!", "��� ��� � ����:", "start - ������ ����",
				"end - ��������� ����", "help - �������", "���� ����� � �������!"), game.greeting());
	}
	
	@Test
	void testGameHelp() throws Exception {
		Game game = new Game();
		
		Assert.assertEquals(Arrays.asList("��� ��� � ����:", "start - ������ ����", "end - ��������� ����",
				"help - �������", "���� ����� � �������!"), game.help());
	}
	
	@Test
	void testGameStart() throws Exception {
		Game game = new Game();
		Data data = new Data();
		Assert.assertTrue(data.hasQuestion(game.step("start").get(0)));
	}
	
	@Test
	void testGameEnd() throws Exception {
		Game game = new Game();
		Assert.assertEquals(Arrays.asList("�� ����� ������!"), game.step("end"));
	}
	
	@Test
	void testGameDubleStart() throws Exception {
		Game game = new Game();
		String question = game.step("start").get(0);
		Assert.assertEquals(Arrays.asList("���� ��� ����, ������� �� ������ :", question), game.step("start"));
	}
	
	@Test
	void testGameTrueAnswer() throws Exception {
		Game game = new Game();
		Data data = new Data();
		String question = game.step("start").get(0);
		List<String> result = game.step(data.GetAnswer(question));
		Assert.assertEquals( Arrays.asList("�������!", "���� ��������� ������:", game.getTask().getQuestion()), result);
	}
	
	@Test
	void testGameFalseAnswer() throws Exception {
		Game game = new Game();
		game.step("start");
		Assert.assertEquals(Arrays.asList("�������� ��� ���..."), game.step("12345"));
	}
	
	@Test
	void testGameQuestionsOver() throws Exception {
		Game game = new Game();
		Data data = new Data();
		game.step("start");
		Pair question = game.getTask();
		List<String> result = null;
		for (int i = 0; i < data.QuestionsCount; i++)
		{
			result = game.step(question.getAnswer());
			question = game.getTask();
		}
		Assert.assertEquals(Arrays.asList("������� ���������, �������!"), result);
	}
}
