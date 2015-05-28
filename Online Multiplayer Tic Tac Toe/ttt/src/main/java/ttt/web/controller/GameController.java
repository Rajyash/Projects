package ttt.web.controller;

import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javassist.expr.NewArray;







//import javax.persistence.Query;
import javax.servlet.http.HttpSession;
//import javax.xml.stream.events.EndDocument;
//import org.hibernate.Hibernate;
//import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;


import ttt.model.GameEntry;
import ttt.model.GameUser;
import ttt.model.GameEntry.Outcome;
import ttt.model.ThisMonth;
import ttt.model.TwoPlayerGames;
import ttt.model.dao.GameDao;
import ttt.service.HostOnlineService;
import ttt.service.JoinOnlineService;
import ttt.service.MultiPlayerGameOnlineService;
import ttt.service.TwoGameOnlineService;
import ttt.service.WhosOnlineService;

@Controller
public class GameController {

    @Autowired
    private GameDao gameDao;
    
    @Autowired
    WhosOnlineService wos;

    @Autowired
    HostOnlineService hos;

    @Autowired
    JoinOnlineService jos;
    
    @Autowired
    TwoGameOnlineService tgos;
    
    @Autowired
    MultiPlayerGameOnlineService mpos;

    
    @RequestMapping(value="/registration.html", method = RequestMethod.GET)
    public String register( ModelMap models )
    {
        models.put( "gameUser", new GameUser() );
        return "registration";
    }
    
    @RequestMapping(value="/registration.html", method = RequestMethod.POST)
    public String register( @ModelAttribute("gameUser") GameUser gameUser, BindingResult bindingResult){
    	gameDao.saveUser(gameUser);
    	return "redirect:/login1.html";
    }
    
    @RequestMapping(value="/login.html", method = RequestMethod.GET)
    public String login( ModelMap models,HttpSession session){
    	//GameUser user = gameDao.getGameUser(session.getAttribute("username").toString());
    	//wos.remove(user.getUsername());
    	session.invalidate();
    	models.put( "gameUser", new GameUser());
    	return "login";
    }
    
    @RequestMapping(value="/login.html", method = RequestMethod.POST)
    public String login( @ModelAttribute("gameUser") GameUser gameUser, HttpSession session){
    	boolean flag = gameDao.authenticate(gameUser.getUsername(), gameUser.getPassword());
    	if(flag){
    		session.setAttribute("username", gameUser.getUsername());
    		//wos.add(gameUser.getUsername());
    		return "redirect:/Menu.html";
    	}
    	else{
    		return "login";
    	}
    }
    
    @RequestMapping(value="/Menu.html", method = RequestMethod.GET)
    public String menuPage( HttpSession session){
    	//if(session.getAttribute("username")!=null){
    		return "Menu";
    	//}
    	/*else{
    		return "redirect:/login.html";
    	}*/
    	
    }
    
    @RequestMapping(value="/SaveGame.html", method = RequestMethod.GET)
    public String saveGame(@RequestParam Integer row, HttpSession session){
    	//if(session.getAttribute("username")!=null){
    		GameEntry gSave = (GameEntry) session.getAttribute("gStart");
    		
    		if(gSave==null){
    			gSave = new GameEntry();
    			if(session.getAttribute("sessionGameId") !=null)
    			{
    				gSave = gameDao.getgamebyid((Integer)session.getAttribute("sessionGameId"));
    			}
    			else
    			{
    				gSave = (GameEntry)session.getAttribute("gStart");
    			}
    			//gSave=gNewSave;
    		}
    		System.out.println("gsave:"+gSave.getStartTime());
    	String[] cell = (String[]) session.getAttribute("cell");
    	GameUser user = gameDao.getGameUser(session.getAttribute("username").toString());
    	List<GameEntry> gSavedList = new ArrayList<GameEntry>();
    	List<GameEntry> gPlayedList = new ArrayList<GameEntry>();
		//gSavedList = gameDao.getAllGamesSaved(user);
		//gPlayedList = gameDao.getAllGamesPlayed(user);
    	List<String> boardList = new ArrayList<String>();
    	String element = "";
    	for(int i=0;i<9;i++){
    		element = i+":"+cell[i];
    		boardList.add(element);
    	}
    	//gSave.setBoard(boardList);
    	//
    	if((gSave.getGameState()==null) && row==9999){
    		gSave.setGameState(Outcome.incomplete);
    		gSave.setSaveTime(new Date());
    		gSave.setBoard(boardList);
    		user.getSavedGames().add(gSave);
    		System.out.println("Start : "+gSave.getStartTime());
    		//gameDao.savePlayedGames(gPlayedList, user.getUserId());
    		//gameDao.saveSavedGames(gSavedList, user.getUserId());
    		/*List<GameEntry> g = user.getSavedGames();
    		boolean flag=false;
    		System.out.println("Size :"+g.size());
    		for(int i=0;i<g.size();i++){
    			
    			if(g.get(i).getStartTime().equals(gSave.getStartTime())){
    				g.get(i).setBoard(boardList);
    				g.get(i).setGameState(Outcome.incomplete);
    				g.get(i).setSaveTime(new Date());
    				user.setSavedGames(g);
    				gameDao.saveUser(user);
    				flag=true;
    			}
    		}
    		if(flag){
    		user.getSavedGames().add(gSave);*/	
			gameDao.saveUser(user);
    		//}
    		//gameDao.saveGameData(gSave);
    		
    		//session.removeAttribute("gStart");
    	}
    	else if((gSave.getGameState().equals(Outcome.incomplete)) && row==9999){
    		//gSave.getBoard().clear();
    		//gSave.setBoard(boardList);
    		
    		GameEntry gm = gameDao.getgamebyid((Integer)session.getAttribute("sessionGameId")); 
    		System.out.println("gameentry:"+gm.getStartTime());
    		List<GameEntry> g = gameDao.getAllGamesSaved(user);//SavedGames(user);
    		//boolean hasBeenSaved=(boolean) session.getAttribute("hasBeenSaved");
    		System.out.println("Size :"+g.size());
    		for(int i=0;i<g.size();i++){
    			System.out.println("before if: g.get(i)"+g.get(i).getStartTime());
    			//int x = g.get(i).getStartTime().compareTo(gSave.getStartTime())
    			if(g.get(i).getStartTime().compareTo(gm.getStartTime())==0){
    				System.out.println("inside if");
    				System.out.println("g.get(i):"+g.get(i).getStartTime());
    				g.get(i).setBoard(boardList);
    				g.get(i).setGameState(Outcome.incomplete);
    				g.get(i).setSaveTime(new Date());
    				user.getSavedGames().remove(g.get(i));
    				user.getSavedGames().add(g.get(i));
    				gameDao.saveUser(user);
    			}
    			/*if(g.get(i).getStartTime().equals(gSave.getStartTime())){
    				g.get(i).setBoard(boardList);
    				g.get(i).setGameState(Outcome.incomplete);
    				g.get(i).setSaveTime(new Date());
    				user.getSavedGames().remove(g.get(i));
    				user.setSavedGames(g);//.add(g.get(i));
    				gameDao.saveUser(user);
    				//flag=true;
    			}*/
    		}
    		/*if(flag){
    		user.getSavedGames().add(gSave);	
			gameDao.saveUser(user);
    		}*/
    	}
    	else if(gSave.getGameState()!=null && row==9999){
    		return "redirect:/Menu.html";
    	}
    	return "Menu";
    	//}
    	/*else{
    		return "redirect:/login.html";
    	}*/
    }
    
    
    
    
    
    @RequestMapping(value="/GamesSaved.html", method=RequestMethod.GET)
    public String getListofSavedGames(ModelMap models, HttpSession session){
    	//if(session.getAttribute("username")!=null){
    	List<GameEntry> savedGames = new ArrayList<GameEntry>();
    	GameUser gUser = gameDao.getGameUser(session.getAttribute("username").toString());
    	List<GameEntry> gTempList = new ArrayList<GameEntry>();
		GameUser user = gameDao.getGameUser(session.getAttribute("username").toString());
		
		savedGames = user.getSavedGames();//gameDao.getSavedGames(gUser);//.getAllGamesSaved(gUser);
		for(int i=0; i<gTempList.size(); i++){
			
		}
    	//savedGames = gameDao.getSavedGames(gUser);
    	System.out.println("sGame Size:"+savedGames.size());
    	models.addAttribute("savedGames",savedGames);
    	return "GamesSaved";
    	/*}
    	else{
    		return "redirect:/login.html";
    	}*/
    }
    
   
    
    @RequestMapping(value="/logout.html", method = RequestMethod.GET)
    public String logout(@RequestParam Integer row, HttpSession session){
    	GameEntry gameLogout = (GameEntry) session.getAttribute("gStart");
    	GameUser user = gameDao.getGameUser(session.getAttribute("username").toString());
    	/*if(gameLogout.getGameState()==null && row==999){
    		gameLogout.setEndTime(new Date());
    		gameLogout.setGameState(Outcome.AI_win);
    		gameDao.saveGameData(gameLogout);
    		List<GameEntry> geList = gameDao.getAllGamesPlayed(user);
			gameDao.savePlayedGames(geList, user.getUserId());
    	}
    	else if(gameLogout.getGameState().equals(Outcome.incomplete) && row==999){
    		gameLogout.setEndTime(new Date());
    		gameLogout.setGameState(Outcome.AI_win);
    		gameDao.saveGameData(gameLogout);
    		List<GameEntry> geList = gameDao.getAllGamesPlayed(user);
			gameDao.savePlayedGames(geList, user.getUserId());
    	}*/
    	if(session.getAttribute("sessionGameId")==null && row==999){
    		GameEntry gStart = (GameEntry) session.getAttribute("gStart");
    		if(gStart==null){
    			return "redirect:/login.html";
    		}
    		if(gStart.getGameState()==null){
    		gStart.setGameState(Outcome.AI_win);
    		gStart.setEndTime(new Date());
    		gameDao.saveGameData(gStart);
    		List<GameEntry> geList = gameDao.getAllGamesPlayed(user);
    		gameDao.savePlayedGames(geList, user.getUserId());
    		}
    	}
    	else if(session.getAttribute("sessionGameId") !=null && row==999){
    		GameEntry gStart = gameDao.getgamebyid((Integer)session.getAttribute("sessionGameId"));
    		if(gStart.getGameState().equals(Outcome.incomplete)){
    			
    			gStart.setGameState(Outcome.AI_win);
    			gStart.setSaveTime(null);
        		gStart.setEndTime(new Date());
        		
    		gameDao.saveGameData(gStart);
    		List<GameEntry> geList = gameDao.getAllGamesPlayed(user);
    		gameDao.savePlayedGames(geList, user.getUserId());
    		//user.getPlayedGames().add(gStart);
    		//session.removeAttribute("gStart");
    		//return "redirect:/DisplayInitGame.html";
    		}
    	
    	}
    	//wos.remove(user.getUsername());
    	session.invalidate();
    	
    	return "redirect:/j_spring_security_logout";
    }
    
