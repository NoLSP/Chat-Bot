import java.io.File;
import java.util.ArrayList;

public class OutputData {
	private String message;
    private ArrayList<String> keyboard;
    private File image;

    public OutputData(String message, ArrayList<String> keyboard, File image) 
    {
    	this.message = message;
    	this.keyboard = keyboard;
    	this.image = image;
    }
    
    public OutputData(String message, ArrayList<String> keyboard) 
    {
    	this.message = message;
    	this.keyboard = keyboard;
    }
    
    public OutputData(String message) 
    {
    	this.message = message;
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

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getKeyboard() {
        return keyboard;
    }
    
    public File getImage()
    {
    	return image;
    }
}
