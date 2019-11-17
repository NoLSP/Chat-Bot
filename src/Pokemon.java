import java.io.File;
import java.util.ArrayList;

public class Pokemon {
	private String name;
	private String info;
	private File photo;
	private ArrayList<Skill> skills;
	private Characteristics characteristics;
	
	public Pokemon(String nm, String inf)
	{
		name = nm;
		info = inf;
	}
	
	public void setPhoto(File ph)
	{
		photo = ph;
	}
	
	public void setSkills(ArrayList<Skill> skls)
	{
		skills = skls;
	}
	
	public void setCharasteristics(int[] chcs)
	{
		characteristics = new Characteristics(chcs);		
	}
	
	public File getPhoto()
	{
		return photo;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getInfo()
	{
		return info;
	}
	
	public int getHealth()
	{
		return characteristics.getHealth();
	}
	
	public int getSpeed()
	{
		return characteristics.getSpeed();
	}
	
	public int getIntellect()
	{
		return characteristics.getIntellect();
	}
	
	public int getStrength()
	{
		return characteristics.getStrength();
	}
	
	public int getDefence()
	{
		return characteristics.getDefence();
	}
	
	public ArrayList<Skill> getSkills()
	{
		return skills;
	}
	
	public ArrayList<String> getSkillsList()
	{
		ArrayList<String> list = new ArrayList<String>();
		for(Skill skill : skills)
			list.add(skill.getName());
		return list;
	}

	public void hurt(int damage) {
		characteristics.reduceHealth(damage);
	}

	public int useSkill(int skillIndex, Characteristics enemyChrs) {
		Skill skill = skills.get(skillIndex);
			if(skill.isReady())
				return skill.use(characteristics, enemyChrs);
		return 0;
	}
	
	public int useRandomSkill(Characteristics enemyChrs) {
		for( Skill skill : skills)
			if(skill.isReady())
				return skill.use(characteristics, enemyChrs);
		return 0;
	}

	public boolean isDead() {
		return characteristics.getHealth() == 0;
	}

	public Characteristics getCharacteristics() {
		return characteristics;
	}
}
