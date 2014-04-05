package gilad.chatclient;

import java.util.HashSet;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import common.ChatMessage;

public class ChatListener implements MessageListener 
{
	
	private final Set<ChatController> controllers = new HashSet<>();
	
	@Override
	public void onMessage(Message msg)
	{
		if (msg instanceof ObjectMessage)
		{
			try 
			{
				ChatMessage chatMsg = (ChatMessage) ((ObjectMessage) msg).getObject();
				notify(chatMsg);
			} 
			catch (JMSException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void notify(ChatMessage msg)
	{
		for (ChatController ctrl : controllers)
		{
			ctrl.messageReceived(msg);
		}
	}
	
	public void register(ChatController controller)
	{
		controllers.add(controller);
	}

}
