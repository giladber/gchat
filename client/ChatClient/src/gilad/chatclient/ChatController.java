package gilad.chatclient;

import gilad.chatclient.jmx.Chat;
import gilad.chatclient.jmx.ChatMBean;
import gilad.chatclient.jmx.ChatMBeanUpdater;

import java.util.Date;

import javax.jms.JMSException;
import javax.naming.NamingException;

import common.ChatMessage;

public class ChatController
{
	private final ChatJMSContext jmsContext; 
	private final ChatWindow window;
	private final String origin;
	private final ChatMessageSender msgSender;
	private final ChatMBeanUpdater jmxUpdater;
	private final ChatMBean chatBean;
	
	public ChatController(String user)
	{
		this.window = new ChatWindow(this);
		ChatJMSContext context = null;
		ChatMessageSender sender = null;
		
		try
		{
			context = new ChatJMSContext();
			sender = new ChatMessageSender(context);
		}
		catch (JMSException | NamingException e)
		{
			e.printStackTrace();
		}
		
		this.jmsContext = context;
		this.origin = user;
		this.msgSender = sender;
		this.window.setVisible(true);
		
		this.chatBean = new Chat();
		this.jmxUpdater = chatBean.createUpdater();
	}
	

	public ChatMBean start() throws JMSException
	{
		ChatListener listener = new ChatListener();
		this.jmsContext.attachListener(listener);
		listener.register(this);
	
		return this.chatBean;
	}

	public void messageReceived(ChatMessage chatMessage)
	{
		window.newMessage(chatMessage);
		jmxUpdater.messageReceived();
	}
	
	public void sendMessage(String msg)
	{
		ChatMessage chatMsg = new ChatMessage(msg, origin, new Date());
		try
		{
			this.msgSender.send(chatMsg);
			jmxUpdater.messageSent();
		}
		catch (JMSException ex)
		{
			ex.printStackTrace();
		}
	}
	
}
