import java.util.Scanner;

public class Console {
	private Scanner scan = new Scanner(System.in); 
	private Game game;
	
	public Console(Game ourGame)
	{
		game = ourGame;
	}
	
	public void play() throws Exception
	{
		System.out.println(game.greeting());
		while (game.isPlay)
		{
			System.out.println(game.step(scan.nextLine()));
		}
	}
}