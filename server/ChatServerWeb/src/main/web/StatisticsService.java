package main.web;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.web.jmx.ChatServerStatistics;

@Path("/stats")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class StatisticsService 
{
	@Inject ChatServerStatistics statistics;
	
	@GET
	@Path("/querynum")
	public long getQueryNum()
	{
		return statistics.getExecutedQueryNum();
	}
	
	@GET
	@Path("/persistnum")
	public long getPersistedMessagesNum()
	{
		return statistics.getInsertedMessagesNum();
	}
}
