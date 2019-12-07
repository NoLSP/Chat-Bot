
public class InputData {
	private Long chatId;
	private String userName;
	private String data;
	
	public InputData(Long chatId, String userName, String data)
	{
		this.chatId = chatId;
		this.userName = userName;
		this.data = data;
	}
	
	public Long getChatId()
	{
		return chatId;
	}
	
	public String getData()
	{
		return data;
	}
	
	public String getUserName()
	{
		return userName;
	}
}