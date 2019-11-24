import java.io.File;
import java.util.ArrayList;
enum Phase { None, Determine, Fight}

public class Game 
{
	private Data data;
	private Determiner determiner;
	private Fight fight;
	private Phase phase;
	private Task currentTask;
	
	public Game()
	{
		data = new Data();
		determiner = new Determiner(data);
		fight = null;
		phase = Phase.None;

		currentTask = new Task();
	}
	
	public String greeting()
	{
		return "Привет, я супер чат-бот!\n" + data.getInfo();
	}
	
	public String help()
	{
		return data.getInfo();
	}
	
	public OutputData step(String input)
	{
		switch (input)
		{
			case ("/start"):
					return new OutputData(greeting());
			case ("/play"):
				data.reset();
				phase = Phase.Determine;
				return determiner.next(input);
			case ("/help"):
				return new OutputData(help());
			case ("/fight"):
				if (!determiner.isComplited())
					return new OutputData("Покемон еще не выбран.\n" + help());	
				if (phase == Phase.Fight)
					return fight.next(input);
				else
				{
					fight = new Fight(data.getCurrentPokemon(), data);
					phase = Phase.Fight;
					return fight.next(input);
				}			
		}			
		if (phase == Phase.Determine)
			return determiner.next(input);
		if (phase == Phase.Fight)
			return fight.next(input);
		//default
		return new OutputData("Не понимаю, что ты хочешь\n" + help());
	}
	
	public Task getTask()
	{
		return currentTask;
	}
}