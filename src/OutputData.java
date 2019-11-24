import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OutputData {
	private List<String> message;
    private List<String> keyboard;
    private File image;
    private int questionNumber;

    public OutputData(int questionNumber, List<String> message, List<String> keyboard, File image) 
    {
    	this.message = message;
    	this.keyboard = keyboard;
    	this.image = image;
    	this.questionNumber = questionNumber;
    }
    
    public OutputData(int questionNumber, List<String> message, List<String> keyboard) 
    {
    	this.message = message;
    	this.keyboard = keyboard;
    	this.questionNumber = questionNumber;
    }
    
    public OutputData(List<String> message, File image) 
    {
    	this.message = message;
    	this.image = image;
    }
    
    public OutputData(List<String> message) 
    {
    	this.message = message;
    	this.questionNumber = -1;
    }
    
    public OutputData(String message) 
    {
    	this.message = Arrays.asList(message);
    	this.questionNumber = -1;
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
    
    public int getQuestionNumber()
    {
    	return questionNumber;
    }

    public List<String> getKeyboard() {
        return keyboard;
    }
    
    public File getImage()
    {
    	return image;
    }
}
