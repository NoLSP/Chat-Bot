import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Arena {
	private static List<FightRoom> rooms = new ArrayList<FightRoom>();
	private static Map<String, Long> chatsIds = new HashMap<String, Long>();
	private static Map<String, FightRoom> waiters = new HashMap<String, FightRoom>();
	private static final int FIGHT_CLOSE = 410;
	
	public static List<OutputData> CreateRoom(Player fromPlayer, String userName)
	{
		
		Long toChatId = null;
		synchronized(chatsIds) { toChatId = chatsIds.get(userName);}
		if (toChatId == null)
			return Arrays.asList(new OutputData(fromPlayer.getChatId(), "Этого противника еще нет в игре)"));
		FightRoom room = new FightRoom(fromPlayer);
		synchronized(waiters) {waiters.put(userName, room);}
		OutputData invitation = room.getInvitation(toChatId);
		OutputData waiting = new OutputData(fromPlayer.getChatId(), "Приглашение отправлено, ожидание ответа...");
		return Arrays.asList(invitation, waiting);
	}
	
	public static void addPlayerData(String userName, Long hisChatId)
	{
		synchronized(chatsIds) { chatsIds.put(userName, hisChatId);}
	}
	
	public static List<OutputData> accept(Player player)
	{
		FightRoom room = null;
		synchronized(waiters) { room = waiters.get(player.getUserName());
		waiters.remove(player.getUserName());}
		room.addPlayer(player);
		synchronized(rooms) { rooms.add(room); }
		List<OutputData> out = new ArrayList<OutputData>();
		out.add(new OutputData(room.getPlayer1().getChatId(), room.getPlayer2().getUserName() + " принял приглашение!"));
		out.addAll(room.turn());
		return out;
	}
	
	public static List<OutputData> decline(Player player)
	{
		FightRoom room = null;
		synchronized(waiters) 
		{ 
			room = waiters.get(player.getUserName());
			waiters.remove(player.getUserName());
		}
		List<OutputData> out = new ArrayList<OutputData>();
		out.add(new OutputData(room.getPlayer1().getChatId(), player.getUserName() + " отклонил приглашение."));
		out.add(new OutputData(player.getChatId(), "Приглашение отклонено."));
		rooms.remove(room);
		return out;
	}
	
	public static List<OutputData> makeTurn(InputData input)
	{
		FightRoom room = getRoom(input.getUserName());
		if (room.isFightComplete())
			return Arrays.asList(new OutputData(input.getChatId(), "Бой уже закончен"));
		return room.turn(input);
	}
	
	private static FightRoom getRoom(String playerName)
	{
		synchronized(rooms) {
			for(FightRoom rm : rooms)
				if (rm.hasPlayer(playerName))
				{
					return rm;
				}
		}
		return null;
	}

	public static List<OutputData> closeFight(Player player)
	{
		FightRoom room = getRoom(player.getUserName());
		Player another = room.getAnotherPayer(player);
		room.refreshPlayers();
		rooms.remove(room);
		return Arrays.asList( new OutputData(player.getChatId(), FIGHT_CLOSE, "Бой прерван. Начать новый?", Arrays.asList("Да", "Нет")), 
							  new OutputData(another.getChatId(), FIGHT_CLOSE, "Противник прервал бой. Начать новый?", Arrays.asList("Да", "Нет")));
	}
}