    @RequestMapping(value="/ResumeGame.html", method=RequestMethod.GET)
    public String getSavedMoves(@RequestParam Integer id, HttpSession session){
    	//if(session.getAttribute("username")!=null){
    		//if(id!=null){
    			String[] cell = new String[9];
    			List<String> movesList = new ArrayList<String>();
    			GameEntry gSavedMoves = gameDao.getSavedMoves(id);
    			Integer sessionGameId = id;
    			session.setAttribute("sessionGameId", sessionGameId);
    			movesList = gSavedMoves.getBoard();
    			for(int i=0; i<movesList.size(); i++){
    				if(movesList.get(i).length()==3){
    					cell[i] = movesList.get(i).substring(2);
    				}
    				else if(movesList.get(i).length()==2){
    					cell[i] = "";
    				}
    			}
    			//gSavedMoves.getBoard()
    			gSavedMoves.getBoard().clear();
    			GameUser user = gameDao.getGameUser(session.getAttribute("username").toString());
    			user.getSavedGames().remove(gSavedMoves);	
    			gameDao.saveUser(user);
    			
    			session.setAttribute("cell", cell);
    			session.setAttribute("gSavedMoves", gSavedMoves);
    			return "redirect:/ResumedBoard.html";
    		//}
    	/*}
    	else
    	{
    		return "redirect:/login.html";
    	}*/
    	//return "redirect:/ResumedBoard.html";
    }
    
    
    @RequestMapping(value="/ResumedBoard.html", method=RequestMethod.GET)
    public String displayResumedGamed(HttpSession session){
    	//if(session.getAttribute("username")!=null){
    	String[] cell = (String [])session.getAttribute("cell");
    	return "ResumedBoard";
    	/*}
    	else{
    		return "redirect:/login.html";
    	}*/
    }
    
    
    @RequestMapping(value="/DisplayInitGame.html", method=RequestMethod.GET)
    public String displayInit(@ModelAttribute("gStart") GameEntry gm, HttpSession session){
    	//if(session.getAttribute("username")!=null){
    		String []cell = new String[9];
    		GameEntry gStart = new GameEntry();
    		List<GameEntry> geList = new ArrayList<GameEntry>();
    		GameUser user = gameDao.getGameUser(session.getAttribute("username").toString());
    		//boolean hasBeenSaved = false;
    		//geList = gameDao.getAllGamesPlayed(user);
    		//user.setPlayedGames(playedGames);
    		//gameDao.savePlayedGames(geList, user.getUserId());
    		String message = null;
    		for(int i=0; i<=8; i++){
    			cell[i]="";
    		}
    		session.setAttribute("cell", cell);
    		session.setAttribute("message", message);
    		//session.setAttribute("hasBeenSaved", hasBeenSaved);
    		gStart.setStartTime(new Date());
    		gStart.setAgainstAI(true);
    		gStart.setPlayer1(user);
    		//user.getPlayedGames().add(gStart);
    		//gameDao.saveUser(user);
    		//gameDao.saveGameData(gStart);
    		session.setAttribute("gStart", gStart);
    		//session.setAttribute("geList", geList);
    		return "DisplayInitGame";
    	/*}
    	else{
    		return "redirect:/login.html";
    	}*/
    	
    }
    
    @RequestMapping(value="/newGame.html", method=RequestMethod.GET)
    public String newGame(@RequestParam Integer row, HttpSession session){
    	//if(session.getAttribute("username")!=null){
    	GameUser user = gameDao.getGameUser(session.getAttribute("username").toString());
    	
    	if(session.getAttribute("sessionGameId")==null && row==99){
    		GameEntry gStart = (GameEntry) session.getAttribute("gStart");
    		if(gStart==null){
    			return "redirect:/DisplayInitGame.html";
    		}
    		if(gStart.getGameState()==null){
    		gStart.setGameState(Outcome.AI_win);
    		gStart.setEndTime(new Date());
    		gameDao.saveGameData(gStart);
    		List<GameEntry> geList = gameDao.getAllGamesPlayed(user);
    		gameDao.savePlayedGames(geList, user.getUserId());
    		}
    	}
    	else if(session.getAttribute("sessionGameId") !=null && row==99){
    		GameEntry gStart = gameDao.getgamebyid((Integer)session.getAttribute("sessionGameId"));
    		if(gStart.getGameState().equals(Outcome.incomplete)){
    			
    			gStart.setGameState(Outcome.AI_win);
    			gStart.setSaveTime(null);
        		gStart.setEndTime(new Date());
        		
    		gameDao.saveGameData(gStart);
    		List<GameEntry> geList = gameDao.getAllGamesPlayed(user);
    		gameDao.savePlayedGames(geList, user.getUserId());
    		//user.getPlayedGames().add(gStart);
    		//session.removeAttribute("gStart");
    		return "redirect:/DisplayInitGame.html";
    		}
    	
    	}
    	session.removeAttribute("sessionGameId");
    	return "redirect:/DisplayInitGame.html";
    	/*}
    	else{
    		return "redirect:/login.html";
    	}*/
    }
    
    
    
    @RequestMapping(value="/GameHistory.html", method=RequestMethod.GET)
    public String getGameHistory(ModelMap models, HttpSession session){
    	//if(session.getAttribute("username")!=null){
    	List<GameEntry> historyList = new ArrayList<GameEntry>();
    	List<GameEntry> monthGames = new ArrayList<GameEntry>();
    	GameUser user = gameDao.getGameUser(session.getAttribute("username").toString());
    	historyList = gameDao.getGameHistory(user);
    	monthGames = gameDao.getGamesThisMonth(user);
    	int completedGames = 0;
    	int gamesAgainstAI = 0;
    	int twoPlayerGames = 0;
    	int winAgainstAI = 0;
    	int winPlayer1 = 0;
    	int winPlayer2 = 0;
    	double winRateAgainstAI = 0.00;
    	double winRateAgainstHuman = 0.00;
    	double gameLength = 0.00;
    	Calendar cal = Calendar.getInstance();
    	int month = cal.get(Calendar.MONTH);
    	int year = cal.get(Calendar.YEAR);
    	
    	List<ThisMonth> gMonth = new ArrayList<ThisMonth>();
    	for(int j=0; j<monthGames.size(); j++){
    		if((monthGames.get(j).getGameState()!=null)|| (!monthGames.get(j).getGameState().equals(Outcome.incomplete))){
    			Date startTime = monthGames.get(j).getStartTime();
        		Date endTime = monthGames.get(j).getEndTime();
        		long diff = endTime.getTime() - startTime.getTime();
        		int diffInDays = (int) diff/(1000*60*60*24);
        		long diffInHours = diff/(60*60*1000);
        		long diffInMinutes = diff/(1000*60)%60;
        		long diffInSeconds = diff/1000%60;
        		
        		String duration = diffInDays+" Days "+diffInHours+" Hours "+diffInMinutes+" Minutes "+diffInSeconds+" Seconds ";
        		if(monthGames.get(j).getPlayer2()==null){
        			ThisMonth tm = new ThisMonth(monthGames.get(j).getGameId(),"AI", monthGames.get(j).getGameState(), duration, null );
        			gMonth.add(tm);
        		}
        		else if(monthGames.get(j).getPlayer1().equals(user) && monthGames.get(j).getGameState().equals(Outcome.player1_win)){
        			ThisMonth tm = new ThisMonth(monthGames.get(j).getGameId(), monthGames.get(j).getPlayer2().getUsername(), monthGames.get(j).getGameState(), duration, "Win");
        			gMonth.add(tm);
        		}
        		else if(monthGames.get(j).getPlayer1().equals(user) && monthGames.get(j).getGameState().equals(Outcome.player2_win)){
        			ThisMonth tm = new ThisMonth(monthGames.get(j).getGameId(), monthGames.get(j).getPlayer2().getUsername(), monthGames.get(j).getGameState(), duration, "Lost");
        			gMonth.add(tm);
        		}
        		else if(monthGames.get(j).getPlayer2().equals(user) && monthGames.get(j).getGameState().equals(Outcome.player2_win)){
        			ThisMonth tm = new ThisMonth(monthGames.get(j).getGameId(), monthGames.get(j).getPlayer1().getUsername(), monthGames.get(j).getGameState(), duration, "Win");
        			gMonth.add(tm);
        		}
        		else if(monthGames.get(j).getPlayer2().equals(user) && monthGames.get(j).getGameState().equals(Outcome.player1_win)){
        			ThisMonth tm = new ThisMonth(monthGames.get(j).getGameId(), monthGames.get(j).getPlayer1().getUsername(), monthGames.get(j).getGameState(), duration, "Lost");
        			gMonth.add(tm);
        		}
        		else if(monthGames.get(j).getPlayer1().equals(user) && monthGames.get(j).getGameState().equals(Outcome.tie_games)){
        			ThisMonth tm = new ThisMonth(monthGames.get(j).getGameId(), monthGames.get(j).getPlayer2().getUsername(), monthGames.get(j).getGameState(), duration, "Tie");
        			gMonth.add(tm);
        		}
        		else if(monthGames.get(j).getPlayer2().equals(user) && monthGames.get(j).getGameState().equals(Outcome.tie_games)){
        			ThisMonth tm = new ThisMonth(monthGames.get(j).getGameId(), monthGames.get(j).getPlayer1().getUsername(), monthGames.get(j).getGameState(), duration, "Tie");
        			gMonth.add(tm);
        		}
        		
        		/*else{
        			ThisMonth tm = new ThisMonth(monthGames.get(j).getGameId(), monthGames.get(j).getPlayer2().getUsername(), monthGames.get(j).getGameState(), duration);
        			gMonth.add(tm);
        		}*/
        	}
    	}
    	models.addAttribute("gMonth", gMonth);
    	for(int i=0; i<historyList.size(); i++){
    		
    		if(historyList.get(i).getGameState()!=null){

    			if(!historyList.get(i).getGameState().equals(Outcome.incomplete)){
    				completedGames=completedGames+1;
    			}
    			if(historyList.get(i).isAgainstAI() && (!historyList.get(i).getGameState().equals(Outcome.incomplete))){
    				gamesAgainstAI = gamesAgainstAI + 1;
    				if(historyList.get(i).getGameState().equals(Outcome.player1_win)){
    					winAgainstAI = winAgainstAI + 1;
    				}
    			}
    			if(historyList.get(i).getPlayer1()!=null && historyList.get(i).getPlayer2()!=null){
    				if(historyList.get(i).getGameState()!=null && !(historyList.get(i).getGameState().equals(Outcome.incomplete)) && !(historyList.get(i).getGameState().equals(Outcome.AI_win))){
    					twoPlayerGames = twoPlayerGames + 1;
    					//get this logic checked
    					if(user.getUserId().equals(historyList.get(i).getPlayer1())){
    						if(historyList.get(i).getGameState().equals(Outcome.player1_win)){
    							winPlayer1 = winPlayer1 + 1;
    							//gMonth.get(i).setOpponent(historyList.get(i).get);
    						}
    					}
    					else if(user.getUserId().equals(historyList.get(i).getPlayer2())){
    						if(historyList.get(i).getGameState().equals(Outcome.player2_win)){
    							winPlayer2 = winPlayer2 + 1;
    						}
    					}
    				}
    			}
    		}
    		/*if(month==endTime.get(Calendar.MONTH) && (year==endTime.get(Calendar.YEAR))){
    			long diffInMillies = endDate.getTime() - startDate.getTime();
    			TimeUnit tu = TimeUnit.MINUTES;
    			
    			gameLength=tu.convert(diffInMillies, TimeUnit.MILLISECONDS);
    		}*/
    	}
    	
    	//check to prevent Zero Division Error
    	if(gamesAgainstAI != 0){
    		winRateAgainstAI = ((double)winAgainstAI/gamesAgainstAI)*100;
    	}
    	else{
    		winRateAgainstAI = 0;
    	}
    	if(twoPlayerGames != 0){
    		winRateAgainstHuman = ((double)(winPlayer1+winPlayer2)/twoPlayerGames)*100;
    	}
    	else{
    		winRateAgainstHuman = 0;
    	}
    	
    	DecimalFormat df = new DecimalFormat("###.##");
    	
    	models.addAttribute("completedGames", completedGames);
    	models.addAttribute("gamesAgainstAI", gamesAgainstAI);
    	models.addAttribute("twoPlayerGames", twoPlayerGames);
    	models.addAttribute("winRateAgainstAI",df.format(winRateAgainstAI));
    	models.addAttribute("winRateAgainstHuman", df.format(winRateAgainstHuman));
    	models.addAttribute("gameLength",gameLength);
    	return "GameHistory";
    /*}
	
    else{
    	return "redirect:/login.html";
    }*/
    }
        
