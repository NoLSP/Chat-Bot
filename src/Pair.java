import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class Pair{
    private SendMessage message;
    private SendPhoto photo;

    public Pair(SendMessage msg, SendPhoto pht) 
    {
    	message = msg;
    	photo = pht;
    }
    
    public Pair(SendMessage msg) 
    {
    	message = msg;
    	photo = null;
    }
    
    public Pair(SendPhoto pht) 
    {
    	photo = pht;
    	message = null;
    }

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasPhoto() {
        return photo != null;
    }
    
    public boolean hasBoth()
    {
    	return photo != null && message != null;
    }

    public SendMessage getMessage() {
        return message;
    }

    public SendPhoto getPhoto() {
        return photo;
    }
}