package org.sjsu.edu.model;

import java.util.HashMap;

public class Trackplayer {

	private static int Id = 0;
	String playerName;
	String gameName;
	private static HashMap<String, Integer> playerId = new HashMap<String, Integer>();
	
	public Trackplayer() {
		// TODO Auto-generated constructor stub
	
	}
	
	public String getplayerName() {
		return playerName;
	}

	public void setplayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getgameName() {
		return gameName;
	}

	public void setgameName(String gameName) {
		this.gameName = gameName;
	}

	@Override
	public String toString() {
	if(!playerId.containsKey(playerName)) 
		playerId.put(playerName, ++Id);
	 return "Hello " + playerName + "! You are registered as a player, for the game " + gameName + " Your Id is " + playerId.get(playerName);
	}
	
}
