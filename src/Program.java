public class Program 
{	
	public static void main(String[] args) throws Exception
	{
		Game game = new Game();
		Console console = new Console(game);
		console.play();
	}
}