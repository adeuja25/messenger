package org.koushik.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.koushik.javabrains.messenger.database.DatabaseClass;
import org.koushik.javabrains.messenger.model.Message;

public class MessageService {
	
	// calling from static method from DatabaseClass
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		// 1L and 2L are id name
		messages.put(1L, new Message(1, "Hello World", "Anup"));
		messages.put(2L, new Message(2, "Hello Jersey", "Anup"));
	}
	
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public Message getMessage(long id){
		return messages.get(id);
	}
	
	public Message addMessage(Message message){
		// adding 1 to get the next element and new id is set
		message.setId(messages.size() + 1);
		// putting it to the map
		messages.put(message.getId(), message);
		return message; // returning added message
	}
	
	public Message updateMessage(Message message){
		
		if(message.getId()<=0){
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	// accepts long id
	public Message removeMessage(long id){
		return messages.remove(id);
	}
}