    @RequestMapping(value="/DisplayGame.html", method=RequestMethod.GET)
    public String display(ModelMap models, @RequestParam Integer row, HttpSession session){
    	//if(session.getAttribute("username")!=null){
    	String message = null;
    	//List<GameEntry> gList = new ArrayList<GameEntry>();
    	//List<String> bList = new ArrayList<String>();
    	//String element = index +":"+ "X";
    	//GameEntry gStart = new GameEntry();
    	GameEntry gStart = (GameEntry) session.getAttribute("gStart");
    	if(session.getAttribute("username")!=null){
    	String cell[] = (String[]) session.getAttribute("cell");
    	if(cell[row]==null || cell[row].isEmpty()){
    		//String element = row +":"+ "X";
    		cell[row]="X";
    		//bList.add(element);
        	
    	}
    	if(message==null){
    		//gStart.setStartTime(new Date());
    		if(cell[0].contains("X") && cell[1].contains("X") && cell[2].contains("X")){
				message = "You won!";
			}
			else if(cell[3].contains("X") && cell[4].contains("X") && cell[5].contains("X")){
				message = "You won!";
			}
			else if(cell[6].contains("X") && cell[7].contains("X") && cell[8].contains("X")){
				message = "You won!";
			}
			else if(cell[0].contains("X") && cell[3].contains("X") && cell[6].contains("X")){
				message = "You won!";
			}
			else if(cell[1].contains("X") && cell[4].contains("X") && cell[7].contains("X")){
				message = "You won!";
			}
			else if(cell[2].contains("X") && cell[5].contains("X") && cell[8].contains("X")){
				message = "You won!";
			}
			else if(cell[0].contains("X") && cell[4].contains("X") && cell[8].contains("X")){
				message = "You won!";
			}
			else if(cell[2].contains("X") && cell[4].contains("X") && cell[6].contains("X")){
				message = "You won!";
			}
		
		// start logic for making the winning move
		// first row
			else if(cell[0].contains("O") && cell[1].contains("O") && (cell[2].isEmpty() || cell[2] == null)){
			//if(cell[2].isEmpty() || cell[2] == null){
				cell[2] = "O";
				message = "I won!";
			//}
		}
		else if(cell[1].contains("O") && cell[2].contains("O") && (cell[0].isEmpty() || cell[0] == null)){
			//if(cell[0].isEmpty() || cell[0] == null){
				cell[0] = "O";
				message = "I won!";
			//}
		}
		else if(cell[0].contains("O") && cell[2].contains("O") && (cell[1].isEmpty() || cell[1] == null)){
			//if(cell[1].isEmpty() || cell[1] == null){
				cell[1] = "O";
				message = "I won!";
			//}
		}
		// end first row
		// start second row
		else if(cell[3].contains("O") && cell[4].contains("O") && (cell[5].isEmpty() || cell[5] == null)){
			//if(cell[5].isEmpty() || cell[5] == null){
				cell[5] = "O";
				message = "I won!";
			//}
		}
		else if(cell[4].contains("O") && cell[5].contains("O") && (cell[3].isEmpty() || cell[3] == null)){
			//if(cell[3].isEmpty() || cell[3] == null){
				cell[3] = "O";
				message = "I won!";
			//}
		}
		else if(cell[5].contains("O") && cell[3].contains("O") && (cell[4].isEmpty() || cell[4] == null)){
			//if(cell[4].isEmpty() || cell[4] == null){
				cell[4] = "O";
				message = "I won!";
			//}
		}
		// end second row
		// start third row
		else if(cell[6].contains("O") && cell[7].contains("O") && (cell[8].isEmpty() || cell[8] == null)){
			//if(cell[8].isEmpty() || cell[8] == null){
				cell[8] = "O";
				message = "I won!";
			//}
		}
		else if(cell[7].contains("O") && cell[8].contains("O") && (cell[6].isEmpty() || cell[6] == null)){
			//if(cell[6].isEmpty() || cell[6] == null){
				cell[6] = "O";
				message = "I won!";
			//}
		}
		else if(cell[8].contains("O") && cell[6].contains("O") && (cell[7].isEmpty() || cell[7] == null)){
			//if(cell[7].isEmpty() || cell[7] == null){
				cell[7] = "O";
				message = "I won!";
			//}
		}
		// end third row
		// start first column
		else if(cell[0].contains("O") && cell[3].contains("O") && (cell[6].isEmpty() || cell[6] == null)){
			//if(cell[6].isEmpty() || cell[6] == null){
				cell[6] = "O";
				message = "I won!";
			//}
		}
		else if(cell[3].contains("O") && cell[6].contains("O") && (cell[0].isEmpty() || cell[0] == null)){
			//if(cell[0].isEmpty() || cell[0] == null){
				cell[0] = "O";
				message = "I won!";
			//}
		}
		else if(cell[6].contains("O") && cell[0].contains("O") && (cell[3].isEmpty() || cell[3] == null)){
			//if(cell[3].isEmpty() || cell[3] == null){
				cell[3] = "O";
				message = "I won!";
			//}
		}
		// end first column
		// start second column
		else if(cell[1].contains("O") && cell[4].contains("O") && (cell[7].isEmpty() || cell[7] == null)){
			//if(cell[7].isEmpty() || cell[7] == null){
				cell[7] = "O";
				message = "I won!";
			//}
		}
		else if(cell[4].contains("O") && cell[7].contains("O") && (cell[1].isEmpty() || cell[1] == null)){
			//if(cell[1].isEmpty() || cell[1] == null){
				cell[1] = "O";
				message = "I won!";
			//}
		}
		else if(cell[7].contains("O") && cell[1].contains("O") && (cell[4].isEmpty() || cell[4] == null)){
			//if(cell[4].isEmpty() || cell[4] == null){
				cell[4] = "O";
				message = "I won!";
			//}
		}
		// end second column
		// start third column
		else if(cell[2].contains("O") && cell[5].contains("O") && (cell[8].isEmpty() || cell[8] == null)){
			//if(cell[8].isEmpty() || cell[8] == null){
				cell[8] = "O";
				message = "I won!";
			//}
		}
		else if(cell[5].contains("O") && cell[8].contains("O") && (cell[2].isEmpty() || cell[2] == null)){
			//if(cell[2].isEmpty() || cell[2] == null){
				cell[2] = "O";
				message = "I won!";
			//}
		}
		else if(cell[8].contains("O") && cell[2].contains("O") && (cell[5].isEmpty() || cell[5] == null)){
			//if(cell[5].isEmpty() || cell[5] == null){
				cell[5] = "O";
				message = "I won!";
			//}
		}
		// end third column
		// start first diagonal
		else if(cell[0].contains("O") && cell[4].contains("O") && (cell[8].isEmpty() || cell[8] == null)){
			//if(cell[8].isEmpty() || cell[8] == null){
				cell[8] = "O";
				message = "I won!";
			//}
		}
		else if(cell[4].contains("O") && cell[8].contains("O") && (cell[0].isEmpty() || cell[0] == null)){
			//if(cell[0].isEmpty() || cell[0] == null){
				cell[0] = "O";
				message = "I won!";
			//}
		}
		else if(cell[0].contains("O") && cell[8].contains("O") && (cell[4].isEmpty() || cell[4] == null)){
			//if(cell[4].isEmpty() || cell[4] == null){
				cell[4] = "O";
				message = "I won!";
			//}
		}
		// end first diagonal
		// start second diagonal
		else if(cell[2].contains("O") && cell[4].contains("O") && (cell[6].isEmpty() || cell[6] == null)){
			//if(cell[6].isEmpty() || cell[6] == null){
				cell[6] = "O";
				message = "I won!";
			//}
		}
		else if(cell[4].contains("O") && cell[6].contains("O") && (cell[2].isEmpty() || cell[2] == null)){
			//if(cell[2].isEmpty() || cell[2] == null){
				cell[2] = "O";
				message = "I won!";
			//}
		}
		else if(cell[2].contains("O") && cell[6].contains("O") && (cell[4].isEmpty() || cell[4] == null)){
			//if(cell[4].isEmpty() || cell[4] == null){
				cell[4] = "O";
				message = "I won!";
			//}
		}
		// end second diagonal
		// end logic for making the winning move
		
		// logic for blocking the winning move
		// checking for first row (0,1,2)
		else if(cell[0].contains("X") && cell[1].contains("X") && (cell[2].isEmpty() || cell[2] == null)){
			//if(cell[2].isEmpty() || cell[2] == null){
				cell[2] = "O";
			//}
		}
		else if(cell[1].contains("X") && cell[2].contains("X") && (cell[0].isEmpty() || cell[0] == null)){
			//if(cell[0].isEmpty() || cell[0] == null){
				cell[0] = "O";
			//}
		}
		else if(cell[0].contains("X") && cell[2].contains("X") && (cell[1].isEmpty() || cell[1] == null)){
			//if(cell[1].isEmpty() || cell[1] == null){
				cell[1] = "O";
			//}
		}
		// end first row
		// start for second row (3,4,5) 
		else if(cell[3].contains("X") && cell[4].contains("X") && (cell[5].isEmpty() || cell[5] == null)){
			//if(cell[5].isEmpty() || cell[5] == null){
				cell[5] = "O";
			//}
		}
		else if(cell[4].contains("X") && cell[5].contains("X") && (cell[3].isEmpty() || cell[3] == null)){
			//if(cell[3].isEmpty() || cell[3] == null){
				cell[3] = "O";
			//}
		}
		else if(cell[5].contains("X") && cell[3].contains("X") && (cell[4].isEmpty() || cell[4] == null)){
			//if(cell[4].isEmpty() || cell[4] == null){
				cell[4] = "O";
			//}
		}
		// end second row
		// start for third row (6,7,8) 
		else if(cell[6].contains("X") && cell[7].contains("X") && (cell[8].isEmpty() || cell[8] == null)){
			//if(cell[8].isEmpty() || cell[8] == null){
				cell[8] = "O";
			//}
		}
		else if(cell[7].contains("X") && cell[8].contains("X") && (cell[6].isEmpty() || cell[6] == null)){
			//if(cell[6].isEmpty() || cell[6] == null){
				cell[6] = "O";
			//}
		}
		else if(cell[8].contains("X") && cell[6].contains("X") && (cell[7].isEmpty() || cell[7] == null)){
			//if(cell[7].isEmpty() || cell[7] == null){
				cell[7] = "O";
			//}
		}
		// end third row
		// start for first column (0,3,6)
		else if(cell[0].contains("X") && cell[3].contains("X") && (cell[6].isEmpty() || cell[6] == null)){
			//if(cell[6].isEmpty() || cell[6] == null){
				cell[6] = "O";
			//}
		}
		else if(cell[3].contains("X") && cell[6].contains("X") && (cell[0].isEmpty() || cell[0] == null)){
			//if(cell[0].isEmpty() || cell[0] == null){
				cell[0] = "O";
			//}
		}
		else if(cell[6].contains("X") && cell[0].contains("X") && (cell[3].isEmpty() || cell[3] == null)){
			//if(cell[3].isEmpty() || cell[3] == null){
				cell[3] = "O";
			//}
		}
		// end first column
		// start for second column (1,4,7)
		else if(cell[1].contains("X") && cell[4].contains("X") && (cell[7].isEmpty() || cell[7] == null)){
			//if(cell[7].isEmpty() || cell[7] == null){
				cell[7] = "O";
			//}
		}
		else if(cell[4].contains("X") && cell[7].contains("X") && (cell[1].isEmpty() || cell[1] == null)){
			//if(cell[1].isEmpty() || cell[1] == null){
				cell[1] = "O";
			//}
		}
		else if(cell[7].contains("X") && cell[1].contains("X") && (cell[4].isEmpty() || cell[4] == null)){
			//if(cell[4].isEmpty() || cell[4] == null){
				cell[4] = "O";
			//}
		}
		// end second column
		// start third column (2,5,8)
		else if(cell[2].contains("X") && cell[5].contains("X") && (cell[8].isEmpty() || cell[8] == null)){
			//if(cell[8].isEmpty() || cell[8] == null){
				cell[8] = "O";
			//}
		}
		else if(cell[5].contains("X") && cell[8].contains("X") && (cell[2].isEmpty() || cell[2] == null)){
			//if(cell[2].isEmpty() || cell[2] == null){
				cell[2] = "O";
			//}
		}
		else if(cell[8].contains("X") && cell[2].contains("X") && (cell[5].isEmpty() || cell[5] == null)){
			//if(cell[5].isEmpty() || cell[5] == null){
				cell[5] = "O";
			//}
		}
		// end third column
		// start first diagonal
		else if(cell[0].contains("X") && cell[4].contains("X") && (cell[8].isEmpty() || cell[8] == null)){
			//if(cell[8].isEmpty() || cell[8] == null){
				cell[8] = "O";
			//}
		}
		else if(cell[4].contains("X") && cell[8].contains("X") && (cell[0].isEmpty() || cell[0] == null)){
			//if(cell[0].isEmpty() || cell[0] == null){
				cell[0] = "O";
			//}
		}
		else if(cell[0].contains("X") && cell[8].contains("X") && (cell[4].isEmpty() || cell[4] == null)){
			//if(cell[4].isEmpty() || cell[4] == null){
				cell[4] = "O";
			//}
		}
		// end first diagonal
		// start second diagonal
		else if(cell[2].contains("X") && cell[4].contains("X") && (cell[6].isEmpty() || cell[6] == null)){
			//if(cell[6].isEmpty() || cell[6] == null){
				cell[6] = "O";
			//}
		}
		else if(cell[4].contains("X") && cell[6].contains("X") && (cell[2].isEmpty() || cell[2] == null)){
			//if(cell[2].isEmpty() || cell[2] == null){
				cell[2] = "O";
			//}
		}
		else if(cell[2].contains("X") && cell[6].contains("X") && (cell[4].isEmpty() || cell[4] == null)){
			//if(cell[4].isEmpty() || cell[4] == null){
				cell[4] = "O";
			//}
		}
		// end second diagonal 
		// blocking winning move logic ends
		// generate a random number
		else {
			List<Integer> temp= new ArrayList<Integer>() ;
			for(int i=0; i<cell.length; i++){
				if(cell[i].isEmpty()){
					temp.add(i);
				}
			}
			Random ran = new Random();
			if(temp.size()!=0){
			cell[temp.get(ran.nextInt(temp.size()))] = "O";
			}
			else if(temp.size()==0){
				message = "Game tied!";
			}
		}

    	}
    	
    	if(message!=null){
    		GameEntry gEnd = new GameEntry();
    		if(session.getAttribute("sessionGameId") !=null)
    		{
    			gEnd = gameDao.getgamebyid((Integer)session.getAttribute("sessionGameId"));
    		}
    		else
    		{
    			gEnd = (GameEntry)session.getAttribute("gStart");
    		}
    		System.out.println("Before message is null:"+gStart);
    		if(message.equalsIgnoreCase("I won!")){
    			gEnd.setGameState(Outcome.AI_win);
    		}
    		else if(message.equalsIgnoreCase("You won!")){
    			gEnd.setGameState(Outcome.player1_win);
    		}
    		else if(message.equalsIgnoreCase("Game tied!")){
    			gEnd.setGameState(Outcome.tie_games);
    		}
    		
    		gEnd.setEndTime(new Date());
    		gEnd.setSaveTime(null);
    		GameUser user = gameDao.getGameUser(session.getAttribute("username").toString());
    		//boolean hasBeenSaved = (boolean) session.getAttribute("hasBeenSaved");
    	//	user.getPlayedGames().add(gEnd);
    		
    		//if(!hasBeenSaved){
    			//System.out.println("Not been Saved :"+hasBeenSaved);
    			if(session.getAttribute("sessionGameId") !=null)
    			{
    				gameDao.saveGameAgain((Integer)session.getAttribute("sessionGameId"));
    			}
    			else
    			{
    				gameDao.saveGameData(gEnd);
    			}
    			List<GameEntry> geList = gameDao.getAllGamesPlayed(user);
    			gameDao.savePlayedGames(geList, user.getUserId());
    			
    		//}
    		//else{
    		//user.getPlayedGames().add(gStart);
    		//System.out.println("P G Size: "+user.getPlayedGames().size());
    		//List<GameEntry> g = gameDao.getAllGamesPlayed(user);
    		/*List<GameEntry> g = user.getPlayedGames();
    		System.out.println("g.SIZE :"+g.size());
    		
    		for(int i=0;i<g.size();i++){
    			//int x = g.get(i).getStartTime().compareTo(gSave.getStartTime())
    			if((g.get(i).getStartTime().compareTo(gEnd.getStartTime()))==0){
    				//g.get(i).setBoard(boardList);
    				g.get(i).setGameState(gEnd.getGameState());
    				System.out.println("G ID for saving after resume:"+(Integer)session.getAttribute("sessionGameId"));
    				gameDao.saveGameAgain((Integer)session.getAttribute("sessionGameId"));
    				//g.get(i).setSaveTime(new Date());
    				//user.getPlayedGames().remove(g.get(i));
    				//user.getPlayedGames().add(g.get(i));
    				//gameDao.saveUser(user);
    			}
    		}
    		}*/
    		//gameDao.saveUser(user);
    		session.removeAttribute("sessionGameId");
    		//session.removeAttribute("hasBeenSaved");
    		//gameDao.saveGameData(gStart);
    		System.out.println("After Merge : "+gEnd);
    		System.out.println("Over"+user);
    	}
    	/*if(session.getAttribute("username")!=null){
    		models.put("gameBoard", new GameEntry());
        	return "DisplayGame";
    	}
    	else{
    		return "redirect:/login.html";
    	}*/
    	}
    	else{
    		return "redirect:/login.html";
    	}
    	
    	session.setAttribute("message", message);
    	return "DisplayGame";
    /*}
    
    else{
     return "redirect:/login.html";		
    }*/
    }
    
