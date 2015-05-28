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

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WhosOnlineService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    List<String> usernames;

    //private static final Logger logger = LoggerFactory.getLogger( WhosOnlineService.class );

    Queue<DeferredResult<String>> results;

    public WhosOnlineService()
    {
        usernames = new ArrayList<String>();
        results = new LinkedList<DeferredResult<String>>();
    }

    public List<String> getUsernames()
    {
    	//GameUser username = gameDao.getGameUser(session.getAttribute("username").toString());
        return usernames;
    }

    public void add( String username )
    {
        usernames.add( username );
        //logger.debug( username + " added." );
        processDeferredResults();
    }

    public void remove( String username )
    {
        usernames.remove( username );
        //logger.debug( username + " removed." );
        processDeferredResults();
    }

    public void addResult( DeferredResult<String> result )
    {
        results.add( result );
    }

    private void processDeferredResults()
    {
        // convert username list to json
        String json = "";
        try
        {
            StringWriter sw = new StringWriter();
            objectMapper.writeValue( sw, usernames );
            json = sw.toString();
        }
        catch( Exception e )
        {
            //logger.error( "Failed to write to JSON", e );
        }

        // process queued results
        while( !results.isEmpty() )
        {
            DeferredResult<String> result = results.remove();
            result.setResult( json );
        }
    }

}
