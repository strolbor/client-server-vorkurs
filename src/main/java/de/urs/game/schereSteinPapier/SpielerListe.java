package de.urs.game.schereSteinPapier;

public class SpielerListe {

	String[] PlayerList = new String[2];
	int zahler=0;
	public String[] getPlayerList() {
		return PlayerList;
	}
	public void setPlayerList(String[] playerList) {
		PlayerList = playerList;
	}
	public void addPlayer(String playerId) {
		PlayerList[zahler++] = playerId;
	}
	public void removePlayer(String playerId) {
		if(PlayerList[0] == playerId) {
			zahler=0;
		}
		if(PlayerList[1] == playerId) {
			zahler=1;
		}
		PlayerList[zahler] = null;
	}
	
	public SpielerListe() {
	}
	
	

}
