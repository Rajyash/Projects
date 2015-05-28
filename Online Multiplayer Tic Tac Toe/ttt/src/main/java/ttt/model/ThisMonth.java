package ttt.model;

import ttt.model.GameEntry.Outcome;

public class ThisMonth {
	
	Integer gameId;
	
	Outcome gameState;
	
	String opponent;
	
	String gameDuration;
	
	String winOrLose;
	
	public ThisMonth(Integer gameId, String opponent, Outcome gameState,
			String gameDuration, String winOrLose) {
		// TODO Auto-generated constructor stub
		this.gameId = gameId;
		this.opponent = opponent;
		this.gameState = gameState;
		this.gameDuration = gameDuration;
		this.winOrLose = winOrLose;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	
	public Outcome getGameState() {
		return gameState;
	}
	public void setGameState(Outcome gameState) {
		this.gameState = gameState;
	}
	public String getOpponent() {
		return opponent;
	}
	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}
	public String getGameDuration() {
		return gameDuration;
	}
	public void setGameDuration(String gameDuration) {
		this.gameDuration = gameDuration;
	}
	public String getWinOrLose() {
		return winOrLose;
	}
	public void setWinOrLose(String winOrLose) {
		this.winOrLose = winOrLose;
	}
	
}
