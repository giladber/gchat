package gilad.chatclient.jmx;

import java.util.concurrent.atomic.AtomicLong;

public class Chat implements ChatMBean 
{
	AtomicLong consumedMessagesNum = new AtomicLong();
	AtomicLong sentMessagesNum = new AtomicLong();
	private final long startTimeMs = System.currentTimeMillis();
	
	@Override
	public long getNumberMessagesConsumed() 
	{
		return consumedMessagesNum.get();
	}

	@Override
	public long getNumberMessagesSent() 
	{
		return sentMessagesNum.get();
	}

	@Override
	public long getUptimeMs() 
	{
		return System.currentTimeMillis() - startTimeMs;
	}

	@Override
	public ChatMBeanUpdater createUpdater() 
	{
		return new ChatUpdater(this);
	}


}
