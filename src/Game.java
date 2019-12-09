import java.util.Arrays;
import java.util.List;

enum Phase { None, Determine, Fight}

public class Game 
{
	private Data data;
	private Determiner determiner;
	private Fighter fight;
	private Phase phase;
	private Task currentTask;
	
	private static final int FIGHT_PHASE_INVITATION = 400;
	private static final String CREATE_FIGHT = "/new";
	
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
	
	public List<OutputData> step(InputData input)
	{
		switch (input.getData())
		{
			case ("/start"):
					return Arrays.asList(new OutputData(input.getChatId(), greeting()));
			case ("/play"):
				phase = Phase.Determine;
				return Arrays.asList(determiner.next(input));
			case ("/help"):
				return Arrays.asList(new OutputData(input.getChatId(), help()));
			case ("/fight"):
				if (!determiner.isComplited())
					return Arrays.asList(new OutputData(input.getChatId(), "Покемон еще не выбран.\n" + help()));	
				if (phase == Phase.Fight)
					return fight.next(input);
				else
				{
					fight = new Fighter(data.getCurrentPokemon(), data);
					phase = Phase.Fight;
					return fight.next(new InputData(input.getChatId(), input.getUserName(), CREATE_FIGHT));
				}			
		}
		if ( (FIGHT_PHASE_INVITATION + "").equalsIgnoreCase(input.getData().substring(0, 3)))
		{	
			fight = new Fighter(data.getCurrentPokemon(), data);
			phase = Phase.Fight;
		}
		if (phase == Phase.Determine)
			return Arrays.asList(determiner.next(input));
		if (phase == Phase.Fight)
			return fight.next(input);
		//default
		return Arrays.asList(new OutputData(input.getChatId(), "Не понимаю, что ты хочешь\n" + help()));
	}
	
	public Task getTask()
	{
		return currentTask;
	}
}