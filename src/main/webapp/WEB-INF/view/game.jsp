<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.brunotot.belacardgame.util.*" %>
<!DOCTYPE html>
<%
	String roomId = (String) request.getAttribute("roomId");
	String nickname = (String) request.getAttribute("nickname");
	String[] playerNames = Helper.getPlayerNicknames(request, roomId, nickname);
%>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Bela card game</title>
	<script src="/webjars/jquery/jquery.min.js"></script>
	<script src="/webjars/sockjs-client/sockjs.min.js"></script>
	<script src="/webjars/stomp-websocket/stomp.min.js"></script>
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>  
	<script>
		window.onload = function () {
			var b = document.getElementById('test');
			if (<%= nickname.equals(playerNames[3]) %>) {
				b.disabled = false;
			}
		}
		var subscription = null;
		var stompClient = null;
		connect();
		
		function connect() {
		    var socket = new SockJS('/websocket');
		    stompClient = Stomp.over(socket);
		    stompClient.connect({}, function (frame) {
		    	stompClient.subscribe('/topic/game/<%= roomId %>', function (room) {
					console.log("ISUSE");
		    		var button = document.getElementById('test');
					var r = JSON.parse(room.body);
					debugger;
					if (r.playerToMove.nickname.localeCompare('<%= nickname %>') === 0) {
						button.disabled = false;
					} else {
						button.disabled = true;
					}
		    	});
		    });
		}
		
		
		function moveToNextPlayer() {
			$.ajax({
                url: '/game/moveToNextPlayer',
                type: 'post',
                data: {
                	'roomId': '<%= roomId %>',
                	'nickname': '<%= nickname %>'
                },
                success:function(data) { 
					stompClient.send("/app/msgGame/<%= roomId %>", {});
                }
			});
		}
	</script>
</head>
<body>
	<div id="player-top" style="text-align: center; position: absolute; left: 50%; right: 50%;">
    	<p><%= playerNames[0] %></p>
    </div>
    <div id="player-left" style="text-align: left; position: absolute; top: 50%; left: 0">
    	<p><%= playerNames[1] %></p>
    </div>
    <div id="player-right" style="text-align: right; position: absolute; top: 50%; right: 0">
    	<p><%= playerNames[2] %></p>
    </div>
    <div id="player-bottom" style="text-align: center; position: absolute; bottom: 0; left: 50%; right: 50%;">
    	<p><%= playerNames[3] %></p>
    	<button disabled="disabled" onclick="moveToNextPlayer()" id="test">Test</button>
    </div>
</body>
</html>