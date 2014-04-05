package main.web.jmx;

import java.lang.management.ManagementFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import main.web.jmx.ChatServerStatistics.QueryExecutedEvent;

@Singleton
public class ChatServerStatisticsUpdater 
{
	private static final String MBEAN_NAME = ChatServerStatistics.class.getPackage().toString() +
			":type=" + ChatServerStatistics.class.getSimpleName();
	
	@Inject private ChatServerStatistics css;
	
	@PostConstruct
	public void init()
	{
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		try
		{
			ObjectName name = new ObjectName(MBEAN_NAME);
			mBeanServer.registerMBean(css, name);
		}
		catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException e) 
		{
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void close()
	{
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		try
		{
			mBeanServer.unregisterMBean(new ObjectName(MBEAN_NAME));
		}
		catch (MalformedObjectNameException | MBeanRegistrationException | InstanceNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void notifyQueryExecuted(@Observes QueryExecutedEvent e)
	{
		css.queryNum.incrementAndGet();
	}

	public void notifyMsgPersisted(@Observes @MessagePersisted QueryExecutedEvent e)
	{
		css.persistedMessagesNum.incrementAndGet();
	}
	
}
