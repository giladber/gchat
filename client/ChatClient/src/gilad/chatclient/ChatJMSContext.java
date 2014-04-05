package gilad.chatclient;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ChatJMSContext implements AutoCloseable
{
	private Context ctx;
	private ConnectionFactory factory;
	private Destination dest;
	private Connection conn;
	private Session session;

	private static final Properties JMS_PROPERTIES;
	private static final String DEFAULT_USERNAME = "gilad";
	private static final String DEFAULT_PASSWORD = "123123";
	private static final String CONN_FACTORY_JNDI_NAME = "/jms/RemoteConnectionFactory";
	private static final String DESTINATION_JNDI_NAME = "/jms/topic/chat";
	private static final String DEFAULT_CHAT_PROVIDER_URL = "remote://localhost:4447";
	
	private static final String PROVIDER_URL = System.getProperty("CHAT_PROVIDER", DEFAULT_CHAT_PROVIDER_URL);

	static
	{
		JMS_PROPERTIES = new Properties();
		JMS_PROPERTIES.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		JMS_PROPERTIES.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		JMS_PROPERTIES.put(Context.PROVIDER_URL, PROVIDER_URL);
		JMS_PROPERTIES.put("jboss.naming.client.ejb.context", true);
		JMS_PROPERTIES.put(Context.SECURITY_PRINCIPAL, DEFAULT_USERNAME);
		JMS_PROPERTIES.put(Context.SECURITY_CREDENTIALS, DEFAULT_PASSWORD);
	}

	public ChatJMSContext() throws NamingException, JMSException
	{
		this.ctx = new InitialContext(JMS_PROPERTIES);
		this.factory = (ConnectionFactory) ctx.lookup(CONN_FACTORY_JNDI_NAME);
		this.dest = (Destination) ctx.lookup(DESTINATION_JNDI_NAME);
		this.conn = factory.createConnection();
		this.session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}
	
	public void attachListener(MessageListener listener) throws JMSException
	{
		MessageConsumer consumer = session.createConsumer(dest);
		consumer.setMessageListener(listener);
		this.conn.start();
	}
	
	public Session session()
	{
		return this.session;
	}
	
	public Connection conn()
	{
		return this.conn;
	}
	
	public Destination dest()
	{
		return this.dest;
	}

	@Override
	public void close() throws Exception 
	{
		if (this.session != null)
		{
			session.close();
		}
		
		if (this.conn != null)
		{
			conn.close();
		}
	}
	
	
}
