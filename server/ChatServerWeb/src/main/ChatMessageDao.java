package main;

import java.util.Collection;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import main.interceptors.Printed;
import main.web.jmx.ChatServerStatistics.QueryExecutedEvent;
import main.web.jmx.MessagePersisted;

import common.ChatMessage;

@Stateless
@LocalBean
@Printed
public class ChatMessageDao
{
	private static final String FROM_CLAUSE = "from "+ChatMessage.class.getName();

	@PersistenceContext 
	private EntityManager em;
	
	@Inject 
	private Event<QueryExecutedEvent> queryEvent;
	
	@Inject 
	@MessagePersisted 
	private Event<QueryExecutedEvent> msgEvent;
	
	
	public Collection<ChatMessage> getMessages()
	{
		Collection<ChatMessage> result = em.createQuery(FROM_CLAUSE, ChatMessage.class).getResultList();
		queryEvent.fire(new QueryExecutedEvent());
		return result;
	}

	public Collection<ChatMessage> getMessagesByAuthor(String author)
	{
		Collection<ChatMessage> result = em.createQuery(FROM_CLAUSE + " where author = :auth", ChatMessage.class).setParameter("auth", author).getResultList();
		queryEvent.fire(new QueryExecutedEvent());
		return result;
	}

	public Collection<String> getAuthors()
	{
		Collection<String> result = em.createQuery("select distinct m.author "+ FROM_CLAUSE + " m", String.class).getResultList();
		queryEvent.fire(new QueryExecutedEvent());
		return result;
	}

	public void insertMessage(ChatMessage msg)
	{
		em.persist(msg);
		msgEvent.fire(new QueryExecutedEvent());
	}
}
