package org.koushik.javabrains.messenger.database;

import java.util.HashMap;
import java.util.Map;

import org.koushik.javabrains.messenger.model.Message;
import org.koushik.javabrains.messenger.model.Profile;


public class DatabaseClass {
	
	// static so that other class can also use it
	private static Map<Long, Message> messages = new HashMap<>();
	// for profile its better to have key as a string
	private static Map<String, Profile> profiles = new HashMap<>();
	
	// any class can access messages or profiles by calling these static methods
	public static Map<Long, Message> getMessages(){
		return messages;
	}
	public static Map<String, Profile> getProfiles(){
		return profiles;
	}
	
}
