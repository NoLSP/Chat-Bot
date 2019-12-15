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
	Assert.assertEquals(Arrays.asList("������, � ����� ���-���!\n" + "��� ��� � ����:\n" + "/play - ������ ����\n"
			+ "/help - �������\n" + "/fight - ������ ���"), game.step(new InputData((long) 12345, "name", "/start")).get(0).getMessage());}


	@Test
	void testGameHelp() throws Exception {
	Game game = new Game();
	Assert.assertEquals(Arrays.asList("��� ��� � ����:\n" + "/play - ������ ����\n" + "/help - �������\n" + "/fight - ������ ���"),
			game.step(new InputData((long) 12345, "name", "/help")).get(0).getMessage());}


	@Test
	void testFightCommandWithoutPokemoneChoice() throws Exception {
	Game game = new Game();
	Assert.assertEquals(Arrays.asList("������� ��� �� ������.\n" + game.help()), game.step(new InputData((long) 12345, "name", "/fight")).get(0).getMessage());}


	@Test
	void testPlayCommand() throws Exception {
	Game game = new Game();
	OutputData out = game.step(new InputData((long) 12345, "name", "/play")).get(0);
	Assert.assertEquals(Arrays.asList("������, ����� ������?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("��, �����!", "���, ��� ��� �� ����"), out.getKeyboard());}


@Test
	void testDeterminerPhaseQuestions() throws Exception {
	Data data = new Data();
	Determiner dt = new Determiner(data);
	OutputData out = dt.next(new InputData((long) 12345, "name", "/play"));
	Assert.assertEquals(Arrays.asList("������, ����� ������?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("��, �����!", "���, ��� ��� �� ����"), out.getKeyboard());

	out = dt.next(new InputData((long) 12345, "name", "100.0"));
	Assert.assertEquals(Arrays.asList("��� �� �� ������ ������ �������?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("����", "���", "���������", "������", "�������", "�������"),
			out.getKeyboard());

	out = dt.next(new InputData((long) 12345, "name", "101.0"));
	Assert.assertEquals(Arrays.asList("�� ���������� � ������� �����������, �� ����� ����������?"),
			out.getMessage());
	Assert.assertEquals(Arrays.asList("�������", "�����", "�������"), out.getKeyboard());

	out = dt.next(new InputData((long) 12345, "name", "102.0"));
	Assert.assertEquals(Arrays.asList("��� ����� �� �������� �� ����� �������?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("���������", "������� �� ������", "�������"), out.getKeyboard());

	out = dt.next(new InputData((long) 12345, "name", "103.0"));
	Assert.assertEquals(Arrays.asList("���� ���������� �������, ��� ������?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("���, ������, ���", "����� ���, ��� ������",
			"���� ���������� � ��������� ������������", "����� ������ �������� � �����", "�� �� ������� ������!"),
			out.getKeyboard());}


	@Test
	void testDeterminerPhaseQuestionsWithAnswerWrongQuestion() throws Exception {
	Data data = new Data();
	Determiner dt = new Determiner(data);
	OutputData out = dt.next(new InputData((long) 12345, "name", "/play"));
	out = dt.next(new InputData((long) 12345, "name", "105.0"));
	Assert.assertEquals(Arrays.asList("������� �� ������� ������:", "������, ����� ������?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("��, �����!", "���, ��� ��� �� ����"), out.getKeyboard());
	dt.next(new InputData((long) 12345, "name", "100.0"));
	out = dt.next(new InputData((long) 12345, "name", "105.0"));
	Assert.assertEquals(Arrays.asList("������� �� ������� ������:", "��� �� �� ������ ������ �������?"),
			out.getMessage());
	Assert.assertEquals(Arrays.asList("����", "���", "���������", "������", "�������", "�������"),
			out.getKeyboard());
	dt.next(new InputData((long) 12345, "name", "101.0"));
	out = dt.next(new InputData((long) 12345, "name", "105.0"));
	Assert.assertEquals(Arrays.asList("������� �� ������� ������:",
			"�� ���������� � ������� �����������, �� ����� ����������?"), out.getMessage());
	Assert.assertEquals(Arrays.asList("�������", "�����", "�������"), out.getKeyboard());
	dt.next(new InputData((long) 12345, "name", "102.0"));
	out = dt.next(new InputData((long) 12345, "name", "105.0"));
	Assert.assertEquals(Arrays.asList("������� �� ������� ������:", "��� ����� �� �������� �� ����� �������?"),
			out.getMessage());
	Assert.assertEquals(Arrays.asList("���������", "������� �� ������", "�������"), out.getKeyboard());
	dt.next(new InputData((long) 12345, "name", "103.0"));
	out = dt.next(new InputData((long) 12345, "name", "105.0"));
	Assert.assertEquals(Arrays.asList("������� �� ������� ������:", "���� ���������� �������, ��� ������?"),
			out.getMessage());
	Assert.assertEquals(Arrays.asList("���, ������, ���", "����� ���, ��� ������",
			"���� ���������� � ��������� ������������", "����� ������ �������� � �����", "�� �� ������� ������!"),
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
	Assert.assertEquals(Arrays.asList("�����������, ��: ������"), out.getMessage());
	Assert.assertEquals("������.jpg", out.getImage().getName());}


	/*
	 * @Test void testStartFightPhase() throws Exception { Fighter fight = new
	 * Fighter(new Pokemon("������", "info"), new Data()); OutputData out =
	 * fight.next(new InputData((long) 12345, "name", "/fight")).get(0);
	 * Assert.assertEquals(Arrays.asList("� ��� ������� ���������?"),
	 * out.getMessage()); Assert.assertEquals(Arrays.asList("��������� ���������",
	 * "������������"), out.getKeyboard());}
	 * 
	 * @Test void testSingleFightPhase() throws Exception { Fighter fight = new
	 * Fighter(new Pokemon("������", "info"), new Data()); OutputData out =
	 * fight.next(new InputData((long) 12345, "name", "/fight")).get(0); out =
	 * fight.next(new InputData((long) 12345, "name", "200.0")).get(0);
	 * Assert.assertEquals(fight.getStatus(), Status.Single);}
	 * 
	 * @Test void testStartMultiFightPhase() throws Exception { Fighter fight = new
	 * Fighter(new Pokemon("������", "info"), new Data()); OutputData out =
	 * fight.next(new InputData((long) 12345, "name", "/fight")).get(0); out =
	 * fight.next(new InputData((long) 12345, "name", "200.1")).get(0);
	 * Assert.assertEquals(fight.getStatus(), Status.Multi);}
	 * 
	 * @Test void testChoseEnemyMultiFightPhase() throws Exception { Fighter fight =
	 * new Fighter(new Pokemon("������", "info"), new Data()); OutputData out =
	 * fight.next(new InputData((long) 12345, "name", "/fight")).get(0); out =
	 * fight.next(new InputData((long) 12345, "name", "200.1")).get(0);
	 * Assert.assertEquals(out.getMessage().get(0),
	 * "� ��� ���������? (����� user name)"); out = fight.next(new InputData((long)
	 * 12345, "name", "blablabla")).get(0);
	 * Assert.assertEquals(out.getMessage().get(0),
	 * "����� ���������� ��� ��� � ����)");}
	 */
}
