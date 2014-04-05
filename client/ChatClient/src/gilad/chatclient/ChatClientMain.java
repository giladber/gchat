package gilad.chatclient;

import gilad.chatclient.jmx.Chat;
import gilad.chatclient.jmx.ChatMBean;

import java.lang.management.ManagementFactory;

import javax.jms.JMSException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class ChatClientMain {

	public static void main(String[] args) 
	{
		String user = args.length >= 1 ? args[0] : "default";
		launch(user);
	}

	private static void launch(String username) 
	{
		ChatController controller = new ChatController(username);
		try
		{
			ChatMBean chatBean = controller.start();
			registerJmxBeans(chatBean);
		}
		catch (JMSException | MalformedObjectNameException | InstanceAlreadyExistsException 
				| MBeanRegistrationException | NotCompliantMBeanException e) 
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void registerJmxBeans(ChatMBean chatBean) throws MalformedObjectNameException,
			InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException 
	{
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName(Chat.class.getPackage().toString() + ":type=Chat");
		mBeanServer.registerMBean(chatBean, name);
	}

}
