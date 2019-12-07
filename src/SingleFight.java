import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleFight implements Fight{
	private Pokemon myPoke;
	private Pokemon enemy;
	private Data data;
	private boolean isMyTurn;
	private boolean enemyChousen;
	
	//id-����� ��������
	private static final int LIKE_ENEMY = 200;
	private static final int STAGE_FIGHT = 201;
	
	//id-����� �������
	private static final int LIKE_ENEMY_YES = 0;
	private static final int LIKE_ENEMY_NO = 1;
	
	//id �������� �������
	private static int CURRENT_QUESTION = 200;
	
	
	public SingleFight(Pokemon myPoke, Data data)
	{
		this.myPoke = myPoke;
		this.data = data;
		enemy = null;
		enemyChousen = false;
		enemyChousen = false;
	}
	
	public List<OutputData> next(InputData inputData) {
		Long chatId = inputData.getChatId();
		String input = inputData.getData();
		
		if(enemy != null && (myPoke.isDead() || enemy.isDead()))
		{
			if(myPoke.isDead())
				return Arrays.asList(new OutputData(chatId, "��������!"));
			else
				return Arrays.asList(new OutputData(chatId, "�� �������, �������!"));
		}
		
		if (!"/start".equals(input))
		{
			int userQuestionNumber = Integer.parseInt(input.substring(0, input.indexOf('.')));
			if(userQuestionNumber != CURRENT_QUESTION)
				return Arrays.asList(new OutputData(chatId, "������� �� ��������� ������!"));
		}
			
		if(!enemyChousen)
		{
			if( "/start".equals(input) || String.format("%1$s.%2$s",LIKE_ENEMY, LIKE_ENEMY_NO).equals(input))
			{				
				enemy = data.getRandomPokemon();
				ArrayList<String> answers = new ArrayList<String>();
				answers.add("��");
				answers.add("���");
				CURRENT_QUESTION = LIKE_ENEMY;
				return Arrays.asList(new OutputData(chatId, LIKE_ENEMY, Arrays.asList("���� ���������: " + enemy.getName(), "\n", enemy.getInfo(), "����������?"), answers, enemy.getPhoto()));
			}
			if(String.format("%1$s.%2$s", LIKE_ENEMY, LIKE_ENEMY_YES).equals(input))
			{
				enemyChousen = true;
				isMyTurn = myPoke.getSpeed() > enemy.getSpeed() ? true : false;
				if(isMyTurn)
					return Arrays.asList(userTurn(chatId, 0));
				else
					return Arrays.asList(enemyTurn(chatId));
			}
		}
		myPoke.refreshSkills();
		enemy.refreshSkills();
		if(String.format("%1$s", STAGE_FIGHT).equals(input.substring(0, input.indexOf('.'))))
		{
			if(isMyTurn)
				return Arrays.asList(userTurn(chatId, Integer.parseInt(input.substring(input.indexOf('.')+1))));
			return Arrays.asList(enemyTurn(chatId));
		}
		return null;	
	}
	
	private OutputData enemyTurn(Long chatId) 
	{
		int damage = enemy.useRandomSkill(myPoke.getCharacteristics());
		myPoke.hurt(damage);
		if (myPoke.isDead())
			return new OutputData(chatId, Arrays.asList("��������� ����� ����: (" + damage + ") ��.", "�� ��������!"));
		isMyTurn = true;
		CURRENT_QUESTION = STAGE_FIGHT;
		return new OutputData(chatId, STAGE_FIGHT, Arrays.asList("��������� ����� ����: (" + damage + ") ��.", "���� ���!"), myPoke.getSkillsList());
	}

	private OutputData userTurn(Long chatId, int skillIndex) {
		int damage = myPoke.useSkill(skillIndex, enemy.getCharacteristics());
		if (damage == -1) 
			return new OutputData(chatId, STAGE_FIGHT, Arrays.asList("��� ����������� ��� �� ��������������, ������ ������"), myPoke.getSkillsList());
		enemy.hurt(damage);
		if (enemy.isDead())
			return new OutputData(chatId, Arrays.asList("���� ������� ����� ����: (" + damage + ") ��.", "�� �������!"));
		isMyTurn = false;
		OutputData enemyTurn = enemyTurn(chatId);
		isMyTurn = true;
		List<String> text = Arrays.asList("���� ������� ����� ����: (" + damage + ") ��.", "��� ����������!");
		//text.addAll(enemyTurn.getMessage());
		CURRENT_QUESTION = STAGE_FIGHT;
		return new OutputData(chatId, STAGE_FIGHT, Arrays.asList(text.get(0), text.get(1), enemyTurn.getMessage().get(0), enemyTurn.getMessage().get(1)), myPoke.getSkillsList());
	}

}