import java.util.HashMap;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class PokeBot extends TelegramLongPollingBot {
	private HashMap<String, Game> games = new HashMap<String, Game>();
	
	@Override
	public String getBotUsername() {
		return "Poke_bot";
	}

	@Override
	public void onUpdateReceived(Update e) {
		synchronized(games) {
			if (e.hasMessage())
			{
				System.out.println(games.toString());
				if(!games.containsKey(e.getMessage().getFrom().getUserName()))
				{
					String userName = e.getMessage().getFrom().getUserName();
					games.put(userName, new Game());
					Arena.addPlayerData(userName, e.getMessage().getChatId());
				}
				Message msg = e.getMessage();
				System.out.println("Message has got. From: " + msg.getFrom().getUserName() + " Message = " + msg.getText());
				Long chatId = msg.getChatId();
				String fromUser = msg.getFrom().getUserName();
				Game game = games.get(msg.getFrom().getUserName());
				List<Pair<SendMessage, SendPhoto>> output = ConverterToTelegramApi.convert(game.step(new InputData(chatId, fromUser, msg.getText())));
				
				sendMsg(output);
			}
			if (e.hasCallbackQuery())
			{
				Long chatId = e.getCallbackQuery().getMessage().getChatId();
				String fromUser = e.getCallbackQuery().getFrom().getUserName();
				Game game = games.get(e.getCallbackQuery().getFrom().getUserName());
				List<Pair<SendMessage, SendPhoto>> output = ConverterToTelegramApi.convert(game.step(new InputData(chatId, fromUser, e.getCallbackQuery().getData())));
				sendMsg(output);
			}
		}
	}

	public String getBotToken() {
		return Reader.ReadToken("Token.txt");
	}
	
	private void sendMsg( List<Pair<SendMessage, SendPhoto>> msg) {
		for (Pair<SendMessage, SendPhoto> out : msg)
		{
			if (out.hasItem1())
			{
				try { 
					execute(out.getItem1());
				} catch (TelegramApiException e1) {
					e1.printStackTrace();
				}
			}
			if (out.hasItem2())
			{
				try { 
					execute(out.getItem2());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}