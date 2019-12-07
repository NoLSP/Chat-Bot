import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public final class ConverterToTelegramApi {
	
	public static ArrayList<Pair<SendMessage, SendPhoto>> convert( List<OutputData> input)
	{
		ArrayList<Pair<SendMessage, SendPhoto>> output = new ArrayList<Pair<SendMessage, SendPhoto>>();
		for (OutputData data : input)
		{
			SendMessage message = null;
			SendPhoto photo = null;
			Long chatId = data.getChatId();
			if ( data.hasMessage())
			{
				String text = "";
				for (int i = 0; i < data.getMessage().size(); i++)
				{
					if (i != 0) text = text + "\n";
					text = text + data.getMessage().get(i);
				}
				if (data.hasKeyboard())
				{
					message = getQuestionWithKeyboard(data.getId(), chatId, text, data.getKeyboard());
				}
				else
				{
					
					message = new SendMessage().setChatId(chatId).setText(text);
				}
			}
			if( data.hasImage())
				photo = new SendPhoto().setChatId(chatId).setPhoto(data.getImage());
			output.add(new Pair<SendMessage, SendPhoto>(message, photo));
		}
		
		return output;
	}
	
	private static SendMessage getQuestionWithKeyboard(int questionId, long chatId, String text, List<String> answers) 
	{
	     InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
	     List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
	     for (String ans : answers)
	     {
	    	 List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
	    	 InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
	    	 inlineKeyboardButton.setText(ans);
	    	 inlineKeyboardButton.setCallbackData(questionId + "." + Integer.toString(answers.indexOf(ans)));
	    	 keyboardButtonsRow.add(inlineKeyboardButton);
	    	 rowList.add(keyboardButtonsRow);
	     }
	     inlineKeyboardMarkup.setKeyboard(rowList);
	     return new SendMessage().setChatId(chatId).setText(text).setReplyMarkup(inlineKeyboardMarkup);
	}
}