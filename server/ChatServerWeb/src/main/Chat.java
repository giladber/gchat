package main;

import java.util.Set;
import java.util.HashSet;

import javax.ws.rs.core.Application;

import main.web.StatisticsService;

/**
 * This class is used to initialize the JAX-RS webservices of the chat server application.
 * 
 * @author Gilad Ber
 *
 */
public class Chat extends Application 
{

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();
	
	public Chat()
	{
		classes.add(ChatMessageStore.class);
		classes.add(StatisticsService.class);
	}
	
	@Override
	public Set<Class<?>> getClasses() 
	{
	     return classes;
	}
	
	@Override
	public Set<Object> getSingletons() 
	{
	     return singletons;
	}
}
