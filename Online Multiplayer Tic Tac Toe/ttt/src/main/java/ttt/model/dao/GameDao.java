package ttt.model.dao;

import java.util.Date;
import java.util.List;

import ttt.model.GameEntry;
import ttt.model.GameUser;

public interface GameDao {

	GameUser getGameUser( String username);

	List<GameEntry> getGamesAgainstAI(GameUser username);

	List<GameEntry> getSavedGames( GameUser gameUser );
	
	GameUser saveUser( GameUser gameUser);
	
	boolean authenticate(String username, String password);
	
	void saveGameData(GameEntry gm);
	
	void savePlayedGames(List<GameEntry> gamesPlayed, Integer gameUserId);
	
	void saveSavedGames(List<GameEntry> gamesPlayed, Integer gameUserId);
	
	List<GameEntry> getAllGamesPlayed(GameUser guPlayed);
	
	List<GameEntry> getAllGamesSaved(GameUser guPlayed);
		
	List<GameEntry> getGameHistory(GameUser user);
	
	List<GameEntry> getGamesThisMonth(GameUser user);
	
	GameEntry getSavedMoves(Integer gId);
	
	void saveGameAgain(Integer game_Id);//, GameEntry ge);
	//GameEntry getResumableGame(Integer gId);
	GameEntry getgamebyid(Integer gid);
}
