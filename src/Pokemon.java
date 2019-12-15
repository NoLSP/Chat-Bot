import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Pokemon {
	private String name;
	private String info;
	private File photo;
	private List<Skill> skills;
	private Characteristics characteristics;
	
	public Pokemon(String name, String info)
	{
		this.name = name;
		this.info = info;
	}
	
	public void setPhoto(File ph)
	{
		photo = ph;
	}
	
	public void setSkills(ArrayList<Skill> skills)
	{
		this.skills = skills;
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
	
	public List<Skill> getSkills()
	{
		return skills;
	}
	
	public List<String> getSkillsList()
	{
		ArrayList<String> list = new ArrayList<String>();
		for(Skill skill : skills)
			list.add(skill.getName() + (skill.getMovesBeforeRecovery() > 0 ? "(" + (skill.getMovesBeforeRecovery()) + ")" : ""));
		return list;
	}

	public void hurt(int damage) {
		characteristics.reduceHealth(damage);
	}

	public int useSkill(int skillIndex, Characteristics enemyChrs) {
		
		Skill skill = skills.get(skillIndex);
			if(skill.isReady())
			{
				refreshSkills();
				return skill.use(characteristics, enemyChrs);
			}
		return -1;
	}
	
	public int useRandomSkill(Characteristics enemyChrs) {
		for( Skill skill : skills)
			if(skill.isReady())
				return skill.use(characteristics, enemyChrs);
		return 0;
	}
	
	public void refreshSkills()
	{
		for( Skill skill : skills)
			skill.refresh();
	}

	public boolean isDead() {
		return characteristics.getHealth() <= 0;
	}

	public Characteristics getCharacteristics() {
		return characteristics;
	}

	public void refresh() {
		characteristics.reset();
		for (Skill skill : skills)
			skill.reset();
	}
}
