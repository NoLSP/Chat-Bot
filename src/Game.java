import java.io.File;
import java.util.ArrayList;
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
	
	public Pair<SendMessage, SendPhoto> step(Message input)
	{
		String text = input.getText();
		Long chatId = input.getChatId();
		switch (text)
		{
			case ("/start"):
				return new Pair<SendMessage, SendPhoto>(new SendMessage().setChatId(chatId).setText(greeting()), null);
			case ("/play"):
				if(isPlay)
				{
					data.reset();
					currentTask = data.getNextTask();
					currentQuestion = currentTask.getQuestion();
					userAnswers = new ArrayList<Integer>();
					return new Pair<SendMessage, SendPhoto>(getQuestionWithKeyboard(chatId, currentQuestion, currentTask.getAnswers()), null);
				}
				else
				{
					currentTask = data.getNextTask();
					currentQuestion = currentTask.getQuestion();
					isPlay = true;
					return new Pair<SendMessage, SendPhoto>(getQuestionWithKeyboard(chatId, currentQuestion, currentTask.getAnswers()), null);	
				}
			case ("/help"):
				return new Pair<SendMessage, SendPhoto>(new SendMessage().setChatId(chatId).setText(data.getInfo()), null);
		}
		if(!isPlay) return new Pair<SendMessage, SendPhoto>(new SendMessage().setChatId(chatId).setText(help()), null);
		else return new Pair<SendMessage, SendPhoto>(new SendMessage().setChatId(chatId).setText("Exception"), null);
	}
	
	public Pair<SendMessage, SendPhoto> step(CallbackQuery input)
	{
		int chatId = input.getMessage().getChatId().intValue();
		int userAnswer = Integer.parseInt(input.getData());
		userAnswers.add(userAnswer);
		System.out.println("User answer = " + userAnswer);
		if(data.hasTask())
		{
			currentTask = data.getNextTask();
			currentQuestion = currentTask.getQuestion();
			isPlay = true;
			return new Pair<SendMessage, SendPhoto>(getQuestionWithKeyboard(chatId, currentQuestion, currentTask.getAnswers()), null);
		}
		else
		{
			return getResult(chatId);
		}
	}
	
	private Pair<SendMessage, SendPhoto> getResult(long chatId) {
		Pair<String, File> result = data.getResult( userAnswers);
		return new Pair<SendMessage, SendPhoto>(new SendMessage().setChatId(chatId).setText(result.getItem1()),
												new SendPhoto().setChatId(chatId).setPhoto(result.getItem2()));
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