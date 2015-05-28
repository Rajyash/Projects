package ttt.model.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ttt.model.GameEntry;
import ttt.model.GameEntry.Outcome;
import ttt.model.GameUser;
import ttt.model.dao.GameDao;


@Repository
public class GameDaoImpl implements GameDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GameUser getGameUser( String username )
    {
        return (GameUser)entityManager.createQuery( "from GameUser where username =:user_name").setParameter("user_name", username).getSingleResult();
    }
    
    @Override
    public List<GameEntry> getGamesAgainstAI(GameUser game_user)
    {
    	List<GameEntry> finalGameEntry = new ArrayList<GameEntry>();
       	List<GameEntry> gEntry = game_user.getPlayedGames();
    	int winCount = 0;
    	int lossCount = 0;
    	//int totalGam
    	for(int i=0; i<gEntry.size();i++){
    		if(gEntry.get(i).isAgainstAI()){
    			if(gEntry.get(i).getGameState()!=null){
    				if(gEntry.get(i).getGameState().equals(Outcome.player1_win)){
    					winCount+=1;
    					finalGameEntry.add(gEntry.get(i));
    				}
    				if(gEntry.get(i).getGameState().equals(Outcome.AI_win)){
    					lossCount+=1;
    					finalGameEntry.add(gEntry.get(i));
    				}
    			}
    		}
    	}
    	
    	return finalGameEntry;
    }
    
    
	@Override
	@PreAuthorize("principal.username == #game_user.username")
	public List<GameEntry> getSavedGames(GameUser game_user) {
		// TODO Auto-generated method stub
		List<GameEntry> savedGames = new ArrayList<GameEntry>();
		List<GameEntry> saveEntry = game_user.getSavedGames();
    	int gameCount = 0;
    	
    	for(int i=0; i<saveEntry.size();i++){
    		if(saveEntry.get(i).getGameState().equals(Outcome.incomplete)){
    			gameCount+=1;
    			savedGames.add(saveEntry.get(i));
    		}
    	}
    	return savedGames;
    	
		
	}

	@Transactional
	@Override
	public GameUser saveUser(GameUser gameUser) {
		// TODO Auto-generated method stub
		return entityManager.merge(gameUser);
	}

	@Override
	public boolean authenticate(String username, String password) {
		// TODO Auto-generated method stub
		GameUser loggedInUser = null;
		try{
		loggedInUser = (GameUser) entityManager.createQuery("from GameUser where username =:user_name and password =:pwd").setParameter("user_name", username).setParameter("pwd", password).getSingleResult();
		}
		catch(Exception e){
			System.out.println(e);
		}
		if(loggedInUser!=null){
			return true;
		}
		else{
			return false;
		}
	}

	@Transactional
	@Override
	public void saveGameData(GameEntry gm) {
		/*GameEntry gs = entityManager.find(GameEntry.class, gm.getGameId());
		gs.setEndTime(new Date());*/
		entityManager.merge(gm);
	}
	
	@Transactional
	@Override
	public void saveGameAgain(Integer game_Id) {
		GameEntry gameAgain =  entityManager.find(GameEntry.class, game_Id);//new GameEntry();
		//gameAgain =	entityManager.find(GameEntry.class, game_Id);
		//gameAgain.setEndTime(new Date());
		//gameAgain.setEndTime(new Date());
		entityManager.merge(gameAgain);
	}

	@Transactional
	@Override
	public void savePlayedGames(List<GameEntry> gamesPlayed, Integer gameUserId) {
		// TODO Auto-generated method stub
		GameUser gUser = new GameUser();
		gUser = entityManager.find(GameUser.class, gameUserId);
		
		gUser.setPlayedGames(gamesPlayed);
		entityManager.merge(gUser);
	}
	
	@Transactional
	@Override
	public void saveSavedGames(List<GameEntry> gamesSaved, Integer gameUserId) {
		// TODO Auto-generated method stub
		GameUser gUser = new GameUser();
		gUser = entityManager.find(GameUser.class, gameUserId);
		
		gUser.setSavedGames(gamesSaved);
		entityManager.merge(gUser);
	}
	
	@Override
	public List<GameEntry> getAllGamesSaved(GameUser guPlayed) {
		// TODO Auto-generated method stub
		return (List<GameEntry>) entityManager.createQuery("from GameEntry where player1=:usersPlayed and gamestate=:outcome").setParameter("usersPlayed", guPlayed).setParameter("outcome", 0).getResultList();
	}

	@Override
	public List<GameEntry> getAllGamesPlayed(GameUser guPlayed) {
		// TODO Auto-generated method stub
		return (List<GameEntry>) entityManager.createQuery("from GameEntry where player1=:usersPlayed").setParameter("usersPlayed", guPlayed).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GameEntry> getGameHistory(GameUser user) {
		// TODO Auto-generated method stub
		return (List<GameEntry>) entityManager.createQuery("from GameEntry where player1="+user.getUserId()+" or player2="+user.getUserId()+"").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GameEntry> getGamesThisMonth(GameUser user){
		
		List<GameEntry> gList =  (List<GameEntry>) entityManager.createQuery("from GameEntry where Month(endtime) = Month(NOW()) and gamestate in (1,2,3,4) and player1=:usersPlayed or player2=:usersPlayed").setParameter("usersPlayed", user).getResultList();
		return gList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GameEntry getSavedMoves(Integer gId) {
		// TODO Auto-generated method stub
		return (GameEntry) entityManager.createQuery("from GameEntry where gameid=:g_ID").setParameter("g_ID", gId).getSingleResult();
	}

	@Override
	public GameEntry getgamebyid(Integer gid) {
		return entityManager.find(GameEntry.class, gid);
		
	}
	
	/*@Override
	public GameEntry getResumableGame(Integer gId) {
		// TODO Auto-generated method stub
		return (GameEntry) entityManager.createQuery("from GameEntry where gameid=:g_ID").setParameter("g_ID", gId).getSingleResult();
	}*/
	
	

}