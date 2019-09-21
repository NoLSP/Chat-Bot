public class Data 
{
	private Pair[] Questions;
	public int QuestionsCount = 0;
	public String Info = "��� ��� � ����:\n"
			+ "start - ������ ����\n"
			+ "end - ��������� ����\n"
			+ "help - �������\n"
			+ "���� ����� � �������!"; 
	
	public Data()
	{
		Questions = new Pair[2];
		for (int i = 0; i <= 1; i++)
			Questions[i] = new Pair();
		Questions[0].set("���� �������� ������?", "12 ������");
		Questions[1].set("���� �������� ������?", "21 �������");
		QuestionsCount = Questions.length;
	};
	
	public Pair getQuestion(int number)
	{
		return Questions[number];
	}
}
