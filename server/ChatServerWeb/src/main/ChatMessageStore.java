package main;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import common.ChatMessage;

@Path("/store")
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.WILDCARD } )
public class ChatMessageStore 
{
	@Inject private ChatMessageDao chatMessageDao;
	
	@GET
	@Path("/messages")
	public Collection<ChatMessage> getMessages()
	{
		return chatMessageDao.getMessages();
	}
	
	@GET
	@Path("/messages/{author}")
	public Collection<ChatMessage> getAuthorMessages(@PathParam("author") String author)
	{
		return chatMessageDao.getMessagesByAuthor(author);
	}
	
	@GET
	@Path("/authors")
	public Collection<String> getAuthors()
	{
		return chatMessageDao.getAuthors();
	}
	
}
