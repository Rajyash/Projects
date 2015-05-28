package ttt.model;

import java.util.Date;

import org.springframework.web.context.request.async.DeferredResult;

import ttt.model.GameEntry.Outcome;

public class TwoPlayerGames {
	
	String gameBetween;
	
	Outcome gameResult;
	
	//String whosTurn;
	
	Date multiStartTime;

	String[] cellBoxes = new String[9];
	 
	public TwoPlayerGames() {
		//super();
		// TODO Auto-generated constructor stub
	}

	public TwoPlayerGames(String gameBetween, Outcome gameResult, /*String whosTurn,*/ Date multiStartTime, String[] cellBoxes) {
		//super();
		this.gameBetween = gameBetween;
		this.gameResult = gameResult;
		//this.whosTurn = whosTurn;
		this.multiStartTime = multiStartTime;
		this.cellBoxes = cellBoxes;
	}

	public String getGameBetween() {
		return gameBetween;
	}

	public void setGameBetween(String gameBetween) {
		this.gameBetween = gameBetween;
	}

	public Outcome getGameResult() {
		return gameResult;
	}

	public void setGameResult(Outcome gameResult) {
		this.gameResult = gameResult;
	}

	/*public String getWhosTurn() {
		return whosTurn;
	}

	public void setWhosTurn(String whosTurn) {
		this.whosTurn = whosTurn;
	}*/

	public Date getMultiStartTime() {
		return multiStartTime;
	}

	public void setMultiStartTime(Date multiStartTime) {
		this.multiStartTime = multiStartTime;
	}

	public String[] getCellBoxes() {
		return cellBoxes;
	}

	public void setCellBoxes(String[] cellBoxes) {
		this.cellBoxes = cellBoxes;
	}
	
}
