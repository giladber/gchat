package main.web.jmx;

public interface ChatServerStatisticsMBean 
{
	public long getExecutedQueryNum();
	
	public long getInsertedMessagesNum();
}
