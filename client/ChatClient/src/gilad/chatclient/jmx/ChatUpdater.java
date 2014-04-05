package gilad.chatclient.jmx;


public class ChatUpdater implements ChatMBeanUpdater
{
	private final Chat chat;
	
	public ChatUpdater(Chat chat)
	{
		this.chat = chat;
	}
	
	public void messageReceived()
	{
		chat.consumedMessagesNum.incrementAndGet();
	}
	
	public void messageSent()
	{
		chat.sentMessagesNum.incrementAndGet();
	}
}
