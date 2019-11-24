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
		
		return (int) (damage * defenceEffect * speedEffect);
	}
	
	public void setCoefficients(String str)
	{
		coefficients = new Coefficients(str);
	}

	public String getName() {
		return name;
	}
}
