import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Arena {
	private static List<FightRoom> rooms = new ArrayList<FightRoom>();
	private static Map<String, Long> chatsIds = new HashMap<String, Long>();
	
	private static final int FIGHT_CLOSE = 410;
	
	public static void addPlayerData(String userName, Long hisChatId)
	{
		synchronized(chatsIds) { chatsIds.put(userName, hisChatId);}
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
	
	public static void addRoom(FightRoom room)
	{
		rooms.add(room);
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

	public static Long getChatId(String name) {
		if (chatsIds.containsKey(name))
			return chatsIds.get(name);
		else return (long) -1;
	}

	public static FightRoom joinRoom(Player player) 
	{
		FightRoom room = getRoom(player.getUserName());
		room.addPlayer(player);
		return room;
	}

	public static List<OutputData> declineInvitation(String userName, Long decliningChatId) 
	{
		FightRoom room = getRoom(userName);
		Player player1 = room.getPlayer1();
		room.complete();
		OutputData out1 = new OutputData(player1.getChatId(), "Пользователь " + userName + " отклонил приглашение");
		OutputData out2 = new OutputData(decliningChatId, "Приглашение отклонено");
		return Arrays.asList(out1, out2);
	}

	public static void deleteRoom(FightRoom room) {
		rooms.remove(room);
	}
}