    //private static final Logger logger = LoggerFactory.getLogger( WhosOnlineController.class );

    @RequestMapping("/whosonline.json")
    public String wosJson( ModelMap models, HttpSession session )
    {
        //logger.debug( "Request received for JSON" );
    	//GameUser onlineUser = new GameUser(); 
    	//onlineUser = gameDao.getGameUser(session.getAttribute("username").toString());
    	
    	//wos.add(onlineUser.getUsername());
        models.put( "username", wos.getUsernames());
        return "jsonView";
    }

    @RequestMapping("/whosonline.deferred.json")
    @ResponseBody
    public DeferredResult<String> wosDeferred(HttpSession session)
    {
        DeferredResult<String> result = new DeferredResult<String>();
        
        wos.addResult( result );
        return result;
    }

    @RequestMapping("/whosonline3.html")
    public String wos3(ModelMap models, HttpSession session)
    {
    	//GameUser username = gameDao.getGameUser(session.getAttribute("username").toString());
    	//models.put( "username", username.getUsername() );
        return "whosonline3";
    }
    
    @RequestMapping("/TwoPlayerMenu.html")
    public String getTwoPlayerMenu(ModelMap models, HttpSession session)
    {
    	//GameUser username = gameDao.getGameUser(session.getAttribute("username").toString());
    	//models.put( "username", username.getUsername() );
        return "TwoPlayerMenu";
    }
    
