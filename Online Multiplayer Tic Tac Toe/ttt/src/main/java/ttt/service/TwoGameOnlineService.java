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

import ttt.model.GameUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TwoGameOnlineService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    List<String> hostedUserNames;

    List<String> joinedUserNames;
    //private static final Logger logger = LoggerFactory.getLogger( WhosOnlineService.class );

    Queue<DeferredResult<String>> results;

    public TwoGameOnlineService()
    {
    	hostedUserNames = new ArrayList<String>();
    	joinedUserNames = new ArrayList<String>();
        results = new LinkedList<DeferredResult<String>>();
    }
    
   /* public TwoGameOnlineService(List<String> hostedUserNames, List<String> joinedUserNames)
    {
    	hostedUserNames = this.hostedUserNames;
    	joinedUserNames = this.joinedUserNames;
        //results = new LinkedList<DeferredResult<String>>();
    }*/
    

    //@JsonIgnore
    public List<String> getHostedUsernames()
    {
    	//GameUser username = gameDao.getGameUser(session.getAttribute("username").toString());
        return hostedUserNames;
    }
    
    //@JsonIgnore
    public List<String> getJoinedUsernames()
    {
    	//GameUser username = gameDao.getGameUser(session.getAttribute("username").toString());
        return joinedUserNames;
    }

    public void addHostedUsers( String hostedServiceUsers )
    {
    	hostedUserNames.add( hostedServiceUsers );
        //logger.debug( username + " added." );
    	//processDeferredResultsForHostedUsers();
    }

    public void removeHostedUsers( String hostedServiceUsers )
    {
    	hostedUserNames.remove( hostedServiceUsers );
        //logger.debug( username + " removed." );
    	//processDeferredResultsForHostedUsers();
    }

    public void addResultHostedUsers( DeferredResult<String> result )
    {
        results.add( result );
    }
    
    public void addJoinedUsers( String joinedServiceUsers )
    {
    	joinedUserNames.add( joinedServiceUsers );
        //logger.debug( username + " added." );
        //processDeferredResultsForJoinedUsers();
    }

    public void removeJoinedUsers( String joinedServiceUsers )
    {
    	joinedUserNames.remove( joinedServiceUsers );
        //logger.debug( username + " removed." );
        //processDeferredResultsForJoinedUsers();
    }

    public void addResultJoinedUsers( DeferredResult<String> result )
    {
        results.add( result );
    }
   

}
