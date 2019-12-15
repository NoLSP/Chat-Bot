import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FightRoom {
	private Player player1;
	private Player player2;
	private Mode mode = Mode.None; //если мод = single : бой в одиночном режиме, и наоборот
	private boolean isPlayer1Turn;
	private boolean isWaitingEnemyName = false;
	private boolean isComplited = false; 
	
	private static final int CHOOSE_TYPE_ENEMY = 400;
	private static final int CHOOSE_REAL_ENEMY = 401;
	private static final int SINGLE_FIGHT_STAGE = 402;
	private static final int INVITATION = 403;
	private static final int MULTI_FIGHT_STAGE = 404;
	
	private static int CURRENT_QUESTION = 0;
	
	private static final int CHOOSE_TYPE_ENEMY_COMP = 0;
	private static final int CHOOSE_TYPE_ENEMY_USER = 1;
	private static final int INVITATION_YES = 0;
	private static final int INVITATION_NO = 1;
	
	
	public FightRoom(Player player1)
	{
		this.player1 = player1;
		Arena.addRoom(this);
	}
	
	public boolean isComplited()
	{
		return isComplited;
	}
	
	public boolean hasPlayer(String name)
	{
		return player1.getUserName().equals(name) || player2.getUserName().equals(name);
	}
	
	public Player getPlayer1()
	{
		return player1;
	}
	
	public Player getPlayer2()
	{
		return player2;
	}
	
	public List<OutputData> invitePlayer(String name)
	{
		Long chatId = Arena.getChatId(name);
		if (chatId == -1)
			return Arrays.asList(new OutputData(player1.getChatId(), "Этого пользователя еще нет в игре, введи другое имя"));
		player2 = new Player(null, name, null);//пока первый игрок ожидает второго, комната знает только имя второго игрока
		return Arrays.asList(new OutputData(player1.getChatId(), "Приглашение отправленно, ожидание ответа..."),
				 new OutputData(chatId, INVITATION, "Пользователь " + player1.getUserName() + " отправил вам приглашение. Принять?", 
							  Arrays.asList("Да", "Нет"))); 
	}
	
	public void addPlayer(Player player)
	{
		player2 = player;
		isPlayer1Turn = player1.getPokemon().getSpeed() >= player2.getPokemon().getSpeed();
	}
	
	public List<OutputData> next(InputData inputData) {
		if ("/fight".equals(inputData.getData()))
		{
			return Arrays.asList(new OutputData(inputData.getChatId(), CHOOSE_TYPE_ENEMY, "С кем хочешь сразиться?", 
												Arrays.asList("Компьютер", "Пользователь")));
		}
		if ((INVITATION + "." + INVITATION_YES).equals(inputData.getData()))
		{
			OutputData out1 = new OutputData(player1.getChatId(), "Пользователь принял приглашение.");
			List<OutputData> out = new ArrayList<OutputData>();
			out.add(out1);
			out.addAll(multiFightTurnInfo());
			isWaitingEnemyName = false;
			return out;
		}
		if (isWaitingEnemyName)
		{
			return invitePlayer(inputData.getData());
		}
		if (("" + CHOOSE_TYPE_ENEMY + "." + CHOOSE_TYPE_ENEMY_COMP).equals(inputData.getData()))
		{
			setRandomComputerPlayer();
			OutputData out1 = new OutputData(inputData.getChatId(), "Твой противник - " + player2.getUserName() + "!", 
										     player2.getPokemon().getPhoto());
			OutputData out2 = makeSingleTurn();
			return Arrays.asList(out1, out2);
		}
		if ((CHOOSE_TYPE_ENEMY + "." + CHOOSE_TYPE_ENEMY_USER).equals(inputData.getData()))
		{
			isWaitingEnemyName = true;
			return Arrays.asList(new OutputData(inputData.getChatId(), CHOOSE_REAL_ENEMY, 
												Arrays.asList("С кем конкретно?", "Введи имя игрока(userName)")));	
		}
		if (("" + SINGLE_FIGHT_STAGE).equals(inputData.getData().substring(0, inputData.getData().indexOf('.'))))
		{
			//to do обрабатывать конец боя(мерть игрока)
			int skillIndex = Integer.parseInt(inputData.getData().substring(inputData.getData().indexOf('.')+1));
			return Arrays.asList(makeSingleTurn(skillIndex));
		}
		
		if ( inputData.getData().indexOf('.') != -1 && 
			("" + MULTI_FIGHT_STAGE).equals(inputData.getData().substring(0, inputData.getData().indexOf('.'))))
		{
			return multiFightTurn(inputData);
		}
		
		
		return Arrays.asList(new OutputData(inputData.getChatId(), "Не знаю о чем ты, воспользуйся /help"));
	}
	
	private OutputData makeSingleTurn() 
	{
		if (isPlayer1Turn)
			return new OutputData(player1.getChatId(), SINGLE_FIGHT_STAGE, "Твой ход!", player1.getPokemon().getSkillsList());
		else
			return singleFightEnemyTurn();
	}
	
	private OutputData makeSingleTurn(int skillIndex) 
	{
		if (isPlayer1Turn)
			return singleFightUserTurn(skillIndex);
		else
			return singleFightEnemyTurn();
	}

	private void setRandomComputerPlayer() 
	{
		Pokemon poke = Data.getRandomPokemon();
		player2 = new Player(poke, poke.getName(), (long)-1);
		isPlayer1Turn = player1.getPokemon().getSpeed() >= player2.getPokemon().getSpeed();		
	}

	private OutputData singleFightEnemyTurn() 
	{
		int damage = player2.getPokemon().useRandomSkill(player1.getPokemon().getCharacteristics());
		player1.getPokemon().hurt(damage);
		if (player1.getPokemon().isDead())
		{
			isComplited = true;
			return new OutputData(player1.getChatId(), Arrays.asList("Противник нанес урон: (" + damage + ") ед.", "Ты проиграл!"));
		}
		isPlayer1Turn = true;
		CURRENT_QUESTION = SINGLE_FIGHT_STAGE;
		return new OutputData(player1.getChatId(), SINGLE_FIGHT_STAGE, 
							  Arrays.asList("Противник нанес урон: (" + damage + ") ед.", "Твой ход!"),
							  player1.getPokemon().getSkillsList());
	}

	private OutputData singleFightUserTurn(int skillIndex) 
	{
		int damage = player1.getPokemon().useSkill(skillIndex, player2.getPokemon().getCharacteristics());
		if (damage == -1) 
			return new OutputData(player1.getChatId(), SINGLE_FIGHT_STAGE, 
								  Arrays.asList("Эта способность еще не восстановилась, выбери другую"), 
								  player1.getPokemon().getSkillsList());
		player2.getPokemon().hurt(damage);
		if (player2.getPokemon().isDead())
		{
			isComplited = true;
			return new OutputData(player1.getChatId(), Arrays.asList("Твой покемон нанес урон: (" + damage + ") ед.", "Ты выиграл!"));
		}
		isPlayer1Turn = false;
		List<String> text = Arrays.asList("Твой покемон нанес урон: (" + damage + ") ед.", "Ход противника!");
		OutputData enemyTurn = singleFightEnemyTurn();
		//text.addAll(enemyTurn.getMessage());
		CURRENT_QUESTION = SINGLE_FIGHT_STAGE;
		return new OutputData(player1.getChatId(), SINGLE_FIGHT_STAGE, 
							  Arrays.asList(text.get(0), text.get(1), enemyTurn.getMessage().get(0), enemyTurn.getMessage().get(1)), 
							  player1.getPokemon().getSkillsList());
	}
	
	public List<OutputData> multiFightTurnInfo()
	{
		OutputData player1Turn = null;
		OutputData player2Turn = null;
		Player firstPlayer = isPlayer1Turn ? player1 : player2;
		Player secondPlayer = isPlayer1Turn ? player2 : player1;
		player1Turn = new OutputData(firstPlayer.getChatId(), MULTI_FIGHT_STAGE, "Твой ход!", firstPlayer.getPokemon().getSkillsList());
		player2Turn = new OutputData(secondPlayer.getChatId(), "Ожидание хода противника...");
		return Arrays.asList(player1Turn, player2Turn);
	}
	
	public List<OutputData> multiFightTurn(InputData input)
	{
		if (!isCorrectPlayerTurn(input.getChatId()))
				return Arrays.asList(new OutputData(input.getChatId(), "Ожидание хода противника..."));
		int skillNumber = Integer.parseInt(input.getData().substring(input.getData().indexOf('.') + 1));
		Player turningPlayer = player1.getChatId().equals(input.getChatId()) ? player1 : player2;
		Player another = player1.getChatId().equals(input.getChatId()) ? player2 : player1;
		int damage = turningPlayer.getPokemon().useSkill(skillNumber, another.getPokemon().getCharacteristics());
		if (damage == -1)
		{
			return Arrays.asList(new OutputData(turningPlayer.getChatId(), MULTI_FIGHT_STAGE, "Эта способность еще не восстановилась, используй другую:", 
														turningPlayer.getPokemon().getSkillsList()));
		}
		another.getPokemon().hurt(damage);
		if (another.getPokemon().isDead())
		{
			OutputData msgTurningPlayer = new OutputData(turningPlayer.getChatId(), Arrays.asList("Твой покемон нанес урон (" + damage + ")", 
					"Поздравляем, твой покемон победил!"));
			OutputData msgAnother = new OutputData(another.getChatId(), Arrays.asList("Противник нанес тебе урон: (" + damage + ")", 
					"К сожалению, ты проиграл."));
			isComplited = true;
			return Arrays.asList(msgTurningPlayer, msgAnother);	
		}
		OutputData msgTurningPlayer = new OutputData(turningPlayer.getChatId(), Arrays.asList("Твой покемон нанес урон (" + damage + ")" + (damage == 0 ? " - Промах!" : ""),
										"Здоровье противника: (" + another.getPokemon().getHealth() +")"));
		OutputData msgAnother = new OutputData(another.getChatId(), Arrays.asList("Противник нанес тебе урон: (" + damage + ")", 
				"Текущее здоровье: (" + another.getPokemon().getHealth() +")"));
		List<OutputData> out = new ArrayList<OutputData>();
		out.add(msgTurningPlayer);
		out.add(msgAnother);
		isPlayer1Turn = !isPlayer1Turn;
		out.addAll(multiFightTurnInfo());
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

	public void complete() {
		isComplited = true;	
	}
}