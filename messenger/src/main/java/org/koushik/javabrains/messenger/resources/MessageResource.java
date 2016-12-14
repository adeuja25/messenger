package org.koushik.javabrains.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.koushik.javabrains.messenger.model.Message;
import org.koushik.javabrains.messenger.resources.beans.MessageFilterBean;
import org.koushik.javabrains.messenger.service.MessageService;

@Path("/messages")//Path of the function
// consuming for the postman rest client
@Consumes(MediaType.APPLICATION_JSON)// instead of writing in all the methods
//@Produces(value={MediaType.APPLICATION_JSON, MediaType.TEXT_XML})// we write it here for convenience
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();
	// API # 1 to get the messages
	@GET // gets the message
	@Produces(MediaType.APPLICATION_JSON)
	// QueryParam for /messages?year=2016 to see the JSON response of 2016
	public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean){//query params called from MessageFilterBean
		System.out.println("JSON method called");
		if(filterBean.getYear()> 0){
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart()>=0 && filterBean.getSize()>0){
			return messageService.getAllMessagesPaginated(filterBean.getStart(),filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@GET // gets the message
	@Produces(MediaType.TEXT_XML)
	// QueryParam for /messages?year=2016 to see the JSON response of 2016
	public List<Message> getXmlMessages(@BeanParam MessageFilterBean filterBean){//query params called from MessageFilterBean
		System.out.println("XML method called");

		if(filterBean.getYear()> 0){
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart()>=0 && filterBean.getSize()>0){
			return messageService.getAllMessagesPaginated(filterBean.getStart(),filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@POST
	// accept the model type as argument to bind the request body
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		System.out.println(uriInfo.getAbsolutePath());
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
				.entity(newMessage)
				.build();
		
		
		//return messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id,Message message){
		message.setId(id); // to set the id in the postman 
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	// not returning anything for delete: void
	public void deleteMessage(@PathParam("messageId") long id){
		messageService.removeMessage(id);
	}
	
	// API # 2 to get the messages  under /messageId
	@GET
	// @Path("/test") for messages/test maps test
	@Path("/{messageId}") // {} means variable inside can be 1 or 2 in our case
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo){ 
		
		Message message = messageService.getMessage(id); // send parameter to MessageService
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
				 .path(MessageResource.class)
				 .path(MessageResource.class,"getCommentResource")
				 .path(CommentResource.class)
				 .resolveTemplate("messageId",message.getId())
				 .build();
		return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
				 .path(ProfileResource.class)
				 .path(message.getAuthor())
				 .build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getRequestUriBuilder()
			.path(MessageResource.class)
			.path(Long.toString(message.getId()))
			.build()
			.toString();
		return uri;
	}
	
	// this method calls commentresource because comments come under messageID
	// looks for the resource in the commentresource
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(){
		return new CommentResource();
	}
}
