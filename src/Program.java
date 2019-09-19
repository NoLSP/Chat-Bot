import java.util.Scanner;

public class Program 
{
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		System.out.println("Привет, я супер чат-бот! \n"
				+ "Вот что я умею:\n"
				+ "\\start - начать игру\n"
				+ "\\end - завершить игру\n"
				+ "\\help - справка\n"
				+ "Пиши старт и погнали!");
		boolean isPlay = true;
		String command = "";
		while(isPlay)
		{
			command = scan.nextLine();
			
		}
	}

}
