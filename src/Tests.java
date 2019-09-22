import static org.junit.jupiter.api.Assertions.*;

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
		
		Assert.assertEquals("������, � ����� ���-���! \n" 
				+ "��� ��� � ����:\n"
				+ "start - ������ ����\n"
				+ "end - ��������� ����\n"
				+ "help - �������\n"
				+ "���� ����� � �������!", game.greeting());
	}
	
	@Test
	void testGameHelp() throws Exception {
		Game game = new Game();
		
		Assert.assertEquals("��� ��� � ����:\n"
				+ "start - ������ ����\n"
				+ "end - ��������� ����\n"
				+ "help - �������\n"
				+ "���� ����� � �������!", game.help());
	}
	
	@Test
	void testGameStart() throws Exception {
		Game game = new Game();
		Data data = new Data();
		Assert.assertTrue(data.hasQuestion(game.step("start")));
	}
}
