import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OutputData {
	private Long chatId;
	private List<String> message;
    private List<String> keyboard;
    private File image;
    private int id;

    public OutputData(Long chatId, int questionId, List<String> message, List<String> keyboard, File image) 
    {
    	this.message = message;
    	this.keyboard = keyboard;
    	this.image = image;
    	this.id = questionId;
    	this.chatId = chatId;
    }
    
    public OutputData(Long chatId, int questionId, List<String> message, List<String> keyboard) 
    {
    	this.message = message;
    	this.keyboard = keyboard;
    	this.id = questionId;
    	this.chatId = chatId;
    }
    
    public OutputData(Long chatId, String message, List<String> keyboard) 
    {
    	this.message = Arrays.asList(message);
    	this.keyboard = keyboard;
    	this.chatId = chatId;
    }
    
    public OutputData(Long chatId, List<String> message, File image) 
    {
    	this.message = message;
    	this.image = image;
    	this.chatId = chatId;
    }
    
    public OutputData(Long chatId, List<String> message) 
    {
    	this.message = message;
    	this.id = -1;
    	this.chatId = chatId;
    }
    
    public OutputData(Long chatId, String message) 
    {
    	this.message = Arrays.asList(message);
    	this.id = -1;
    	this.chatId = chatId;
    }
    
    public OutputData(Long chatId, int questionId, String message, List<String> keyboard) {
    	this.message = Arrays.asList(message);
    	this.keyboard = keyboard;
    	this.id = questionId;
    	this.chatId = chatId;
	}

	public Long getChatId()
    {
    	return chatId;
    }

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasKeyboard() {
        return keyboard != null;
    }
    
    public boolean hasImage()
    {
    	return image != null;
    }

    public List<String> getMessage() {
        return message;
    }
    
    public int getId()
    {
    	return id;
    }

    public List<String> getKeyboard() {
        return keyboard;
    }
    
    public File getImage()
    {
    	return image;
    }
}
