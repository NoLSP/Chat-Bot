import java.util.Arrays;
import java.util.List;

public class MultiFight implements Fight{
	private Pokemon pokemon;
	
	private static final int INVITATION = 400;
	private static final int FIGHT = 401;
	
	private static final int INVITATION_YES = 0;
	private static final int INVITATION_NO = 1;
	
	public MultiFight(Pokemon pokemon)
	{
		this.pokemon = pokemon;
	}
	
	public List<OutputData> next(InputData inputData) {
		String input = inputData.getData();
		Long chatId = inputData.getChatId();
		
		if ("/start".equals(input))
		{
			return Arrays.asList(new OutputData(chatId, "С кем конкретно? (Введи user name)"));
		}
		
		if (String.format("%1$s.%2$s", INVITATION, INVITATION_YES).equals(input))
		{
			Player player = new Player(pokemon, inputData.getUserName(), inputData.getChatId());
			return Arena.accept(player);
		}
		if ( input.indexOf('.') != -1 && ("" + FIGHT).equals(input.substring(0, input.indexOf('.'))))
		{
			return Arena.makeTurn(inputData);
		}
		
		Player fromPlayer = new Player(pokemon, inputData.getUserName(), inputData.getChatId());
		return Arena.CreateRoom(fromPlayer, inputData.getData());
		
	}

}
