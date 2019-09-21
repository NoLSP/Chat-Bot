import java.util.Scanner;

public class Game 
{
	private Data data = new Data();
	private MyRandom random = new MyRandom(data.QuestionsCount - 1);
	private Scanner scan = new Scanner(System.in);
	private boolean isPlay;
	
	public void greeting()
	{
		System.out.println("Привет, я супер чат-бот! \n" + data.Info);
	}
	
	public void help()
	{
		System.out.println(data.Info);
	}
	
	public void start()
	{
		isPlay = true;
		String input;
		Pair question;
		while(isPlay)
		{
			System.out.println(random.CanGetValue);
			scan.nextLine();
			if (!random.CanGetValue) break;
			int number = random.getRandomNumber();
			question = data.getQuestion(number);
			System.out.println(question.getQuestion());
			input = scan.nextLine();
			if (input.equals(question.getAnswer()))
			{
				System.out.println("Красава!");
			}
			
		}
	}
}
