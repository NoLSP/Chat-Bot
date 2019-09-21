import java.util.Scanner;

public class Program 
{
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		boolean isPlay = true;
		String command = "";
		Game game = new Game();
		game.greeting();
		while(isPlay)
		{
			command = scan.nextLine();
			if (command.equals("start"))
			{
				game.start();
			}
			else if(command.equals("help"))
			{
				game.help();
			}
			else if(command.equals("end"))
			{
				isPlay = false;
			}
		}
		System.out.println("До новых встреч!");
	}

}
