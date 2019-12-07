import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FightRoom {
	private Player player1;
	private Player player2;
	private boolean isPlayer1Turn;
	private boolean fightIsComplete = false; 
	
	private static final int INVITATION = 400;
	private static final int FIGHT = 401;
	
	private static final int INVITATION_YES = 0;
	private static final int INVITATION_NO = 1;
	
	public FightRoom(Player player1, Player player2)
	{
		this.player1 = player1;
		this.player2 = player2;
		isPlayer1Turn = player1.getPokemon().getSpeed() > player2.getPokemon().getSpeed();
	}
	
	public boolean isFightComplete()
	{
		return fightIsComplete;
	}
	
	public boolean hasPlayer(String name)
	{
		return player1.getUserName().equals(name) || player2.getUserName().equals(name);
	}
	
	public FightRoom(Player player)
	{
		this.player1 = player;
	}
	
	public Player getPlayer1()
	{
		return player1;
	}
	
	public Player getPlayer2()
	{
		return player2;
	}
	
	public OutputData getInvitation(Long chatId)
	{
		return new OutputData(chatId, INVITATION, "Пользователь " + player1.getUserName() + " отправил вам приглашение. Принять?", Arrays.asList("Да", "Нет")); 
	}
	
	public void addPlayer(Player player)
	{
		player2 = player;
		isPlayer1Turn = player1.getPokemon().getSpeed() > player2.getPokemon().getSpeed();
	}
	
	public List<OutputData> turn()
	{
		OutputData player1Turn = null;
		OutputData player2Turn = null;
		if (isPlayer1Turn)
		{
			player1Turn = new OutputData(player1.getChatId(), FIGHT, "Твой ход!", player1.getPokemon().getSkillsList());
			player2Turn = new OutputData(player2.getChatId(), "Ожидание хода противника...");
		}
		else
		{
			player2Turn = new OutputData(player2.getChatId(), FIGHT, "Твой ход!", player2.getPokemon().getSkillsList());
			player1Turn = new OutputData(player1.getChatId(), "Ожидание хода противника...");
		}
		return Arrays.asList(player1Turn, player2Turn);
	}
	
	public List<OutputData> turn(InputData input)
	{
		if (player1.getChatId().equals(input.getChatId()) && !isPlayer1Turn ||
				player2.getChatId().equals(input.getChatId()) && isPlayer1Turn)
		{
				return Arrays.asList(new OutputData(input.getChatId(), "Ожидание хода противника..."));
		}
		int skillNumber = Integer.parseInt(input.getData().substring(input.getData().indexOf('.') + 1));
		Player turningPlayer = player1.getChatId().equals(input.getChatId()) ? player1 : player2;
		Player another = player1.getChatId().equals(input.getChatId()) ? player2 : player1;
		int damage = turningPlayer.getPokemon().useSkill(skillNumber, another.getPokemon().getCharacteristics());
		another.getPokemon().hurt(damage);
		
		if (another.getPokemon().isDead())
		{
			fightIsComplete = true;
			OutputData msgTurningPlayer = new OutputData(turningPlayer.getChatId(), Arrays.asList("Твой покемон нанес урон (" + damage + ")", 
					"Поздравляем, твой покемон победил!"));
			OutputData msgAnother = new OutputData(another.getChatId(), Arrays.asList("Противник нанес тебе урон: (" + damage + ")", 
					"К сожалению, ты проиграл"));
			return Arrays.asList(msgTurningPlayer, msgAnother);
			
		}
		
		OutputData msgTurningPlayer = new OutputData(turningPlayer.getChatId(), Arrays.asList("Твой покемон нанес урон (" + damage + ")", 
										"Здоровье противника: (" + another.getPokemon().getHealth() +")"));
		OutputData msgAnother = new OutputData(another.getChatId(), Arrays.asList("Противник нанес тебе урон: (" + damage + ")", 
				"Текущее здоровье: (" + another.getPokemon().getHealth() +")"));
		List<OutputData> out = new ArrayList<OutputData>();
		out.addAll(Arrays.asList(msgTurningPlayer, msgAnother));
		isPlayer1Turn = !isPlayer1Turn;
		out.addAll(turn());
		return out;
	}
	
	
}