<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Two Player Game</title>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	
	var host;
	var join;
	var check = false;
	$(function(){
	    $.ajax({
	        url: "hostsonline.json",
	        cache: false,
	        success: function( data ) {
	        	$("#hostlist").empty();
	        	for(var i=0; i<data.hostusernames.length; i++){
	        		$("#hostlist").append("<li>"+data.hostusernames[i]+"</li>");
	        	}
	        }
	    });
	    updateHostList();
	});
	function updateHostList()
	{
	    $.ajax({
	        url: "hostsonline.deferred.json",
	        cache: false,
	        success: function( data ) {
	            $("#hostlist").empty();
	            for(var i=0; i<data.length; i++){
	        		$("#hostlist").append("<li>"+data[i]+"</li>");
	        	}
	            updateHostList();
	        }
	    });
	}
	$(function(){
		    $.ajax({
		        url: "joinonline.json",
		        cache: false,
		        success: function( data ) {
		        	$("#joinlist").empty();
		        	for(var i=0; i<data.joinedusernames.length; i++){
		        		$("#joinlist").append("<li>"+data.joinedusernames[i]+"</li>");
		        	}
		        }
		    });
		    updateJoinList();
		});
		function updateJoinList()
		{
		    $.ajax({
		        url: "joinonline.deferred.json",
		        cache: false,
		        success: function( data ) {
		            $("#joinlist").empty();
		            for(var i=0; i<data.length; i++){
		        		$("#joinlist").append("<li>"+data[i]+"</li>");
		        	}
		            updateJoinList();
		        }
		    });
		}
		
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
			        			//alert("p1 bl "+data.twoGameOnlineService[i].cellBoxes[i]);
			        			if(check){
			        			var boardLength = 9;
				            	   for(var j=0; j<boardLength; j++){
				            		   
				            		    if(j>=0 && j<3){
				            		    	//$("#firstRow").empty();
				            		    	if(data.twoGameOnlineService[i].cellBoxes[j] === null){
				            		    		$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
				            		    	}
				            		    	else{
				            		    		$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data.twoGameOnlineService[i].cellBoxes[j]+"</a></td>");
				            		    	}
				            		    }
				            		    else if(j>=3 && j<6){
				            		    	//$("#secondRow").empty();
				            		    	if(data.twoGameOnlineService[i].cellBoxes[j] === null){
				            		    		$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
				            		    	}
				            		    	else{
				            		    		$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data.twoGameOnlineService[i].cellBoxes[j]+"</a></td>");
				            		    	}
				            		    }
				            		    else if(j>=6 && j<9){
				            		    	//$("#thirdRow").empty();
				            		    	if(data.twoGameOnlineService[i].cellBoxes[j] === null){
				            		    		$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
				            		    	}
				            		    	else{
				            		    		$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data.twoGameOnlineService[i].cellBoxes[j]+"</a></td>");
				            		    	}
				            		    }
				            	   }
				            	   check=false;
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
			        			if(check){
				        			var boardLength = 9;
					            	   for(var j=0; j<boardLength; j++){
					            		   
					            		   if(j>=0 && j<3){
					            		    	//$("#firstRow").empty();
					            		    	if(data.twoGameOnlineService[i].cellBoxes[j] === null){
					            		    		$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    	}
					            		    	else{
					            		    		$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data.twoGameOnlineService[i].cellBoxes[j]+"</a></td>");
					            		    	}
					            		    }
					            		    else if(j>=3 && j<6){
					            		    	//$("#secondRow").empty();
					            		    	if(data.twoGameOnlineService[i].cellBoxes[j] === null){
					            		    		$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    	}
					            		    	else{
					            		    		$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data.twoGameOnlineService[i].cellBoxes[j]+"</a></td>");
					            		    	}
					            		    }
					            		    else if(j>=6 && j<9){
					            		    	//$("#thirdRow").empty();
					            		    	if(data.twoGameOnlineService[i].cellBoxes[j] === null){
					            		    		$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    	}
					            		    	else{
					            		    		$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data.twoGameOnlineService[i].cellBoxes[j]+"</a></td>");
					            		    	}
					            		    }
					            	   }
					            	   check=false;
				        			}
			        			break;
		            		}
		            	}
		            	//}
		            /* if(data.twoGameOnlineService["hostedUsernames"].length === data.twoGameOnlineService["joinedUsernames"].length){
		            for(var i=0; i<data.twoGameOnlineService["hostedUsernames"].length; i++){
		        		//alert(data.twoGameOnlineService["hostedUsernames"][i]);
		        		if(data.twoGameOnlineService["joinedUsernames"].length > 0){
		        			if(data.twoGameOnlineService["hostedUsernames"][i] === "${username}" ){
		        			
		        			$("#message2game").empty();
		        			$("#message2game").append("<li>"+data.twoGameOnlineService["joinedUsernames"][i] +" has joined the game. Please make your move.....</li>");
		        			
		        			host = data.twoGameOnlineService["hostedUsernames"][i];
		        			check = true;
		        			break;
		        			}
		        		}
		        		if(data.twoGameOnlineService["hostedUsernames"].length > 0){
		        			if(data.twoGameOnlineService["joinedUsernames"][i] === "${username}" ){
		        			
		        			$("#message2game").empty();
		        			$("#message2game").append("<li>Joined game hosted by "+ data.twoGameOnlineService["hostedUsernames"][i]+". Waiting for "+data.twoGameOnlineService["hostedUsernames"][i]+"'s move ...</li>");
		        			
		        			join = data.twoGameOnlineService["joinedUsernames"][i];
		        			check = true;
		        			break;
		        			}
		        			
		        		}
		        		//$("#joinlist").append("<li>"+data.joinedusernames[i]+"</li>");
		        	} */
		            //}
		            /* if(check){
		            	$(function () { 
		            		var boardLength = 9;
			            	   for(var i=0; i<boardLength; i++){
			            		   
			            		    if(i>=0 && i<3){
			            		    	$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+i+"'>__</a></td>");
			            		    }
			            		    else if(i>=3 && i<6){
			            		    	$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+i+"'>__</a></td>");
			            		    }
			            		    else if(i>=6 && i<9){
			            		    	$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+i+"'>__</a></td>");
			            		    }
			            	   }
		            	   //document.getElementById("boardDisplay").style.display="";
		            	   check= false;
		            	});
		            } */
		            //}
		            //else{
		           
		            //}
		            //updateTwoPlayerGame();
		           
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
			        			if(check){
				        			var boardLength = 9;
					            	   for(var j=0; j<boardLength; j++){
					            		   
					            		    if(j>=0 && j<3){
					            		    	//$("#firstRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    	}
					            		    	else{
					            		    		$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data[i].cellBoxes[j]+"</a></td>");
					            		    	}
					            		    }
					            		    else if(j>=3 && j<6){
					            		    	//$("#secondRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    	}
					            		    	else{
					            		    		$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data[i].cellBoxes[j]+"</a></td>");
					            		    	}
					            		    }
					            		    else if(j>=6 && j<9){
					            		    	//$("#thirdRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    	}
					            		    	else{
					            		    		$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data[i].cellBoxes[j]+"</a></td>");
					            		    	}
					            		    }
					            	   }
					            	   check=false;
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
			        			if(check){
				        			var boardLength = 9;
					            	   for(var j=0; j<boardLength; j++){
					            		   
					            		   if(j>=0 && j<3){
					            		    	//$("#firstRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    	}
					            		    	else{
					            		    		$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data[i].cellBoxes[j]+"</a></td>");
					            		    	}
					            		    }
					            		    else if(j>=3 && j<6){
					            		    	//$("#secondRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    	}
					            		    	else{
					            		    		$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data[i].cellBoxes[j]+"</a></td>");
					            		    	}
					            		    }
					            		    else if(j>=6 && j<9){
					            		    	//$("#thirdRow").empty();
					            		    	if(data[i].cellBoxes[j] === null){
					            		    		$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>__</a></td>");
					            		    	}
					            		    	else{
					            		    		$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+j+"'>"+data[i].cellBoxes[j]+"</a></td>");
					            		    	}
					            		    }
					            	   }
					            	   check=false;
				        			}
			        			break;
		            		}
		            	}
		            	//}
		        	/* if(check){
		            	//alert('Outside Func');
		            	$(function () { 
		            	   var boardLength = 9;
		            	   for(var i=0; i<boardLength; i++){
		            		   if(i>=0 && i<3){
		            		    	$("#firstRow").append("<td><a href='multiPlayerGame.html?id="+i+"'>__</a></td>");
		            		    }
		            		    else if(i>=3 && i<6){
		            		    	$("#secondRow").append("<td><a href='multiPlayerGame.html?id="+i+"'>__</a></td>");
		            		    }
		            		    else if(i>=6 && i<9){
		            		    	$("#thirdRow").append("<td><a href='multiPlayerGame.html?id="+i+"'>__</a></td>");
		            		    }
		            	   }
		            	   check= false;
		            	});
		            } */ 
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
	<td>
		<h3>Host Username List</h3>
		<ul id="hostlist"></ul>
	</td>
	<td>
		<h3>Join Username List</h3>
		<ul id="joinlist"></ul>
	</td>
