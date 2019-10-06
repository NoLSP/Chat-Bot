import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class PokeBot extends TelegramLongPollingBot {
	@Override
	public String getBotUsername() {
		return "Poke_bot";
	}

	@Override
	public void onUpdateReceived(Update e) {
		Message msg = e.getMessage(); // Это нам понадобится
		try {
			sendMsg(msg, Game.step(msg.getText()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
	}

	@Override
	public String getBotToken() {
		return "968482226:AAEyAg1bNq5uPRtenot5nEwffEwiyJKJLIY";
	}
	
	@SuppressWarnings("deprecation")
	private void sendMsg(Message msg, String text) {
		SendMessage s = new SendMessage();
		s.setChatId(msg.getChatId()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять)
		s.setText(text);
		try { //Чтобы не крашнулась программа при вылете Exception 
			sendMessage(s);
		} catch (TelegramApiException e){
			e.printStackTrace();
		}
	}
}