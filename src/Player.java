
public class Player {
	private Pokemon pokemon;
	private String userName;
	private Long chatId;
	
	public Player(Pokemon poke, String userName, Long chatId)
	{
		pokemon = poke;
		this.userName = userName;
		this.chatId = chatId;
	}
	
	public String getUserName()
	{
		return userName;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}
	
	public Long getChatId()
	{
		return chatId;
	}

	public void refresh() {
		pokemon.refresh();
	}
}
