<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Who's Online (jQuery)</title>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
$(function(){
    $.ajax({
        url: "whosonline.json",
        cache: false,
        success: function( data ) {
        	$("#list").empty();
        	for(var i=0; i<data.username.length; i++){
        		$("#list").append("<li>"+data.username[i]+"</li>");
        	}
        }
    });
    update();
});
function update()
{
    $.ajax({
        url: "whosonline.deferred.json",
        cache: false,
        success: function( data ) {
            $("#list").empty();
            for(var i=0; i<data.length; i++){
        		$("#list").append("<li>"+data[i]+"</li>");
        	}
            update();
        }
    });
}
</script>
</head>
<body>
<h3>Who's Online</h3>
<ul id="list">
</ul>
</body>
</html>
