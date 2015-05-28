package ttt.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "games")
public class GameEntry {
	
	@Id
	@GeneratedValue
	private Integer gameId;
	
	private Date startTime;
	
	private Date endTime;
	
	private Date saveTime;
	
	public enum Outcome {incomplete, player1_win, player2_win, AI_win, tie_games };
	
	private Outcome gameState;//win or loss or tie
	
	private boolean againstAI;
	
	@ManyToOne
	@JoinColumn(name="player1")
	private GameUser player1;
	
	@ManyToOne
	@JoinColumn(name="player2")
	private GameUser player2;
	
	
	
	@ElementCollection
	@CollectionTable(name="moves", joinColumns=@JoinColumn(name="game_id"))
	@Column(name="board")
	private List<String> board;
	
	/*private String pos0;
	
	private String pos1;
	
	private String pos2;
	
	private String pos3;
	
	private String pos4;
	
	private String pos5;
	
	private String pos6;
	
	private String pos7;
	
	private String pos8;*/

	/*public GameEntry() {
		// TODO Auto-generated constructor stub
	}

	public GameEntry(Integer gameId, Date startTime, Date endTime,
			Date saveTime, Outcome gameState, boolean againstAI,
			GameUser player1, GameUser player2, List<String> board) {
		super();
		this.gameId = gameId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.saveTime = saveTime;
		this.gameState = gameState;
		this.againstAI = againstAI;
		this.player1 = player1;
		this.player2 = player2;
		this.board = board;
	}*/
	
	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	public Outcome getGameState() {
		return gameState;
	}

	public void setGameState(Outcome gameState) {
		this.gameState = gameState;
	}

	public boolean isAgainstAI() {
		return againstAI;
	}

	public void setAgainstAI(boolean againstAI) {
		this.againstAI = againstAI;
	}

	public GameUser getPlayer1() {
		return player1;
	}

	public void setPlayer1(GameUser player1) {
		this.player1 = player1;
	}

	public GameUser getPlayer2() {
		return player2;
	}

	public void setPlayer2(GameUser player2) {
		this.player2 = player2;
	}

	public List<String> getBoard() {
		return board;
	}

	public void setBoard(List<String> board) {
		this.board = board;
	}

	

	
	/*public String getPos0() {
		return pos0;
	}

	public void setPos0(String pos0) {
		this.pos0 = pos0;
	}

	public String getPos1() {
		return pos1;
	}

	public void setPos1(String pos1) {
		this.pos1 = pos1;
	}

	public String getPos2() {
		return pos2;
	}

	public void setPos2(String pos2) {
		this.pos2 = pos2;
	}

	public String getPos3() {
		return pos3;
	}

	public void setPos3(String pos3) {
		this.pos3 = pos3;
	}

	public String getPos4() {
		return pos4;
	}

	public void setPos4(String pos4) {
		this.pos4 = pos4;
	}

	public String getPos5() {
		return pos5;
	}

	public void setPos5(String pos5) {
		this.pos5 = pos5;
	}

	public String getPos6() {
		return pos6;
	}

	public void setPos6(String pos6) {
		this.pos6 = pos6;
	}

	public String getPos7() {
		return pos7;
	}

	public void setPos7(String pos7) {
		this.pos7 = pos7;
	}

	public String getPos8() {
		return pos8;
	}

	public void setPos8(String pos8) {
		this.pos8 = pos8;
	}*/
	
}