    @RequestMapping(value="/HostGame.html", method=RequestMethod.GET)
    public String hostTwoPlayerGame(ModelMap models, HttpSession session){
    	//if(session.getAttribute("username")!=null){
    		GameUser hostUser = gameDao.getGameUser(session.getAttribute("username").toString());
    		if(hostUser.getUsername()!=null){
    			String onlineHostUser = hostUser.getUsername();
    			models.put("hostUser", hostUser.getUsername());
    			//hos.add(onlineHostUser);
				//tgos.addHostedUsers(hos);
    			//hos.add(hostUser.getUsername());
    			
    			if(jos.getUsernames().size()==0){
    				if(!hos.getUsernames().contains(onlineHostUser)){
    					hos.add(onlineHostUser);
    					//tgos.addHostedUsers(onlineHostUser);
    				}
    				String twoPlayerGameMessage = "Waiting for another player to join the game ...";
    				models.put("twoPlayerGameMessage", twoPlayerGameMessage);
    			}
    			else{
    				//int randomJoin = (int) (Math.random()*100%jos.getUsernames().size());
    				String joiningPlayer = jos.getUsernames().get(0);
    				GameUser jUser = gameDao.getGameUser(joiningPlayer);
    				String[] multiCells = new String[9];
    				TwoPlayerGames currentJoinGame = new TwoPlayerGames(onlineHostUser+"&"+joiningPlayer, Outcome.incomplete, new Date(), multiCells);
    				/*if(mpos.getMultiPlayerGames().size()==0){
    					currentJoinGame = new GameEntry();
    				}
    				else{
    					currentJoinGame = mpos.getMultiPlayerGames().get(0);
    				}*/
    				if(hos.getUsernames().contains(onlineHostUser)){
    					hos.remove(onlineHostUser);
    					//tgos.removeHostedUsers(onlineHostUser);
    				}
    				/*if(!tgos.getHostedUsernames().contains(onlineHostUser)){
    					tgos.addHostedUsers(onlineHostUser);
    					
    					session.setAttribute("hostsession", onlineHostUser);
    				}*/
    				if(jos.getUsernames().contains(joiningPlayer)){
    					jos.remove(joiningPlayer);
    				}
    				/*if(!currentJoinGame.getPlayer2().getUsername().contains(joiningPlayer)){
    					currentJoinGame.setPlayer2(jUser);
    					mpos.addMultiPlayerGames(currentJoinGame);
    				}*/
    				if(!mpos.getMultiPlayerGames().contains(currentJoinGame)){
    					mpos.add(currentJoinGame);
    				}
    				/*if(!tgos.getJoinedUsernames().contains(joiningPlayer)){
    					tgos.addJoinedUsers(joiningPlayer);
    					session.setAttribute("joinsession", joiningPlayer);
    				}*/
    				
    			}
    			/*if(jos.getUsernames().size()==0){
    				String waitMessage = "Waiting for another player to join the game ...";
    				models.addAttribute("waitMessage", waitMessage);
    				if(!hos.getUsernames().contains(onlineHostUser)){
    					hos.add(onlineHostUser);
    					tgos.addHostedUsers(hos);
    				}
    			}
    			else{
    				String joiningPlayer = jos.getUsernames().get(0);
    				if(hos.getUsernames().contains(onlineHostUser)){
    					hos.remove(onlineHostUser);
    				}
    				if(jos.getUsernames().contains(joiningPlayer)){
    					jos.remove(joiningPlayer);
    				}
    				String joinMessage = joiningPlayer+", has joined the Game. Please make your move.";
    				models.addAttribute("joinMessage", joinMessage);
    			}*/
    		}
    		return "TwoPlayerGame";
    	/*}
    	else{
    		return "redirect:/login.html";
    	}*/
    }
    
    @RequestMapping("/hostsonline.json")
    public String hosJson( ModelMap models, HttpSession session )
    {
        //logger.debug( "Request received for JSON" );
    	//GameUser onlineUser = new GameUser(); 
    	//onlineUser = gameDao.getGameUser(session.getAttribute("username").toString());
    	
    	//wos.add(onlineUser.getUsername());
        models.put( "hostusernames", hos.getUsernames());
        return "jsonView";
    }

    @RequestMapping("/hostsonline.deferred.json")
    @ResponseBody
    public DeferredResult<String> hosDeferred(HttpSession session)
    {
        DeferredResult<String> result = new DeferredResult<String>();
        
        hos.addResult( result );
        return result;
    }

    
    @RequestMapping(value="/JoinGame.html", method=RequestMethod.GET)
    public String joinTwoPlayerGame(ModelMap models, HttpSession session){
    	//if(session.getAttribute("username")!=null){
    		GameUser joinedUser = gameDao.getGameUser(session.getAttribute("username").toString());
    		if(joinedUser.getUsername()!=null){
    			String onlineJoinedUser = joinedUser.getUsername();
    			models.put("joinedUser", joinedUser.getUsername());
    			//jos.add(onlineJoinedUser);
				//tgos.addJoinedUsers(jos);
    			//jos.add(joinedUser.getUsername());
    			
    			
    			if(hos.getUsernames().size()==0){
    				if(!jos.getUsernames().contains(onlineJoinedUser)){
    					jos.add(onlineJoinedUser);
    					//tgos.addJoinedUsers(onlineJoinedUser);
    					
    				}
    				String twoPlayerGameMessage = "Waiting for another player to host a game ...";
    				models.put("twoPlayerGameMessage", twoPlayerGameMessage);
    			}
    			else{
    				//int randomHost = (int) (Math.random()*100%hos.getUsernames().size());
    				String hostPlayer = hos.getUsernames().get(0);
    				GameUser hUser = gameDao.getGameUser(hostPlayer);
    				String[] multiCells = new String[9];
    				TwoPlayerGames currentHostGame = new TwoPlayerGames(hostPlayer+"&"+onlineJoinedUser, Outcome.incomplete, new Date(),multiCells);
    				
    				//HGame = gameDao.getgamebyid(gid)
    				//GameEntry currentHostGame = mpos.getMultiPlayerGames().get(0);
    				if(jos.getUsernames().contains(onlineJoinedUser)){
    					jos.remove(onlineJoinedUser);
    					//tgos.addJoinedUsers(onlineJoinedUser);
    				}
    				if(hos.getUsernames().contains(hostPlayer)){
    					hos.remove(hostPlayer);
    					//tgos.addHostedUsers(hostPlayer);
    				}
    				if(!mpos.getMultiPlayerGames().contains(currentHostGame)){
    					mpos.add(currentHostGame);
    				}
    				/*if(!tgos.getHostedUsernames().contains(hostPlayer)){
    					tgos.addHostedUsers(hostPlayer);
    					session.setAttribute("hostsession", hostPlayer);
    				}
    				if(!tgos.getJoinedUsernames().contains(onlineJoinedUser)){
    					tgos.addJoinedUsers(onlineJoinedUser);
    					session.setAttribute("joinsession", onlineJoinedUser);
    				}*/
    				
    				/*if(!HGame.getPlayer1().getUsername().contains(hostPlayer)){
    					HGame.setPlayer1(hUser);
    					HGame.setStartTime(new Date());
    					mpos.addMultiPlayerGames(currentHostGame);
    					gameDao.saveGameAgain(HGame.getGameId());
    				}*/
    				
    				
    			}
    			
    			
    			/*if(hos.getUsernames().size()==0){
    				String waitMessage = "Waiting for another player to host the game ...";
    				models.addAttribute("waitMessage", waitMessage);
    				if(!jos.getUsernames().contains(onlineJoinedUser)){
    					jos.add(onlineJoinedUser);
    					tgos.addJoinedUsers(jos);
    				}
    			}
    			else{
    				String hostPlayer = hos.getUsernames().get(0);
    				if(jos.getUsernames().contains(onlineJoinedUser)){
    					jos.remove(onlineJoinedUser);
    				}
    				if(hos.getUsernames().contains(hostPlayer)){
    					hos.remove(hostPlayer);
    				}
    				
    			}*/
    			//hos.remove(hos.getUsernames().get(0));
    			//session.setAttribute("currentUser", session.getAttribute("username").toString());
    		}
    		return "TwoPlayerGame";
    	/*}
    	else{
    		return "redirect:/login.html";
    	}*/
    }
    
