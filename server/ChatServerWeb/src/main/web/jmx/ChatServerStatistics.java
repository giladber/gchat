package main.web.jmx;

import java.util.concurrent.atomic.AtomicLong;

import javax.ejb.LocalBean;
import javax.inject.Singleton;

@LocalBean
@Singleton
public class ChatServerStatistics implements ChatServerStatisticsMBean 
{
	final AtomicLong queryNum = new AtomicLong();
	final AtomicLong persistedMessagesNum = new AtomicLong();
	
	@Override
	public long getExecutedQueryNum() 
	{
		return queryNum.get();
	}

	@Override
	public long getInsertedMessagesNum() 
	{
		return persistedMessagesNum.get();
	}
	
	public static class QueryExecutedEvent
	{

	}
}
