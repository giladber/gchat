package main.listeners;

import javax.ejb.Asynchronous;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import main.ChatMessageDao;
import common.ChatMessage;

@Asynchronous
public class MessageHandler
{
	@Inject private ChatMessageDao chatMessageDao;

	public void handleMessage(Message msg) throws JMSException
	{
		if (msg instanceof ObjectMessage)
		{
			ChatMessage chatMsg = (ChatMessage) ((ObjectMessage) msg).getObject();
			chatMessageDao.insertMessage(chatMsg);
		}
	}
}
