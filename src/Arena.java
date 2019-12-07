import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Arena {
	private static List<FightRoom> rooms = new ArrayList<FightRoom>();
	private static Map<String, Long> chatsIds = new HashMap<String, Long>();
	private static Map<String, FightRoom> waiters = new HashMap<String, FightRoom>();
	
	public static List<OutputData> CreateRoom(Player fromPlayer, String userName)
	{
		
		Long toChatId = chatsIds.get(userName);
		if (toChatId == null)
			return Arrays.asList(new OutputData(fromPlayer.getChatId(), "Ётого противника еще нет в игре)"));
		
		FightRoom room = new FightRoom(fromPlayer);
		waiters.put(userName, room);
		OutputData invitation = room.getInvitation(toChatId);
		OutputData waiting = new OutputData(fromPlayer.getChatId(), "ѕриглашение отправлено, ожидание ответа...");
		return Arrays.asList(invitation, waiting);
	}
	
	public static void addPlayerData(String userName, Long hisChatId)
	{
		chatsIds.put(userName, hisChatId);
	}
	
	public static List<OutputData> accept(Player player)
	{
		FightRoom room = waiters.get(player.getUserName());
		waiters.remove(player.getUserName());
		room.addPlayer(player);
		rooms.add(room);
		List<OutputData> out = new ArrayList<OutputData>();
		out.add(new OutputData(room.getPlayer1().getChatId(), room.getPlayer2().getUserName() + " прин€л приглашение!"));
		out.addAll(room.turn());
		return out;
	}
	
	public static List<OutputData> makeTurn(InputData input)
	{
		FightRoom room = null;
		for(FightRoom rm : rooms)
			if (rm.hasPlayer(input.getUserName()))
			{
				room = rm;
				break;
			}
		if (room.isFightComplete())
			return Arrays.asList(new OutputData(input.getChatId(), "Ѕой уже закончен"));
		return room.turn(input);
	}
}
