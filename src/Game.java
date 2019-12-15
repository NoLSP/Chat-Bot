import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum Phase { None, Determine, Fight}

public class Game 
{
	private Data data;
	private Determiner determiner;
	private Phase phase;
	private Task currentTask;
	private FightRoom fightRoom = null;
	
	private static final int FIGHT_PHASE_INVITATION = 403;
	
	private static final int FIGHT_PHASE_INVITATION_YES = 0;
	private static final int FIGHT_PHASE_INVITATION_NO = 1;
	
	public Game()
	{
		data = new Data();
		determiner = new Determiner(data);
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
				determiner = new Determiner(data);
				fightRoom = null;
				data.reset();
				return Arrays.asList(determiner.next(input));
			case ("/help"):
				return Arrays.asList(new OutputData(input.getChatId(), help()));
			case ("/fight"):
				if (!determiner.isComplited())
					return Arrays.asList(new OutputData(input.getChatId(), "Покемон еще не выбран.\n" + help()));	
				if (phase == Phase.Fight)
					return fightRoom.next(input);
				else
				{
					Player player = new Player(data.getCurrentPokemon(), input.getUserName(), input.getChatId());
					fightRoom = new FightRoom(player);
					phase = Phase.Fight;
					return fightRoom.next(new InputData(input.getChatId(), input.getUserName(), "/fight"));
				}			
		}
		//эта проверка нужна для удаления комнаты проигравшим игроком в мультиплеерном бою
		if (fightRoom != null && fightRoom.isComplited() && phase == Phase.Fight)
		{
			fightRoom = null;
			phase = Phase.None;
		}
		if ( (FIGHT_PHASE_INVITATION + "." + FIGHT_PHASE_INVITATION_YES).equalsIgnoreCase(input.getData()))
		{	
			Player player = new Player(data.getCurrentPokemon(), input.getUserName(), input.getChatId());
			fightRoom = Arena.joinRoom(player);
			phase = Phase.Fight;
		}
		if ( (FIGHT_PHASE_INVITATION + "." + FIGHT_PHASE_INVITATION_NO).equalsIgnoreCase(input.getData()))
		{	
			return Arena.declineInvitation(input.getUserName(), input.getChatId());
		}
		if (phase == Phase.Determine)
			return Arrays.asList(determiner.next(input));
		if (phase == Phase.Fight)
		{
			List<OutputData> out = fightRoom.next(input);
			if (fightRoom.isComplited())
			{
				Arena.deleteRoom(fightRoom);
				OutputData out1 = new OutputData(fightRoom.getPlayer1().getChatId(), "Бой окончен!");
				OutputData out2 = new OutputData(fightRoom.getPlayer2().getChatId(), "Бой окончен!");
				fightRoom = null;
				phase = Phase.None;
				return Arrays.asList(out.get(0),out.get(1), out1, out2);
			}
			return  out;
		}
		//default
		return Arrays.asList(new OutputData(input.getChatId(), "Не понимаю, что ты хочешь\n" + help()));
	}

	public Task getTask()
	{
		return currentTask;
	}
}