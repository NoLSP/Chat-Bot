import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum Status { None, Single, Multi}

public class Fighter {
	private Pokemon myPoke;
	private Data data;
	private Status status;
	private Fight fight;
	private boolean enemyChousen;
	
	//id-����� ��������
	private static final int CHOOSE_ENEMY = 200;
	private static final int INVITATION = 400;
	private static final int FIGHT_CLOSE = 410;
	private static final String CREATE_FIGHT = "/new";
	
	//id-����� �������
	private static final int CHOOSE_ENEMY_RANDOM = 0;
	private static final int CHOOSE_ENEMY_USER = 1;
	private static final int INVITATION_ACCEPT = 0;
	private static final int INVITATION_DECLINE = 1;
	private static final int FIGHT_CLOSE_YES = 0;
	private static final int FIGHT_CLOSE_NO = 1;
	
	//id �������� �������
	private static int CURRENT_QUESTION = 200;
	
	public Fighter(Pokemon myPoke, Data data)
	{
		this.myPoke = myPoke;
		this.data = data;
		enemyChousen = false;
		status = Status.None;
		fight = null;
	}
	

	public List<OutputData> next(InputData inputData)
	{
		Long chatId = inputData.getChatId();
		String input = inputData.getData();
		if (CREATE_FIGHT.equals(input) || (FIGHT_CLOSE + "."+FIGHT_CLOSE_YES).equals(input))
		{
			status = Status.None;
			fight = null;
			enemyChousen = false;
			return Arrays.asList(choseEnemy(chatId));
		}
		if ((FIGHT_CLOSE + "."+FIGHT_CLOSE_NO).equals(input))
		{
			status = Status.None;
			fight = null;
			enemyChousen = false;
			return Arrays.asList(new OutputData(inputData.getChatId(), data.getInfo()));
		}
		if ("/fight".equals(input))
		{
			if (status == Status.None || status == Status.Single)
				return Arrays.asList(choseEnemy(chatId));
			return Arena.closeFight(new Player(myPoke, inputData.getUserName(), chatId));
		}
		if ( (INVITATION + "." + INVITATION_ACCEPT).equals(input))
		{
			status = Status.Multi;
			enemyChousen = true;
			fight = new MultiFight(myPoke);
		}
		if ( (INVITATION + "." + INVITATION_DECLINE).equals(input))
		{
			return Arena.decline(new Player(myPoke, inputData.getUserName(), chatId));
		}
		
		if (status == Status.None && enemyChousen == false)
		{
			if (String.format("%1$s.%2$s",CHOOSE_ENEMY, CHOOSE_ENEMY_RANDOM).equals(input))
			{
				status = Status.Single;
				fight = new SingleFight(myPoke, data);
				return fight.next(new InputData(chatId, inputData.getUserName(), "/start"));
			}
			if (String.format("%1$s.%2$s",CHOOSE_ENEMY, CHOOSE_ENEMY_USER).equals(input))
			{
				status = Status.Multi;
				fight = new MultiFight(myPoke);
				return fight.next(new InputData(chatId, inputData.getUserName(), "/start"));
			}
			enemyChousen = true;
		}
		return fight.next(inputData);
	}
	
	public Status getStatus()
	{
		return status;
	}
	
	private OutputData choseEnemy(Long chatId) 
	{
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("��������� ���������");
		answers.add("������������");
		CURRENT_QUESTION = CHOOSE_ENEMY;
		return new OutputData(chatId, CHOOSE_ENEMY, Arrays.asList("� ��� ������� ���������?"), answers);
	}
}