    @RequestMapping("/joinonline.json")
    public String josJson( ModelMap models, HttpSession session )
    {
        //logger.debug( "Request received for JSON" );
    	//GameUser onlineUser = new GameUser(); 
    	//onlineUser = gameDao.getGameUser(session.getAttribute("username").toString());
    	
    	//wos.add(onlineUser.getUsername());
        models.put( "joinedusernames", jos.getUsernames());
        return "jsonView";
    }

    @RequestMapping("/joinonline.deferred.json")
    @ResponseBody
    public DeferredResult<String> josDeferred(HttpSession session)
    {
        DeferredResult<String> result = new DeferredResult<String>();
        
        jos.addResult( result );
        return result;
    }
    
    @RequestMapping("/twoGameOnline.json")
    public String twoGameJson( ModelMap models, HttpSession session )
    {
        //logger.debug( "Request received for JSON" );
    	//GameUser onlineUser = new GameUser(); 
    	//onlineUser = gameDao.getGameUser(session.getAttribute("username").toString());
    	
    	//wos.add(onlineUser.getUsername());
    	//tgos = new TwoGameOnlineService(hos.getUsernames(), jos.getUsernames());
    	//TwoGameOnlineService game2 = new TwoGameOnlineService(hos.getUsernames(), jos.getUsernames());
        models.put( "twoGameOnlineService", mpos.getMultiPlayerGames());
        //TwoPlayerGames tw = new TwoPlayerGames();
        
        //models.put( "twoGamePlayersHost", hos);
        return "jsonView";
    }
    
    @RequestMapping("/twoGameOnline.deferred.json")
    @ResponseBody
    public DeferredResult<List<TwoPlayerGames>> gosDeferred(HttpSession session)
    {
        DeferredResult<List<TwoPlayerGames>> result = new DeferredResult<List<TwoPlayerGames>>();
        
        mpos.addResult(result);
        return result;
    }
    
    @RequestMapping(value="/multiPlayerGame.html", method=RequestMethod.GET)
    public String multiPlayerGameBoard(@RequestParam Integer id, HttpSession session, ModelMap models){
    	//if(session.getAttribute("username")!=null){
    		for(int j=0; j<mpos.getMultiPlayerGames().size(); j++){
    			if(mpos.getMultiPlayerGames().get(j).getGameBetween().contains(session.getAttribute("username").toString())){
    				int gbLength = mpos.getMultiPlayerGames().get(j).getGameBetween().length();
    				int splitIndex = mpos.getMultiPlayerGames().get(j).getGameBetween().indexOf("&");
    				String player1 = mpos.getMultiPlayerGames().get(j).getGameBetween().substring(0,splitIndex);
    				String player2 = mpos.getMultiPlayerGames().get(j).getGameBetween().substring(splitIndex+1, gbLength);
    				String[] multiCells = mpos.getMultiPlayerGames().get(j).getCellBoxes(); 
    				if(mpos.getMultiPlayerGames().get(j).getGameResult().equals(Outcome.incomplete)){
    					if(player1.equalsIgnoreCase(session.getAttribute("username").toString())){
        					if(multiCells[id]==null){
        						multiCells[id] = "X";
        						//mpos.getMultiPlayerGames().remove(index)
        						mpos.getMultiPlayerGames().get(j).setCellBoxes(multiCells);
        						mpos.updateResults();
        						models.put("turn", "p2");
        						models.put("enable", false);
        					}
        				}
        				else if(player2.equalsIgnoreCase(session.getAttribute("username").toString())){
        					if(multiCells[id]==null){
        						multiCells[id]="O";
        						mpos.getMultiPlayerGames().get(j).setCellBoxes(multiCells);
        						mpos.updateResults();
        						models.put("turn", "p1");
        					}
        				}
    					String r1 = "";
						String r2 = "";
						String r3 = "";
						String c1 = "";
						String c2 = "";
						String c3 = "";
						String d1 = "";
						String d2 = "";
						
    					for(int k=0; k<9;k++){
    						if(k>=0 && k<3){
    							if(multiCells[k]!=null){
    								r1 = r1.concat(multiCells[k]);
    							}
    							if(k%3==0){
    								if(multiCells[k]!=null){
    									c1 = c1.concat(multiCells[k]);
    									d1 = d1.concat(multiCells[k]);
    								}
    							}
    							else if(k%3==1){
    								if(multiCells[k]!=null)
    								c2 = c2.concat(multiCells[k]);
    							}
    							else if(k%3==2){
    								if(multiCells[k]!=null){
        								c3 = c3.concat(multiCells[k]);
        								d2 = d2.concat(multiCells[k]);
    								}
    							}
    							/*if(r1.length()==3){
    								if(r1.equalsIgnoreCase("XXX")){
    									mpos.getMultiPlayerGames().get(j).setGameResult(Outcome.player1_win);
    								}
    								else if(r1.equalsIgnoreCase("OOO")){
    									mpos.getMultiPlayerGames().get(j).setGameResult(Outcome.player2_win);
    								}
    							}*/
    						}
    						if(k>=3 && k<6){
    							if(multiCells[k]!=null){
    								r2 = r2.concat(multiCells[k]);
    							}
    							if(k%3==0){
    								if(multiCells[k]!=null)
    								c1 = c1.concat(multiCells[k]);
    							}
    							else if(k%3==1){
    								if(multiCells[k]!=null){
    									c2 = c2.concat(multiCells[k]);
    									d1 = d1.concat(multiCells[k]);
    									d2 = d2.concat(multiCells[k]);
    								}
    							}
    							else if(k%3==2){
    								if(multiCells[k]!=null)
        								c3 = c3.concat(multiCells[k]);
    							}
    							/*if(r2.length()==3){
    								if(r2.equalsIgnoreCase("XXX")){
    									mpos.getMultiPlayerGames().get(j).setGameResult(Outcome.player1_win);
    								}
    								else if(r2.equalsIgnoreCase("OOO")){
    									mpos.getMultiPlayerGames().get(j).setGameResult(Outcome.player2_win);
    								}
    							}*/
    						}
    						if(k>=6 && k<9){
    							if(multiCells[k]!=null){
    								r3 = r3.concat(multiCells[k]);
    							}
    							if(k%3==0){
    								if(multiCells[k]!=null){
    									c1 = c1.concat(multiCells[k]);
    									d2 = d2.concat(multiCells[k]);
    								}
    							}
    							else if(k%3==1){
    								if(multiCells[k]!=null)
    								c2 = c2.concat(multiCells[k]);
    							}
    							else if(k%3==2){
    								if(multiCells[k]!=null){
        								c3 = c3.concat(multiCells[k]);
        								d1 = d1.concat(multiCells[k]);
    								}
    							}
    							/*if(r3.length()==3){
    								if(r3.equalsIgnoreCase("XXX")){
    									mpos.getMultiPlayerGames().get(j).setGameResult(Outcome.player1_win);
    								}
    								else if(r3.equalsIgnoreCase("OOO")){
    									mpos.getMultiPlayerGames().get(j).setGameResult(Outcome.player2_win);
    								}
    							}*/
    						}
    					}
    					if(r1.equalsIgnoreCase("XXX")||c1.equalsIgnoreCase("XXX")||c2.equalsIgnoreCase("XXX")||c3.equalsIgnoreCase("XXX")||r2.equalsIgnoreCase("XXX")||r3.equalsIgnoreCase("XXX")||d1.equalsIgnoreCase("XXX")||d2.equalsIgnoreCase("XXX")){
    						mpos.getMultiPlayerGames().get(j).setGameResult(Outcome.player1_win);
    						System.out.println("Player1 win:::::::"+d1);
    						mpos.updateResults();
    						GameEntry twoGame = new GameEntry();
    						twoGame.setPlayer1(gameDao.getGameUser(player1));
    						twoGame.setPlayer2(gameDao.getGameUser(player2));
    						twoGame.setAgainstAI(false);
    						twoGame.setGameState(Outcome.player1_win);
    						twoGame.setStartTime(mpos.getMultiPlayerGames().get(j).getMultiStartTime());
    						twoGame.setEndTime(new Date());
    						gameDao.saveGameData(twoGame);
    						break;
    					}
    					else if(r1.equalsIgnoreCase("OOO")||c1.equalsIgnoreCase("OOO")||c2.equalsIgnoreCase("OOO")||c3.equalsIgnoreCase("OOO")||r2.equalsIgnoreCase("OOO")||r3.equalsIgnoreCase("OOO")||d1.equalsIgnoreCase("OOO")||d2.equalsIgnoreCase("OOO")){
    						mpos.getMultiPlayerGames().get(j).setGameResult(Outcome.player2_win);
    						System.out.println("Player2 win");
    						mpos.updateResults();
    						GameEntry twoGame = new GameEntry();
    						twoGame.setPlayer1(gameDao.getGameUser(player1));
    						twoGame.setPlayer2(gameDao.getGameUser(player2));
    						twoGame.setAgainstAI(false);
    						twoGame.setGameState(Outcome.player2_win);
    						twoGame.setStartTime(mpos.getMultiPlayerGames().get(j).getMultiStartTime());
    						twoGame.setEndTime(new Date());
    						gameDao.saveGameData(twoGame);
    						break;
    					}
    					else if(r1.length()==3 && r2.length()==3 && r3.length()==3 && c1.length()==3 && c2.length()==3 && c3.length()==3 && d1.length()==3 && d2.length()==3){
    						if(!(r1.equalsIgnoreCase("XXX")||c1.equalsIgnoreCase("XXX")||c2.equalsIgnoreCase("XXX")||c3.equalsIgnoreCase("XXX")||r2.equalsIgnoreCase("XXX")||r3.equalsIgnoreCase("XXX")||d1.equalsIgnoreCase("XXX")||d2.equalsIgnoreCase("XXX")||r1.equalsIgnoreCase("OOO")||c1.equalsIgnoreCase("OOO")||c2.equalsIgnoreCase("OOO")||c3.equalsIgnoreCase("OOO")||r2.equalsIgnoreCase("OOO")||r3.equalsIgnoreCase("OOO")||d1.equalsIgnoreCase("OOO")||d2.equalsIgnoreCase("OOO"))){
    							mpos.getMultiPlayerGames().get(j).setGameResult(Outcome.tie_games);
    							System.out.println("Game Tied");
    							mpos.updateResults();
    							GameEntry twoGame = new GameEntry();
        						twoGame.setPlayer1(gameDao.getGameUser(player1));
        						twoGame.setPlayer2(gameDao.getGameUser(player2));
        						twoGame.setAgainstAI(false);
        						twoGame.setGameState(Outcome.tie_games);
        						twoGame.setStartTime(mpos.getMultiPlayerGames().get(j).getMultiStartTime());
        						twoGame.setEndTime(new Date());
        						gameDao.saveGameData(twoGame);
    							break;
    						}
    					}
    					
    					
    					
    				}
    				/*if(player1.equalsIgnoreCase(session.getAttribute("username").toString())){
    					if(multiCells[id]==null){
    						multiCells[id] = "X";
    						//mpos.getMultiPlayerGames().remove(index)
    						mpos.getMultiPlayerGames().get(j).setCellBoxes(multiCells);
    						mpos.updateResults();
    					}
    				}
    				else if(player2.equalsIgnoreCase(session.getAttribute("username").toString())){
    					if(multiCells[id]==null){
    						multiCells[id]="O";
    						mpos.getMultiPlayerGames().get(j).setCellBoxes(multiCells);
    						mpos.updateResults();
    					}
    				}*/
    			}
    		}
    		models.put("twoGameOnlineService", mpos.getMultiPlayerGames());
    	//}
    	return "multiPlayerGame";
    }

