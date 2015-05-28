package ttt.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "game_users")
public class GameUser {
	
	@Id
	@GeneratedValue
	private Integer userId;
	
	@Column(nullable=false, unique=true)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Column(unique=true)
	private String email;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="games_saved")
    List<GameEntry> savedGames;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "games_played")
	List<GameEntry> playedGames;
	
	/*public GameUser(){
		
	}

	public GameUser(Integer userId, String username, String password,
			String email, List<GameEntry> savedGames,
			List<GameEntry> playedGames) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		//this.savedGames = savedGames;
		this.playedGames = playedGames;
	}*/

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<GameEntry> getSavedGames() {
		return savedGames;
	}

	public void setSavedGames(List<GameEntry> savedGames) {
		this.savedGames = savedGames;
	}

	public List<GameEntry> getPlayedGames() {
		return playedGames;
	}

	public void setPlayedGames(List<GameEntry> playedGames) {
		this.playedGames = playedGames;
	}
	
}
