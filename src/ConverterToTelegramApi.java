import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public final class ConverterToTelegramApi {
	
	public static Pair<SendMessage, SendPhoto> convert( Pair<Pair<String, ArrayList<String>>, File> input, long chatId)
	{
		SendMessage message = null;
		SendPhoto photo = null;
		if ( input.hasItem1())
		{
			if (input.getItem1().hasBoth())
			{
				message = getQuestionWithKeyboard(chatId, input.getItem1().getItem1(), input.getItem1().getItem2());
			}
			else
				message = new SendMessage().setChatId(chatId).setText(input.getItem1().getItem1());
		}
		if( input.hasItem2())
			photo = new SendPhoto().setChatId(chatId).setPhoto(input.getItem2());
		return new Pair<SendMessage, SendPhoto>( message, photo);
	}
	
	private static SendMessage getQuestionWithKeyboard(long chatId, String text, ArrayList<String> answers) 
	{
	     InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
	     List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
	     for (String ans : answers)
	     {
	    	 List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
	    	 InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
	    	 inlineKeyboardButton.setText(ans);
	    	 inlineKeyboardButton.setCallbackData(text.substring(0, 1) + Integer.toString(answers.indexOf(ans)));
	    	 keyboardButtonsRow.add(inlineKeyboardButton);
	    	 rowList.add(keyboardButtonsRow);
	     }
	     inlineKeyboardMarkup.setKeyboard(rowList);
	     return new SendMessage().setChatId(chatId).setText(text.substring(1)).setReplyMarkup(inlineKeyboardMarkup);
	}
}