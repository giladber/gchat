package main.listeners;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(
	name = "ChatMessageListener",
	activationConfig = 
	{
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/chat"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
	}
)
public class ChatMDB implements MessageListener
{

	@Inject private MessageHandler msgHandler;
	@Resource private MessageDrivenContext ctx;
	
	@Override
	public void onMessage(Message msg) 
	{
		try
		{
			msgHandler.handleMessage(msg);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ctx.setRollbackOnly();
		}
	}

}
