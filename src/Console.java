import java.util.List;
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
		print(game.greeting());
		while (game.getGameStatus())
		{
			print(game.step(scan.nextLine()));
		}
	}
	
	private void print(List<String> data)
	{
		for ( String str : data)
			System.out.println(str);
	}
}