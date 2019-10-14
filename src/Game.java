import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;



public class Game 
{
	private Data data;
	private boolean isPlay;
	private ArrayList<Integer> userAnswers;
	private String currentQuestion;
	private Task currentTask;
	//private enum commands {start, end, help};
	
	public Game()
	{
		data = new Data();
		isPlay = false;
		userAnswers = new ArrayList<Integer>();
		currentQuestion = "";
		currentTask = new Task();
	}
	
	public String greeting()
	{
		return "Привет, я супер чат-бот!\n" + data.getInfo();
	}
	
	public String help()
	{
		return data.getInfo();
	}
	
	public Pair step(Message input)
	{
		String text = input.getText();
		Long chatId = input.getChatId();
		switch (text)
		{
			case ("/start"):
				return new Pair(new SendMessage().setChatId(chatId).setText(greeting()));
			case ("/play"):
				if(isPlay) 
					return new Pair(getQuestionWithKeyboard(chatId, currentQuestion, currentTask.getAnswers()));
				else
				{
					currentTask = data.getNextTask();
					currentQuestion = currentTask.getQuestion();
					isPlay = true;
					return new Pair(getQuestionWithKeyboard(chatId, currentQuestion, currentTask.getAnswers()));	
				}
			case ("/help"):
				return new Pair(new SendMessage().setChatId(chatId).setText(data.getInfo()));
			case ("/reset"):
				data.reset();
				isPlay = false;
				userAnswers = new ArrayList<Integer>();
				return new Pair(new SendMessage().setChatId(chatId).setText("Игра перезапущена, жми /play"));	
		}
		if(!isPlay) return new Pair(new SendMessage().setChatId(chatId).setText(help()));
		else return new Pair(new SendMessage().setChatId(chatId).setText("Exception"));
	}
	
	public Pair step(CallbackQuery input)
	{
		int chatId = input.getMessage().getChatId().intValue();
		userAnswers.add(Integer.parseInt(input.getData()));
		if(data.hasTask())
		{
			currentTask = data.getNextTask();
			currentQuestion = currentTask.getQuestion();
			isPlay = true;
			return new Pair(getQuestionWithKeyboard(chatId, currentQuestion, currentTask.getAnswers()));
		}
		else
		{
			return getResult(chatId);
		}
	}
	
	private Pair getResult(long chatId) {
		try {
			return data.getResult(chatId, userAnswers);
		}
		catch(Exception e){
			System.out.println("Exception");
		}
		return null;
	}

	public SendMessage getQuestionWithKeyboard(long chatId, String text, ArrayList<String> answers) 
	{
	     InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
	     List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
	     for (String ans : answers)
	     {
	    	 List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
	    	 InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
	    	 inlineKeyboardButton.setText(ans);
	    	 inlineKeyboardButton.setCallbackData(Integer.toString(answers.indexOf(ans)));
	    	 keyboardButtonsRow.add(inlineKeyboardButton);
	    	 rowList.add(keyboardButtonsRow);
	     }
	     inlineKeyboardMarkup.setKeyboard(rowList);
	     return new SendMessage().setChatId(chatId).setText(text).setReplyMarkup(inlineKeyboardMarkup);
	}
	
	public Task getTask()
	{
		return currentTask;
	}
}