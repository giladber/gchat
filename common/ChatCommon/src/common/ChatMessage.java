package common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ChatMessage implements Serializable
{
	private static final long serialVersionUID = 1L;

	public String msg;
	public String author;
	private Date publishDate;
	private long id;
	
	public ChatMessage()
	{
		msg = author = "";
	}
	
	public ChatMessage(String msg, String origin, Date date)
	{
		this.msg = msg;
		this.author = origin;
		this.publishDate = date;
	}
	
	@Column(name="content")
	public String getMsg()
	{
		return this.msg;
	}
	
	@Column(name="author")
	public String getAuthor()
	{
		return this.author;
	}

	@Id	@GeneratedValue
	public long getId()
	{
		return this.id;
	}
	
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public void setAuthor(String origin)
	{
		this.author = origin;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}

	@Column(name="date")
	public Date getPublishDate() 
	{
		return publishDate;
	}

	public void setPublishDate(Date publishDate) 
	{
		this.publishDate = publishDate;
	}
	
	

}
