import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public final class ConverterToTelegramApi {
	
	public static Pair<SendMessage, SendPhoto> convert( OutputData input, long chatId)
	{
		SendMessage message = null;
		SendPhoto photo = null;
		if ( input.hasMessage())
		{
			String text = "";
			for (int i = 0; i < input.getMessage().size(); i++)
			{
				if (i != 0) text = text + "\n";
				text = text + input.getMessage().get(i);
			}
			if (input.hasKeyboard())
			{
				message = getQuestionWithKeyboard(input.getQuestionNumber(), chatId, text, input.getKeyboard());
			}
			else
			{
				
				message = new SendMessage().setChatId(chatId).setText(text);
			}
		}
		if( input.hasImage())
			photo = new SendPhoto().setChatId(chatId).setPhoto(input.getImage());
		return new Pair<SendMessage, SendPhoto>( message, photo);
	}
	
	private static SendMessage getQuestionWithKeyboard(int questionNumber, long chatId, String text, List<String> answers) 
	{
	     InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
	     List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
	     for (String ans : answers)
	     {
	    	 List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
	    	 InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
	    	 inlineKeyboardButton.setText(ans);
	    	 inlineKeyboardButton.setCallbackData(questionNumber + Integer.toString(answers.indexOf(ans)));
	    	 keyboardButtonsRow.add(inlineKeyboardButton);
	    	 rowList.add(keyboardButtonsRow);
	     }
	     inlineKeyboardMarkup.setKeyboard(rowList);
	     return new SendMessage().setChatId(chatId).setText(text).setReplyMarkup(inlineKeyboardMarkup);
	}
}