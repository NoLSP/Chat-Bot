import java.util.Random;
import java.util.function.Function;

public class Skill {
	private String name;
	private String info;
	private int coolDown;
	private int movesBeforeRecovery;
	private Coefficients coefficients;  
	
	public Skill(String nm, String inf, int cd)
	{
		name = nm;
		info = inf;
		coolDown = cd;
		movesBeforeRecovery = 0;
	}
	
	public boolean isReady()
	{
		return movesBeforeRecovery == 0;
	}
	
	//наносит урон противнику и возвращает количество урона
	public int use(Characteristics myChrs, Characteristics enemyChrs)
	{
		double healthComponent = coefficients.forHealth() * myChrs.getHealth();
		double speedComponent = coefficients.forSpeed() * myChrs.getSpeed();
		double intellectComponent = coefficients.forIntellect() * myChrs.getIntellect();
		double strengthComponent = coefficients.forStrength() * myChrs.getStrength();
		double defenceComponent = coefficients.forDefence() * myChrs.getDefence();

		double damage = healthComponent + speedComponent + intellectComponent + strengthComponent + defenceComponent;
		double defenceEffect = 1 - enemyChrs.getDefence()/100;
		int speedEffect = 1;// to do
		
		enemyChrs.reduceHealth((int) (damage * defenceEffect * speedEffect));
		return (int) (damage * defenceEffect * speedEffect);
	}
	
	public void setCoefficients(String str)
	{
		String[] inputData = str.split("\\|");
		//Коэффициенты способности
		double myH = Double.parseDouble(inputData[0]);
		double myStr = Double.parseDouble(inputData[1]);
		double myD = Double.parseDouble(inputData[2]);
		double mySpd = Double.parseDouble(inputData[3]);
		double myI = Double.parseDouble(inputData[4]);
		coefficients = new Coefficients(myH, mySpd, myI, myStr, myD);
	}

	public String getName() {
		return name;
	}
}
