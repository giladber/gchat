package gilad.chatclient;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;

import common.ChatMessage;

public class ChatMessageSender implements AutoCloseable
{
	private final ChatJMSContext ctx;
	private final MessageProducer producer;
	
	public ChatMessageSender(ChatJMSContext ctx) throws JMSException
	{
		this.ctx = ctx;
		this.producer = ctx.session().createProducer(ctx.dest());
	}
	
	public void send(ChatMessage msg) throws JMSException
	{
		ObjectMessage objMsg = ctx.session().createObjectMessage();
		objMsg.setObject(msg);
		producer.send(objMsg);
	}

	@Override
	public void close() throws Exception 
	{
		if (producer != null)
		{
			producer.close();
		}
	}
	
	
}
