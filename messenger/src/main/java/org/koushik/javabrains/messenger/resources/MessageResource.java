package org.koushik.javabrains.messenger.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.koushik.javabrains.messenger.model.Message;
import org.koushik.javabrains.messenger.service.MessageService;

@Path("/messages")//Path of the function
public class MessageResource {
	
	MessageService messageService = new MessageService();
	// API # 1 to get the messages
	@GET // gets the message
	@Produces(MediaType.APPLICATION_JSON) // returns text/plain in the browser
	public List<Message> getMessages(){
		return messageService.getAllMessages();
	}
	
	// API # 2 to get the messages  under /messageId
	@GET
	// @Path("/test") for messages/test maps test
	@Path("/{messageId}") // {} means variable inside can be 1 or 2 in our case
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId") long id){ 
		return messageService.getMessage(id); // send parameter to MessageService 
	}
}
