package org.koushik.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.koushik.javabrains.messenger.database.DatabaseClass;
import org.koushik.javabrains.messenger.exception.DataNotFoundException;
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
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for(Message message: messages.values()){
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR)==year){
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if(start+size > list.size())
			return new ArrayList<Message>();
		return list.subList(start, start + size);
		
	}
	
	public Message getMessage(long id){
		 Message message = messages.get(id);
		 if(message==null){
			 throw new DataNotFoundException("Message with id " + id + " is not found");
		 }
		 return message;
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
