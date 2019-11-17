import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Fight {
	private Pokemon myPoke;
	private Pokemon enemy;
	private Data data;
	private boolean isMyTurn;
	private boolean fightPhaseComplited;
	private boolean enemyChousen;
	
	public Fight(Pokemon me, Data dt)
	{
		myPoke = me;
		enemy = null;
		data = dt;
		fightPhaseComplited = false;
		enemyChousen = false;
	}
	
	public boolean isComplited()
	{
		return fightPhaseComplited;
	}
	
	public OutputData next(String input)
	{
		if(enemy != null && (myPoke.isDead() || enemy.isDead()))
		{
			fightPhaseComplited = true;
			if(myPoke.isDead())
				return new OutputData("Проиграл!");
			else
				return new OutputData("Ты победил, красава!");
		}
		
		if ("/fight".equals(input))
		{
			fightPhaseComplited = false;
			enemyChousen = false;
			return choseEnemy();
		}
		if(!enemyChousen)
		{
			//код ответа имеет следующую структуру: первая цифра - номер вопроса; 
			//вторая - номер варианта ответа, который выбрал юзер
			//нужно это для отслеживания, на нужный впрос ли ответил юзер
			if("00".equals(input))
			{				
				enemy = data.getRandomPokemon();
				ArrayList<String> answers = new ArrayList<String>();
				answers.add("Да");
				answers.add("Нет");
				return new OutputData("1Твой противник: " + enemy.getName() + "\n\n" + enemy.getInfo() + "\n\nУстраивает?", answers, enemy.getPhoto());
			}
			if("01".equals(input))
				return new OutputData("Этот модуль еще в разработке");
			if("10".equals(input))
			{
				enemyChousen = true;
				isMyTurn = myPoke.getSpeed() > enemy.getSpeed() ? true : false;
				if(isMyTurn)
					return userTurn(0);
				else
					return enemyTurn();
			}
			if ("11".equals(input))
			{
				enemy = data.getRandomPokemon();
				ArrayList<String> answers = new ArrayList<String>();
				answers.add("Да");
				answers.add("Нет");
				return  new OutputData("1Твой противник: " + enemy.getName() + "\n\n" + enemy.getInfo() + "\n\nУстраивает?", answers, enemy.getPhoto());
			}
		}
		if("5".equals(input.substring(0, 1)))
		{
			if(isMyTurn)
				return userTurn(Integer.parseInt(input.substring(1)));
			return enemyTurn();
		}
		return null;
		
	}
	
	private OutputData enemyTurn() 
	{
		int damage = enemy.useRandomSkill(myPoke.getCharacteristics());
		isMyTurn = true;
		return new OutputData("5Противник нанес урон: (" + damage + ") ед.\nТвой ход!", myPoke.getSkillsList());
	}

	private OutputData userTurn(int skillIndex) {
		int damage = myPoke.useSkill(skillIndex, enemy.getCharacteristics());
		isMyTurn = false;
		OutputData enemyTurn = enemyTurn();
		isMyTurn = true;
		return new OutputData("5Твой покемон нанес урон: (" + damage + ") ед.\nХод противника!\n\n" + enemyTurn.getMessage(), myPoke.getSkillsList());
	}

	private OutputData choseEnemy() 
	{
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("Случайный противник");
		answers.add("Пользователь");
		return new OutputData("0С кем желаешь сразиться?", answers);
	}
}
