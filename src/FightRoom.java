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
		return new OutputData(chatId, INVITATION, "������������ " + player1.getUserName() + " �������� ��� �����������. �������?", Arrays.asList("��", "���")); 
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
		Player firstPlayer = isPlayer1Turn ? player1 : player2;
		Player secondPlayer = isPlayer1Turn ? player2 : player1;
		player1Turn = new OutputData(firstPlayer.getChatId(), FIGHT, "���� ���!", firstPlayer.getPokemon().getSkillsList());
		player2Turn = new OutputData(secondPlayer.getChatId(), "�������� ���� ����������...");
		return Arrays.asList(player1Turn, player2Turn);
	}
	
	public List<OutputData> turn(InputData input)
	{
		if (!isCorrectPlayerTurn(input.getChatId()))
				return Arrays.asList(new OutputData(input.getChatId(), "�������� ���� ����������..."));
		int skillNumber = Integer.parseInt(input.getData().substring(input.getData().indexOf('.') + 1));
		Player turningPlayer = player1.getChatId().equals(input.getChatId()) ? player1 : player2;
		Player another = player1.getChatId().equals(input.getChatId()) ? player2 : player1;
		int damage = turningPlayer.getPokemon().useSkill(skillNumber, another.getPokemon().getCharacteristics());
		if (damage == -1)
		{
			return Arrays.asList(new OutputData(turningPlayer.getChatId(), "��� ����������� ��� �� ��������������, ��������� ������:", 
														turningPlayer.getPokemon().getSkillsList()));
		}
		another.getPokemon().hurt(damage);
		if (another.getPokemon().isDead())
		{
			fightIsComplete = true;
			OutputData msgTurningPlayer = new OutputData(turningPlayer.getChatId(), Arrays.asList("���� ������� ����� ���� (" + damage + ")", 
					"�����������, ���� ������� �������!"));
			OutputData msgAnother = new OutputData(another.getChatId(), Arrays.asList("��������� ����� ���� ����: (" + damage + ")", 
					"� ���������, �� ��������."));
			return Arrays.asList(msgTurningPlayer, msgAnother);	
		}
		OutputData msgTurningPlayer = new OutputData(turningPlayer.getChatId(), Arrays.asList("���� ������� ����� ���� (" + damage + ")" + (damage == 0 ? " - ������!" : ""),
										"�������� ����������: (" + another.getPokemon().getHealth() +")"));
		OutputData msgAnother = new OutputData(another.getChatId(), Arrays.asList("��������� ����� ���� ����: (" + damage + ")", 
				"������� ��������: (" + another.getPokemon().getHealth() +")"));
		List<OutputData> out = new ArrayList<OutputData>();
		out.add(msgTurningPlayer);
		out.add(msgAnother);
		isPlayer1Turn = !isPlayer1Turn;
		out.addAll(turn());
		return out;
	}
	
	private boolean isCorrectPlayerTurn(Long chatId)
	{
		return (player1.getChatId().equals(chatId) && isPlayer1Turn) || (player2.getChatId().equals(chatId) && !isPlayer1Turn);
	}

	public Player getAnotherPayer(Player player) {
		return player1.getUserName().equals(player.getUserName()) ? player2 : player1;
	}

	public void refreshPlayers() {
		player1.refresh();
		player2.refresh();
	}
	
	
}