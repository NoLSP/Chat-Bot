import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
		if (e.hasMessage())
		{
			if(!games.containsKey(e.getMessage().getChatId().intValue()))
			{
				games.put(e.getMessage().getChatId().intValue(), new Game());
			}
			Message msg = e.getMessage();
			Game game = games.get(msg.getChatId().intValue());
			Pair output = game.step(msg);
			if (output.hasMessage())
			{
				try { 
					execute(output.getMessage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			else
			{
				try { 
					execute(output.getPhoto());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		if (e.hasCallbackQuery())
		{
			Game game = games.get(e.getCallbackQuery().getMessage().getChatId().intValue());
			Pair output = game.step(e.getCallbackQuery());
			if (output.hasBoth())
			{
				try { 
					execute(output.getMessage());
					execute(output.getPhoto());
				} catch (Exception e1) {
					e1.printStackTrace();
				}			
			}
			else
			{
				if (output.hasMessage())
				{
					try { 
						execute(output.getMessage());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				else
				{
					try { 
						execute(output.getPhoto());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public String getBotToken() {
		return "968482226:AAEyAg1bNq5uPRtenot5nEwffEwiyJKJLIY";
	}
	
	private void sendMsg(SendMessage msg) {
		try { //Чтобы не крашнулась программа при вылете Exception 
			execute(msg);
		} catch (TelegramApiException e){
			e.printStackTrace();
		}
	}
}