</tr>
<tr>
	<td colspan="2">
		<h3>Message</h3>
		<ul id="message2game">${twoPlayerGameMessage}</ul>
	</td>
</tr>

</table>
<p id="cellValue"></p>
<br/>
<table id="multiPlayerBoard" border="1" align="center">
<tr id="firstRow"></tr>
<tr id="secondRow"></tr>
<tr id="thirdRow"></tr>
</table>
<div id="boardDisplay" style="display:none;" align="center">

<table border="1">

<tr>

<td id="0" class="asd" height="40" width="40" align="center">__</td>
<td id="1" class="asd" height="40" width="40" align="center">__</td>
<td id="2" class="asd" height="40" width="40" align="center">__</td>
</tr>                                        
<tr>                                         
<td id="3" height="40" width="40" align="center"><p>__<p></td>
<td id="4" height="40" width="40" align="center"><p>__<p></td>
<td id="5" height="40" width="40" align="center"><p>__<p></td>
</tr>                                        
<tr>                                         
<td id="6" height="40" width="40" align="center"><p>__<p></td>
<td id="7" height="40" width="40" align="center"><p>__<p></td>
<td id="8" height="40" width="40" align="center"><p>__<p></td>

</tr> 

</table>



<!-- </table> -->
</div>
</body>
</html>