    @RequestMapping(value="/Exit.html", method=RequestMethod.GET)
    public String exitFromGame(HttpSession session, ModelMap models){
    	//String h = session.getAttribute("hostsession").toString();
    	//String j = session.getAttribute("joinsession").toString();
    	GameUser gu = gameDao.getGameUser(session.getAttribute("username").toString());
    	if(session.getAttribute("username").toString()!=null){
    		for(int i=0; i<mpos.getMultiPlayerGames().size(); i++){
    			if(mpos.getMultiPlayerGames().get(i).getGameBetween().contains(gu.getUsername())){
    				mpos.getMultiPlayerGames().remove(mpos.getMultiPlayerGames().get(i));
    			}
    		}
    		/*for(int i=0; i<tgos.getJoinedUsernames().size(); i++){
    			if(tgos.getJoinedUsernames().get(i).equalsIgnoreCase(j)){
    				tgos.getJoinedUsernames().remove(tgos.getJoinedUsernames().get(i));
    			}
    		}*/
    		return "Menu";
    	}
    	else{
    		return "redirect:/login.html";
    	}
    }
    
    @RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model,HttpSession session,Principal principal) {
 
		//UserEntry user = (UserEntry)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String name = principal.getName();
	
		//model.addAttribute("username", name);
		session.setAttribute("username", name);
		return "redirect:/Menu.html";
 
	}
 
	@RequestMapping(value="/login1", method = RequestMethod.GET)
	public String login(ModelMap model) {
 
		return "login1";
 
	}
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
 
		model.addAttribute("error", "true");
		return "login1";
 
	}
	
	/*@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
 
		return "login1";
 
	}*/
	
	@RequestMapping(value="/playAsGuest.html", method=RequestMethod.GET)
    public String guestPlayInit(@RequestParam(required=false) Integer row, HttpSession session){
		if(session.getAttribute("cellArray") == null){
			String message = null;
			String[] cellArray = new String[9];
			cellArray[0] = "";
			cellArray[1] = "";
			cellArray[2] = "";
			cellArray[3] = "";
			cellArray[4] = "";
			cellArray[5] = "";
			cellArray[6] = "";
			cellArray[7] = "";
			cellArray[8] = "";
			session.setAttribute("cellArray", cellArray);
			session.setAttribute("message", message);
		}
		else{
		String cellArray[] = (String[]) session.getAttribute("cellArray");
		String message = (String) session.getAttribute("message");
    	if(cellArray[row]==null || cellArray[row].isEmpty()){
    		//String element = row +":"+ "X";
    		cellArray[row]="X";
    		//bList.add(element);
        	
    	}
    	if(message==null){
    		if(cellArray[0].contains("X") && cellArray[1].contains("X") && cellArray[2].contains("X")){
				message = "You won!";
			}
			else if(cellArray[3].contains("X") && cellArray[4].contains("X") && cellArray[5].contains("X")){
				message = "You won!";
			}
			else if(cellArray[6].contains("X") && cellArray[7].contains("X") && cellArray[8].contains("X")){
				message = "You won!";
			}
			else if(cellArray[0].contains("X") && cellArray[3].contains("X") && cellArray[6].contains("X")){
				message = "You won!";
			}
			else if(cellArray[1].contains("X") && cellArray[4].contains("X") && cellArray[7].contains("X")){
				message = "You won!";
			}
			else if(cellArray[2].contains("X") && cellArray[5].contains("X") && cellArray[8].contains("X")){
				message = "You won!";
			}
			else if(cellArray[0].contains("X") && cellArray[4].contains("X") && cellArray[8].contains("X")){
				message = "You won!";
			}
			else if(cellArray[2].contains("X") && cellArray[4].contains("X") && cellArray[6].contains("X")){
				message = "You won!";
			}
		
		// start logic for making the winning move
		// first row
			else if(cellArray[0].contains("O") && cellArray[1].contains("O") && (cellArray[2].isEmpty() || cellArray[2] == null)){
			//if(cellArray[2].isEmpty() || cellArray[2] == null){
				cellArray[2] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[1].contains("O") && cellArray[2].contains("O") && (cellArray[0].isEmpty() || cellArray[0] == null)){
			//if(cellArray[0].isEmpty() || cellArray[0] == null){
				cellArray[0] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[0].contains("O") && cellArray[2].contains("O") && (cellArray[1].isEmpty() || cellArray[1] == null)){
			//if(cellArray[1].isEmpty() || cellArray[1] == null){
				cellArray[1] = "O";
				message = "I won!";
			//}
		}
		// end first row
		// start second row
		else if(cellArray[3].contains("O") && cellArray[4].contains("O") && (cellArray[5].isEmpty() || cellArray[5] == null)){
			//if(cellArray[5].isEmpty() || cellArray[5] == null){
				cellArray[5] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[4].contains("O") && cellArray[5].contains("O") && (cellArray[3].isEmpty() || cellArray[3] == null)){
			//if(cellArray[3].isEmpty() || cellArray[3] == null){
				cellArray[3] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[5].contains("O") && cellArray[3].contains("O") && (cellArray[4].isEmpty() || cellArray[4] == null)){
			//if(cellArray[4].isEmpty() || cellArray[4] == null){
				cellArray[4] = "O";
				message = "I won!";
			//}
		}
		// end second row
		// start third row
		else if(cellArray[6].contains("O") && cellArray[7].contains("O") && (cellArray[8].isEmpty() || cellArray[8] == null)){
			//if(cellArray[8].isEmpty() || cellArray[8] == null){
				cellArray[8] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[7].contains("O") && cellArray[8].contains("O") && (cellArray[6].isEmpty() || cellArray[6] == null)){
			//if(cellArray[6].isEmpty() || cellArray[6] == null){
				cellArray[6] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[8].contains("O") && cellArray[6].contains("O") && (cellArray[7].isEmpty() || cellArray[7] == null)){
			//if(cellArray[7].isEmpty() || cellArray[7] == null){
				cellArray[7] = "O";
				message = "I won!";
			//}
		}
		// end third row
		// start first column
		else if(cellArray[0].contains("O") && cellArray[3].contains("O") && (cellArray[6].isEmpty() || cellArray[6] == null)){
			//if(cellArray[6].isEmpty() || cellArray[6] == null){
				cellArray[6] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[3].contains("O") && cellArray[6].contains("O") && (cellArray[0].isEmpty() || cellArray[0] == null)){
			//if(cellArray[0].isEmpty() || cellArray[0] == null){
				cellArray[0] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[6].contains("O") && cellArray[0].contains("O") && (cellArray[3].isEmpty() || cellArray[3] == null)){
			//if(cellArray[3].isEmpty() || cellArray[3] == null){
				cellArray[3] = "O";
				message = "I won!";
			//}
		}
		// end first column
		// start second column
		else if(cellArray[1].contains("O") && cellArray[4].contains("O") && (cellArray[7].isEmpty() || cellArray[7] == null)){
			//if(cellArray[7].isEmpty() || cellArray[7] == null){
				cellArray[7] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[4].contains("O") && cellArray[7].contains("O") && (cellArray[1].isEmpty() || cellArray[1] == null)){
			//if(cellArray[1].isEmpty() || cellArray[1] == null){
				cellArray[1] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[7].contains("O") && cellArray[1].contains("O") && (cellArray[4].isEmpty() || cellArray[4] == null)){
			//if(cellArray[4].isEmpty() || cellArray[4] == null){
				cellArray[4] = "O";
				message = "I won!";
			//}
		}
		// end second column
		// start third column
		else if(cellArray[2].contains("O") && cellArray[5].contains("O") && (cellArray[8].isEmpty() || cellArray[8] == null)){
			//if(cellArray[8].isEmpty() || cellArray[8] == null){
				cellArray[8] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[5].contains("O") && cellArray[8].contains("O") && (cellArray[2].isEmpty() || cellArray[2] == null)){
			//if(cellArray[2].isEmpty() || cellArray[2] == null){
				cellArray[2] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[8].contains("O") && cellArray[2].contains("O") && (cellArray[5].isEmpty() || cellArray[5] == null)){
			//if(cellArray[5].isEmpty() || cellArray[5] == null){
				cellArray[5] = "O";
				message = "I won!";
			//}
		}
		// end third column
		// start first diagonal
		else if(cellArray[0].contains("O") && cellArray[4].contains("O") && (cellArray[8].isEmpty() || cellArray[8] == null)){
			//if(cellArray[8].isEmpty() || cellArray[8] == null){
				cellArray[8] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[4].contains("O") && cellArray[8].contains("O") && (cellArray[0].isEmpty() || cellArray[0] == null)){
			//if(cellArray[0].isEmpty() || cellArray[0] == null){
				cellArray[0] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[0].contains("O") && cellArray[8].contains("O") && (cellArray[4].isEmpty() || cellArray[4] == null)){
			//if(cellArray[4].isEmpty() || cellArray[4] == null){
				cellArray[4] = "O";
				message = "I won!";
			//}
		}
		// end first diagonal
		// start second diagonal
		else if(cellArray[2].contains("O") && cellArray[4].contains("O") && (cellArray[6].isEmpty() || cellArray[6] == null)){
			//if(cellArray[6].isEmpty() || cellArray[6] == null){
				cellArray[6] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[4].contains("O") && cellArray[6].contains("O") && (cellArray[2].isEmpty() || cellArray[2] == null)){
			//if(cellArray[2].isEmpty() || cellArray[2] == null){
				cellArray[2] = "O";
				message = "I won!";
			//}
		}
		else if(cellArray[2].contains("O") && cellArray[6].contains("O") && (cellArray[4].isEmpty() || cellArray[4] == null)){
			//if(cellArray[4].isEmpty() || cellArray[4] == null){
				cellArray[4] = "O";
				message = "I won!";
			//}
		}
		// end second diagonal
		// end logic for making the winning move
		
		// logic for blocking the winning move
		// checking for first row (0,1,2)
		else if(cellArray[0].contains("X") && cellArray[1].contains("X") && (cellArray[2].isEmpty() || cellArray[2] == null)){
			//if(cellArray[2].isEmpty() || cellArray[2] == null){
				cellArray[2] = "O";
			//}
		}
		else if(cellArray[1].contains("X") && cellArray[2].contains("X") && (cellArray[0].isEmpty() || cellArray[0] == null)){
			//if(cellArray[0].isEmpty() || cellArray[0] == null){
				cellArray[0] = "O";
			//}
		}
		else if(cellArray[0].contains("X") && cellArray[2].contains("X") && (cellArray[1].isEmpty() || cellArray[1] == null)){
			//if(cellArray[1].isEmpty() || cellArray[1] == null){
				cellArray[1] = "O";
			//}
		}
		// end first row
		// start for second row (3,4,5) 
		else if(cellArray[3].contains("X") && cellArray[4].contains("X") && (cellArray[5].isEmpty() || cellArray[5] == null)){
			//if(cellArray[5].isEmpty() || cellArray[5] == null){
				cellArray[5] = "O";
			//}
		}
		else if(cellArray[4].contains("X") && cellArray[5].contains("X") && (cellArray[3].isEmpty() || cellArray[3] == null)){
			//if(cellArray[3].isEmpty() || cellArray[3] == null){
				cellArray[3] = "O";
			//}
		}
		else if(cellArray[5].contains("X") && cellArray[3].contains("X") && (cellArray[4].isEmpty() || cellArray[4] == null)){
			//if(cellArray[4].isEmpty() || cellArray[4] == null){
				cellArray[4] = "O";
			//}
		}
		// end second row
		// start for third row (6,7,8) 
		else if(cellArray[6].contains("X") && cellArray[7].contains("X") && (cellArray[8].isEmpty() || cellArray[8] == null)){
			//if(cellArray[8].isEmpty() || cellArray[8] == null){
				cellArray[8] = "O";
			//}
		}
		else if(cellArray[7].contains("X") && cellArray[8].contains("X") && (cellArray[6].isEmpty() || cellArray[6] == null)){
			//if(cellArray[6].isEmpty() || cellArray[6] == null){
				cellArray[6] = "O";
			//}
		}
		else if(cellArray[8].contains("X") && cellArray[6].contains("X") && (cellArray[7].isEmpty() || cellArray[7] == null)){
			//if(cellArray[7].isEmpty() || cellArray[7] == null){
				cellArray[7] = "O";
			//}
		}
		// end third row
		// start for first column (0,3,6)
		else if(cellArray[0].contains("X") && cellArray[3].contains("X") && (cellArray[6].isEmpty() || cellArray[6] == null)){
			//if(cellArray[6].isEmpty() || cellArray[6] == null){
				cellArray[6] = "O";
			//}
		}
		else if(cellArray[3].contains("X") && cellArray[6].contains("X") && (cellArray[0].isEmpty() || cellArray[0] == null)){
			//if(cellArray[0].isEmpty() || cellArray[0] == null){
				cellArray[0] = "O";
			//}
		}
		else if(cellArray[6].contains("X") && cellArray[0].contains("X") && (cellArray[3].isEmpty() || cellArray[3] == null)){
			//if(cellArray[3].isEmpty() || cellArray[3] == null){
				cellArray[3] = "O";
			//}
		}
		// end first column
		// start for second column (1,4,7)
		else if(cellArray[1].contains("X") && cellArray[4].contains("X") && (cellArray[7].isEmpty() || cellArray[7] == null)){
			//if(cellArray[7].isEmpty() || cellArray[7] == null){
				cellArray[7] = "O";
			//}
		}
		else if(cellArray[4].contains("X") && cellArray[7].contains("X") && (cellArray[1].isEmpty() || cellArray[1] == null)){
			//if(cellArray[1].isEmpty() || cellArray[1] == null){
				cellArray[1] = "O";
			//}
		}
		else if(cellArray[7].contains("X") && cellArray[1].contains("X") && (cellArray[4].isEmpty() || cellArray[4] == null)){
			//if(cellArray[4].isEmpty() || cellArray[4] == null){
				cellArray[4] = "O";
			//}
		}
		// end second column
		// start third column (2,5,8)
		else if(cellArray[2].contains("X") && cellArray[5].contains("X") && (cellArray[8].isEmpty() || cellArray[8] == null)){
			//if(cellArray[8].isEmpty() || cellArray[8] == null){
				cellArray[8] = "O";
			//}
		}
		else if(cellArray[5].contains("X") && cellArray[8].contains("X") && (cellArray[2].isEmpty() || cellArray[2] == null)){
			//if(cellArray[2].isEmpty() || cellArray[2] == null){
				cellArray[2] = "O";
			//}
		}
		else if(cellArray[8].contains("X") && cellArray[2].contains("X") && (cellArray[5].isEmpty() || cellArray[5] == null)){
			//if(cellArray[5].isEmpty() || cellArray[5] == null){
				cellArray[5] = "O";
			//}
		}
		// end third column
		// start first diagonal
		else if(cellArray[0].contains("X") && cellArray[4].contains("X") && (cellArray[8].isEmpty() || cellArray[8] == null)){
			//if(cellArray[8].isEmpty() || cellArray[8] == null){
				cellArray[8] = "O";
			//}
		}
		else if(cellArray[4].contains("X") && cellArray[8].contains("X") && (cellArray[0].isEmpty() || cellArray[0] == null)){
			//if(cellArray[0].isEmpty() || cellArray[0] == null){
				cellArray[0] = "O";
			//}
		}
		else if(cellArray[0].contains("X") && cellArray[8].contains("X") && (cellArray[4].isEmpty() || cellArray[4] == null)){
			//if(cellArray[4].isEmpty() || cellArray[4] == null){
				cellArray[4] = "O";
			//}
		}
		// end first diagonal
		// start second diagonal
		else if(cellArray[2].contains("X") && cellArray[4].contains("X") && (cellArray[6].isEmpty() || cellArray[6] == null)){
			//if(cellArray[6].isEmpty() || cellArray[6] == null){
				cellArray[6] = "O";
			//}
		}
		else if(cellArray[4].contains("X") && cellArray[6].contains("X") && (cellArray[2].isEmpty() || cellArray[2] == null)){
			//if(cellArray[2].isEmpty() || cellArray[2] == null){
				cellArray[2] = "O";
			//}
		}
		else if(cellArray[2].contains("X") && cellArray[6].contains("X") && (cellArray[4].isEmpty() || cellArray[4] == null)){
			//if(cellArray[4].isEmpty() || cellArray[4] == null){
				cellArray[4] = "O";
			//}
		}
		// end second diagonal 
		// blocking winning move logic ends
		// generate a random number
		else {
			List<Integer> temp= new ArrayList<Integer>() ;
			for(int i=0; i<cellArray.length; i++){
				if(cellArray[i].isEmpty()){
					temp.add(i);
				}
			}
			Random ran = new Random();
			if(temp.size()!=0){
			cellArray[temp.get(ran.nextInt(temp.size()))] = "O";
			}
			else if(temp.size()==0){
				message = "Game tied!";
			}
		}

    	
    	}
    	
    	if(message!=null){
    		/*if(message.equalsIgnoreCase("I won!")){
    			gEnd.setGameState(Outcome.AI_win);
    		}
    		else if(message.equalsIgnoreCase("You won!")){
    			gEnd.setGameState(Outcome.player1_win);
    		}
    		else if(message.equalsIgnoreCase("Game tied!")){
    			gEnd.setGameState(Outcome.tie_games);
    		}*/
    		//session.removeAttribute("cellArray");
    	}
    	/*else{
    		return "redirect:/login.html";
    	}*/
    	
    	session.setAttribute("message", message);
    	
		
		}
		return "playAsGuest";
	}
	
	@RequestMapping(value="/newGameAsGuest.html", method=RequestMethod.GET)
    public String newGuestGame(HttpSession session){
		if(session.getAttribute("cellArray") != null){
			session.removeAttribute("cellArray");
		}
		return "redirect:/playAsGuest.html";
	}
}