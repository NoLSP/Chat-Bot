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
		System.out.println(Thread.currentThread());
		if (e.hasMessage())
		{
			if(!games.containsKey(e.getMessage().getChatId().intValue()))
			{
				games.put(e.getMessage().getChatId().intValue(), new Game());
			}
			Message msg = e.getMessage();
			Long chatId = msg.getChatId();
			Game game = games.get(chatId.intValue());
			Pair<SendMessage, SendPhoto> output = game.step(msg);
			
			sendMsg(chatId, output);
		}
		if (e.hasCallbackQuery())
		{
			Long chatId = e.getCallbackQuery().getMessage().getChatId();
			Game game = games.get(chatId.intValue());
			Pair<SendMessage, SendPhoto> output = game.step(e.getCallbackQuery());
			sendMsg(chatId, output);
		}
	}

	public String getBotToken() {
		return "968482226:AAEyAg1bNq5uPRtenot5nEwffEwiyJKJLIY";
	}
	
	private void sendMsg( Long chatId, Pair<SendMessage, SendPhoto> msg) {
		if (msg.hasItem1())
		{
			try { 
				execute(msg.getItem1());
			} catch (TelegramApiException e1) {
				System.out.println("Exception in execute message");
				e1.printStackTrace();
			}
		}
		if (msg.hasItem2())
		{
			try { 
				execute(msg.getItem2());
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("Eception in execute photo");
			}
		}
	}
}