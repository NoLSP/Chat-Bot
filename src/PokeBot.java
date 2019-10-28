import java.util.HashMap;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class PokeBot extends TelegramLongPollingBot {
	private HashMap<Integer, Game> games = new HashMap<Integer, Game>();
	
	@Override
	public String getBotUsername() {
		return "Poke_bot";
	}

	@Override
	public void onUpdateReceived(Update e) {
		synchronized(games) {
			if (e.hasMessage())
			{
				if(!games.containsKey(e.getMessage().getChatId().intValue()))
				{
					games.put(e.getMessage().getChatId().intValue(), new Game());
				}
				Message msg = e.getMessage();
				System.out.println("Message has got. From: " + msg.getFrom().getUserName() + " Message = " + msg.getText());
				Long chatId = msg.getChatId();
				Game game = games.get(chatId.intValue());
				Pair<SendMessage, SendPhoto> output = ConverterToTelegramApi.convert(game.step(msg.getText()), chatId);
				
				sendMsg(output);
			}
			if (e.hasCallbackQuery())
			{
				Long chatId = e.getCallbackQuery().getMessage().getChatId();
				Game game = games.get(chatId.intValue());
				Pair<SendMessage, SendPhoto> output = ConverterToTelegramApi.convert(game.step(e.getCallbackQuery().getData()), chatId);
				sendMsg(output);
			}
		}
	}

	public String getBotToken() {
		return Reader.ReadToken("Token.txt");
	}
	
	private void sendMsg( Pair<SendMessage, SendPhoto> msg) {
		if (msg.hasItem1())
		{
			try { 
				execute(msg.getItem1());
			} catch (TelegramApiException e1) {
				e1.printStackTrace();
			}
		}
		if (msg.hasItem2())
		{
			try { 
				execute(msg.getItem2());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}