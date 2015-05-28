<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Multi-Player Game</title>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	
	var host;
	var join;
	var check = false;
	var enableClick = false;
		$(function(){
		    $.ajax({
		        url: "twoGameOnline.json",
		        cache: false,
		        success: function( data ) {
		            //$("#joinlist").empty();
		            //alert('here');
		            //alert(data.twoGameOnlineService["hostedUsernames"].length);
		            //if("${check}") {
		            	//alert("simple json "+data.twoGameOnlineService.length);
		            	
		            	//if(!check){
		            		$("#message2game").empty();
		            	for(var i=0; i<data.twoGameOnlineService.length; i++){
		            		//alert("simple json i"+data.twoGameOnlineService[i].gameBetween);
		            		var gamePlayers = data.twoGameOnlineService[i].gameBetween.split('&');
		            		var player1 = gamePlayers[0];
		            		var player2 = gamePlayers[1];
		            		//var j=i;
		            		//alert(player1);
		            		if(player1==="${username}"){
		            			$("#message2game").empty();
			        			$("#message2game").append("<li>"+ player2 +" has joined the game. Please make your move.....</li>");
			        			//alert("here in simple json if");
			        			//host = data.twoGameOnlineService["hostedUsernames"][i];
			        			$("#firstRow").empty();
			        			$("#secondRow").empty();
			        			$("#thirdRow").empty();
			        			check = true;
			        			//enableClick = true;
			        			//alert("p1 bl "+data.twoGameOnlineService[i].cellBoxes[i]);
			        			if(data.twoGameOnlineService[i].gameResult === "player1_win"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li>"+ player1 +" has won the game.</li>");
			        				check = false;
			        				break;
			        			}
			        			else if(data.twoGameOnlineService[i].gameResult === "player2_win"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li>"+ player2 +" has won the game.</li>");
			        				check = false;
			        				break;
			        			}
			        			else if(data.twoGameOnlineService[i].gameResult === "tie_games"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li> Game Tied!!!</li>");
			        				check = false;
			        				break;
			        			}
			        			if(check){
			        			var boardLength = 9;
			        			
			        			for(var j=0; j<boardLength; j++){
				            		   
				            		    if(j>=0 && j<3){
				            		    	//$("#firstRow").empty();
				            		    	if(data.twoGameOnlineService[i].cellBoxes[j] === null){
				            		    		if(!enableClick){
				            		    			
							        				
				            		    			$("#firstRow").append("<td>__</td>");
				            		    			player1Turn = false;
				            		    		}
				            		    		else if(enableClick){
				            		    			
				            		    			$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
				            		    		}
				            		    	}
				            		    	else{
				            		    		$("#firstRow").append("<td>"+data.twoGameOnlineService[i].cellBoxes[j]+"</td>");
				            		    	}
				            		    }
				            		    else if(j>=3 && j<6){
				            		    	//$("#secondRow").empty();
				            		    	if(data.twoGameOnlineService[i].cellBoxes[j] === null){
				            		    		if(!enableClick){
				            		    			$("#secondRow").append("<td>__</td>");
				            		    		}
				            		    		else if(enableClick){
				            		    			
				            		    			$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
				            		    			//enableClick;
				            		    		}
				            		    	}
				            		    	else{
				            		    		$("#secondRow").append("<td>"+data.twoGameOnlineService[i].cellBoxes[j]+"</td>");
				            		    	}
				            		    }
				            		    else if(j>=6 && j<9){
				            		    	//$("#thirdRow").empty();
				            		    	if(data.twoGameOnlineService[i].cellBoxes[j] == null){
				            		    		if(!enableClick){
				            		    			
				            		    			$("#thirdRow").append("<td>__</td>");
				            		    		}
				            		    		else if(enableClick){
				            		    			
				            		    			$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
				            		    		}
				            		    	}
				            		    	else{
				            		    		$("#thirdRow").append("<td>"+data.twoGameOnlineService[i].cellBoxes[j]+"</td>");
				            		    	}
				            		    }
				            	   }
				            	   check=false;
				            	   enableClick = false;
			        			}
			        			
			        			break;
		            		}
		            		else if(player2==="${username}"){
		            			$("#message2game").empty();
			        			$("#message2game").append("<li>Joined game hosted by "+ player1 +". Waiting for "+player1+"'s move ...</li>");
			        			//alert("below here in else if");
			        			//join = data.twoGameOnlineService["joinedUsernames"][i];
			        			$("#firstRow").empty();
			        			$("#secondRow").empty();
			        			$("#thirdRow").empty();
			        			check = true;
			        			//enableClick = true;
			        			if(data.twoGameOnlineService[i].gameResult === "player1_win"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li>"+ player1 +" has won the game.</li>");
			        				check = false;
			        				break;
			        			}
			        			else if(data.twoGameOnlineService[i].gameResult === "player2_win"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li>"+ player2 +" has won the game.</li>");
			        				check = false;
			        				break;
			        			}
			        			else if(data.twoGameOnlineService[i].gameResult === "tie_games"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li> Game Tied!!!</li>");
			        				check = false;
			        				break;
			        			}
			        			if(check){
				        			var boardLength = 9;
					            	   for(var j=0; j<boardLength; j++){
					            		   
					            		   if(j>=0 && j<3){
					            		    	//$("#firstRow").empty();
					            		    	if(data.twoGameOnlineService[i].cellBoxes[j] === null){
					            		    		if(!enableClick){
					            		    			
					            		    			$("#firstRow").append("<td>__</td>");
					            		    		}
					            		    		else if(enableClick){
					            		    			
					            		    			$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    			//alert(enableClick);
					            		    		}
					            		    	}
					            		    	else{
					            		    		$("#firstRow").append("<td>"+data.twoGameOnlineService[i].cellBoxes[j]+"</td>");
					            		    	}
					            		    }
					            		    else if(j>=3 && j<6){
					            		    	//$("#secondRow").empty();
					            		    	if(data.twoGameOnlineService[i].cellBoxes[j] == null){
					            		    		if(!enableClick){
					            		    			
					            		    			$("#secondRow").append("<td>__</td>");
					            		    		}
					            		    		else if(enableClick){
					            		    			
					            		    			$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    		}
					            		    	}
					            		    	else{
					            		    		$("#secondRow").append("<td>"+data.twoGameOnlineService[i].cellBoxes[j]+"</td>");
					            		    	}
					            		    }
					            		    else if(j>=6 && j<9){
					            		    	//$("#thirdRow").empty();
					            		    	if(data.twoGameOnlineService[i].cellBoxes[j] == null){
					            		    		if(!enableClick){
					            		    			
					            		    			$("#thirdRow").append("<td>__</td>");
					            		    		}
					            		    		else if(enableClick){
					            		    			
					            		    			$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    		}
					            		    	}
					            		    	else{
					            		    		$("#thirdRow").append("<td>"+data.twoGameOnlineService[i].cellBoxes[j]+"</td>");
					            		    	}
					            		    }
					            	   }
					            	   check=false;
					            	   enableClick = false;
				        			}
			        			break;
		            		}
		            	}
		                       
		        }
		    });
		    updateTwoPlayerGame();
		});
		
		function updateTwoPlayerGame()
		{
		    $.ajax({
		        url: "twoGameOnline.deferred.json",
		        cache: false,
		        success: function( data ) {
		        	//if(!check){
		        		//alert("deferred length"+ data.length);
		            	for(var i=0; i<data.length; i++){
		            		//alert(" deferred "+data[i].gameBetween);
		            		var gamePlayers = data[i].gameBetween.split('&');
		            		var player1 = gamePlayers[0];
		            		var player2 = gamePlayers[1];
		            		//alert(player1);
		            		if(player1==="${username}"){
		            			$("#message2game").empty();
			        			$("#message2game").append("<li>"+ player2 +" has joined the game. Please make your move.....</li>");
			        			$("#firstRow").empty();
			        			$("#secondRow").empty();
			        			$("#thirdRow").empty();
			        			
			        			//host = data.twoGameOnlineService["hostedUsernames"][i];
			        			check = true;
			        			enableClick = true;
			        			if(data[i].gameResult === "player1_win"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li>"+ player1 +" has won the game.</li>");
			        				check = false;
			        				break;
			        			}
			        			else if(data[i].gameResult === "player2_win"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li>"+ player2 +" has won the game.</li>");
			        				check = false;
			        				break;
			        			}
			        			else if(data[i].gameResult === "tie_games"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li> Game Tied!!!</li>");
			        				check = false;
			        				break;
			        			}
			        			
			        			if(check){
			        				
				        			var boardLength = 9;
					            	   for(var j=0; j<boardLength; j++){
					            		   
					            		    if(j>=0 && j<3){
					            		    	//$("#firstRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		if(!enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Wait for your opponent's move</li>");
								        				
					            		    			$("#firstRow").append("<td>__</td>");
					            		    		}
					            		    		else if(enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Please make your move</li>");
								        				
					            		    			$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    			//enableClick = false;
					            		    		}
					            		    	}
					            		    	else{
					            		    		$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data[i].cellBoxes[j]+"</a></td>");
					            		    	}
					            		    }
					            		    else if(j>=3 && j<6){
					            		    	//$("#secondRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		if(!enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Wait for your opponent's move</li>");
								        				
					            		    			$("#secondRow").append("<td>__</td>");
					            		    		}
					            		    		else if(enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Please make your move</li>");
								        				
					            		    			$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    			//enableClick = false;
					            		    		}
					            		    	}
					            		    	else{
					            		    		$("#secondRow").append("<td>"+data[i].cellBoxes[j]+"</td>");
					            		    	}
					            		    }
					            		    else if(j>=6 && j<9){
					            		    	//$("#thirdRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		if(!enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Wait for your opponent's move</li>");
								        				
					            		    			$("#thirdRow").append("<td>__</td>");
					            		    		}
					            		    		else if(enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Please make your move</li>");
								        				
					            		    			$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    			//enableClick = false;
					            		    		}
					            		    	}
					            		    	else{
					            		    		$("#thirdRow").append("<td>"+data[i].cellBoxes[j]+"</td>");
					            		    	}
					            		    }
					            	   }
					            	   check=false;
					            	   enableClick=false;
				        			}
			        			break;
		            		}
		            		else if(player2 === "${username}"){
		            			$("#message2game").empty();
			        			$("#message2game").append("<li>Joined game hosted by "+ player1 +". Waiting for "+player1+"'s move ...</li>");
			        			$("#firstRow").empty();
			        			$("#secondRow").empty();
			        			$("#thirdRow").empty();
			        			//join = data.twoGameOnlineService["joinedUsernames"][i];
			        			check = true;
			        			enableClick = true;
			        			if(data[i].gameResult === "player1_win"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li>"+ player1 +" has won the game.</li>");
			        				check = false;
			        				break;
			        			}
			        			else if(data[i].gameResult === "player2_win"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li>"+ player2 +" has won the game.</li>");
			        				check = false;
			        				break;
			        			}
			        			else if(data[i].gameResult === "tie_games"){
			        				$("#winMessage").empty();
			        				$("#winMessage").append("<li> Game Tied!!!</li>");
			        				check = false;
			        				break;
			        			}
			        			if(check){
			        				
				        			var boardLength = 9;
					            	   for(var j=0; j<boardLength; j++){
					            		   
					            		   if(j>=0 && j<3){
					            		    	//$("#firstRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		if(!enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Wait for your opponent's move</li>");
								        				
					            		    			$("#firstRow").append("<td>__</td>");
					            		    		}
					            		    		else if(enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Please make your move</li>");
								        				
					            		    			$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    		}
					            		    	}
					            		    	else{
					            		    		$("#firstRow").append("<td>"+data[i].cellBoxes[j]+"</td>");
					            		    	}
					            		    }
					            		    else if(j>=3 && j<6){
					            		    	//$("#secondRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		if(!enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Wait for your opponent's move</li>");
								        				
					            		    			$("#secondRow").append("<td>__</td>");
					            		    		}
					            		    		else if(enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Please make your move</li>");
								        				
					            		    			$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    		}
					            		    	}
					            		    	else{
					            		    		$("#secondRow").append("<td>"+data[i].cellBoxes[j]+"</td>");
					            		    	}
					            		    }
					            		    else if(j>=6 && j<9){
					            		    	//$("#thirdRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		if(!enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Wait for your opponent's move</li>");
								        				
					            		    			$("#thirdRow").append("<td>__</td>");
					            		    		}
					            		    		else if(enableClick){
					            		    			//$("#winMessage").empty();
								        				//$("#winMessage").append("<li>Please make your move</li>");
								        				
					            		    			$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    		}
					            		    	}
					            		    	else{
					            		    		$("#thirdRow").append("<td>"+data[i].cellBoxes[j]+"</td>");
					            		    	}
					            		    }
					            	   }
					            	   check=false;
					            	   enableClick = false;
				        			}
			        			break;
		            		}
		            	}
		            updateTwoPlayerGame();
		        }
		    });
		}
		
</script>

</head>

<body>
<h3>Hello, ${username}</h3>
<table border="1" align="center">
<tr>
	<td colspan="2">
		<h3>Message</h3>
		<ul id="winMessage">${twoPlayerGameMessage}</ul>
	</td>
</tr>

</table>

<br/>
<table id="multiPlayerBoard" border="1" align="center">
<tr id="firstRow"></tr>
<tr id="secondRow"></tr>
<tr id="thirdRow"></tr>
</table>
<br>
<p align="center"><a href="Exit.html">Exit</a></p>
</body>
</html>