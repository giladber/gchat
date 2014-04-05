package gilad.chatclient.jmx;


public interface ChatMBean
{
	public long getNumberMessagesConsumed();
	
	public long getNumberMessagesSent();
	
	public long getUptimeMs();
	
	public ChatMBeanUpdater createUpdater();
	
}
