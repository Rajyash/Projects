package ttt.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;








//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import ttt.model.GameEntry;
import ttt.model.GameEntry.Outcome;
import ttt.model.GameUser;
import ttt.model.TwoPlayerGames;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MultiPlayerGameOnlineService extends DeferredResult{

    private static final ObjectMapper objectMapper = new ObjectMapper();

    List<TwoPlayerGames> multiPlayerGames;

    
    //private static final Logger logger = LoggerFactory.getLogger( WhosOnlineService.class );

    //Queue<DeferredResult<String>> results;
    
    Queue<DeferredResult<List<TwoPlayerGames>>> results;

    public MultiPlayerGameOnlineService()
    {
    	multiPlayerGames = new ArrayList<TwoPlayerGames>();
    	//joinedUserNames = new ArrayList<String>();
        results = new LinkedList<DeferredResult<List<TwoPlayerGames>>>();
    }
    
   /* public TwoGameOnlineService(List<String> hostedUserNames, List<String> joinedUserNames)
    {
    	hostedUserNames = this.hostedUserNames;
    	joinedUserNames = this.joinedUserNames;
        //results = new LinkedList<DeferredResult<String>>();
    }*/
    

    /*//@JsonIgnore
    public List<String> getHostedUsernames()
    {
    	//GameUser username = gameDao.getGameUser(session.getAttribute("username").toString());
        return hostedUserNames;
    }*/
    
    public List<TwoPlayerGames> getMultiPlayerGames(){
    	return multiPlayerGames;
    }
    
    //@JsonIgnore
    /*public List<String> getJoinedUsernames()
    {
    	//GameUser username = gameDao.getGameUser(session.getAttribute("username").toString());
        return joinedUserNames;
    }*/

    public void add( TwoPlayerGames geAdded )
    {
    	multiPlayerGames.add( geAdded );
        //logger.debug( username + " added." );
    	processDeferredResultsForGame();
    }
    
    public void updateResults()
    {
    	processDeferredResultsForGame();
    }

    public void remove( TwoPlayerGames geRemoved )
    {
    	multiPlayerGames.remove( geRemoved );
        //logger.debug( username + " removed." );
    	processDeferredResultsForGame();
    }

    public void addResult( DeferredResult<List<TwoPlayerGames>> result )
    {
        results.add( result );
    }
    
   /* public void addJoinedUsers( String joinedServiceUsers )
    {
    	joinedUserNames.add( joinedServiceUsers );
        //logger.debug( username + " added." );
        //processDeferredResultsForJoinedUsers();
    }*/

   /* public void removeJoinedUsers( String joinedServiceUsers )
    {
    	joinedUserNames.remove( joinedServiceUsers );
        //logger.debug( username + " removed." );
        //processDeferredResultsForJoinedUsers();
    }

    public void addResultJoinedUsers( DeferredResult<String> result )
    {
        results.add( result );
    }*/
   
    private void processDeferredResultsForGame()
    {
        // convert username list to json
        //List<TwoPlayerGames> json = new ArrayList<TwoPlayerGames>();
    	String json="";
        try
        {
            StringWriter sw = new StringWriter();
            //TwoPlayerGames tw = new TwoPlayerGames();
        	objectMapper.writeValue( sw, multiPlayerGames );
            json = sw.toString();
        	//TwoPlayerGames twJson = new TwoPlayerGames();
        	//json=multiPlayerGames;
            System.out.println("json:"+json);
        }
        catch( Exception e )
        {
            //logger.error( "Failed to write to JSON", e );
        }

        // process queued results
        while( !results.isEmpty() )
        {
            DeferredResult<List<TwoPlayerGames>> result = results.remove();
            result.setResult( multiPlayerGames );
        }
    }

}
