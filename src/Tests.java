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
	  Assert.assertEquals(Arrays.asList("������, � ����� ���-���!\n" + "��� ��� � ����:\n"
				+ "/play - ������ ����\n"
				+ "/help - �������\n"
				+ "/fight - ������ ���"), game.step("/start").getMessage()); }
	  
	  @Test void testGameHelp() throws Exception { Game game = new Game();
	  Assert.assertEquals("��� ��� � ����:\n" + "/play - ������ ����\n" +
	  "/help - �������\n" + "/fight - ������ ���",
	  game.help()); }
	  
	  @Test void testFightCommandWithoutPokemoneChoice() throws Exception { 
		  Game game = new Game();
		  Assert.assertEquals(Arrays.asList("������� ��� �� ������.\n" + game.help()),
		  game.step("/fight").getMessage()); 
	  }
	  
	  @Test void testPlayCommand() throws Exception { 
		  Game game = new Game();
		  OutputData out = game.step("/play");
		  Assert.assertEquals(Arrays.asList("������, ����� ������?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("��, �����!", "���, ��� ��� �� ����"),
				  out.getKeyboard());
	  }
	  
	  @Test void testDeterminerPhaseQuestions() throws Exception { 
		  Game game = new Game();
		  OutputData out = game.step("/play");
		  Assert.assertEquals(Arrays.asList("������, ����� ������?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("��, �����!", "���, ��� ��� �� ����"),
				  out.getKeyboard());
		  
		  out = game.step("00");
		  Assert.assertEquals(Arrays.asList("��� �� �� ������ ������ �������?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("����", "���", "���������", "������", "�������", "�������"),
				  out.getKeyboard());
		  
		  out = game.step("10");
		  Assert.assertEquals(Arrays.asList("�� ���������� � ������� �����������, �� ����� ����������?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("�������", "�����", "�������"),
				  out.getKeyboard());
		  
		  out = game.step("20");
		  Assert.assertEquals(Arrays.asList("��� ����� �� �������� �� ����� �������?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("���������", "������� �� ������", "�������"),
				  out.getKeyboard());
		  
		  out = game.step("30");
		  Assert.assertEquals(Arrays.asList("���� ���������� �������, ��� ������?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("���, ������, ���", "����� ���, ��� ������", "���� ���������� � ��������� ������������",
				  "����� ������ �������� � �����", "�� �� ������� ������!"),
				  out.getKeyboard());
	  }
	  
	  @Test void testDeterminerPhaseQuestionsWithAnswerWrongQuestion() throws Exception { 
		  Game game = new Game();
		  OutputData out = game.step("/play");
		  out = game.step("10");
		  Assert.assertEquals(Arrays.asList("������� �� ������� ������:", "������, ����� ������?"),
				  out.getMessage());
				  Assert.assertEquals(Arrays.asList("��, �����!", "���, ��� ��� �� ����"),
						  out.getKeyboard());
		  game.step("00");	  
		  out = game.step("50");
		  Assert.assertEquals(Arrays.asList("������� �� ������� ������:","��� �� �� ������ ������ �������?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("����", "���", "���������", "������", "�������", "�������"),
				  out.getKeyboard());
		  game.step("10");
		  out = game.step("00");
		  Assert.assertEquals(Arrays.asList("������� �� ������� ������:", "�� ���������� � ������� �����������, �� ����� ����������?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("�������", "�����", "�������"),
				  out.getKeyboard());
		  game.step("20");
		  out = game.step("20");
		  Assert.assertEquals(Arrays.asList("������� �� ������� ������:","��� ����� �� �������� �� ����� �������?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("���������", "������� �� ������", "�������"),
				  out.getKeyboard());
		  game.step("30");
		  out = game.step("30");
		  Assert.assertEquals(Arrays.asList("������� �� ������� ������:", "���� ���������� �������, ��� ������?"),
		  out.getMessage());
		  Assert.assertEquals(Arrays.asList("���, ������, ���", "����� ���, ��� ������", "���� ���������� � ��������� ������������",
				  "����� ������ �������� � �����", "�� �� ������� ������!"),
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
		  Assert.assertEquals(Arrays.asList("�����������, ��: ������"),
		  out.getMessage());
		  Assert.assertEquals("������.jpg", out.getImage().getName());
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
		  Assert.assertEquals(Arrays.asList("� ��� ������� ���������?"), out.getMessage());
		  Assert.assertEquals(Arrays.asList("��������� ���������", "������������"), out.getKeyboard());
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
		  Assert.assertEquals("���� ���������:", out.getMessage().get(0).substring(0, 15));
		  Assert.assertEquals(Arrays.asList("��", "���"), out.getKeyboard());
	  }
}
