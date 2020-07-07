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
    <link rel="stylesheet" type="text/css" href="css/game/scoretable.css">
    <link rel="stylesheet" type="text/css" href="css/main/main.css">
    <link rel="stylesheet" type="text/css" href="css/game/gametable.css">
    <style>
    	img {
    		border: 2px solid transparent;
    	}
    	img:hover {
    		border: 2px solid green;
    		cursor: pointer;
    	}
    </style>
	<script>
		window.onload = function () {
			loadCards();
// 			var b = document.getElementById('test');
<%-- 			if (<%= nickname.equals(playerNames[3]) %>) { --%>
// 				b.disabled = false;
// 			}
		}
		var subscription = null;
		var stompClient = null;
		connect();
		
		function playCard(card) {
			console.log(card);
		}
		
		function connect() {
		    var socket = new SockJS('/websocket');
		    stompClient = Stomp.over(socket);
		    stompClient.connect({}, function (frame) {
		    	stompClient.subscribe('/topic/game/<%= roomId %>', function (room) {
		    		var button = document.getElementById('playerCards');
					var r = JSON.parse(room.body);
					if (r.playerToMove.nickname.localeCompare('<%= nickname %>') === 0) {
						$('playerCards').click(true);
						button.style.pointerEvents = "auto";
						button.style.border = "2px solid green";
					} else {
						button.style.pointerEvents = "none";
						button.style.border = "2px solid blue";
					}
		    	});
				stompClient.send("/app/msgGame/<%= roomId %>", {});
			});
		}
		
		function loadCards() {
			var cards = JSON.parse('<%= Helper.getCards(request, roomId, nickname) %>');
			for (var x in cards) {
				var card = cards[x];
				var imagePath = "img/cards/";
				var imageNode = document.createElement("IMG");
				let suit = card.suit;
				let rank = card.rank;
				if (card.hidden) {
					imageNode.src = imagePath + "SPUSTENA.png";	
				} else {
					imageNode.src = imagePath + card.rank + "_" + card.suit.charAt(0) + ".png";
					imageNode.onclick = function() {
						moveToNextPlayer();
					}
				}
				document.getElementById('playerCards').appendChild(imageNode);
			}
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
	<style>
		div.inline { float:left; }
		.clearBoth { clear:both; }
	</style>
</head>
<body>
	<table id="tablica">
	    <tr id="tableHeader">
	        <td>Rezultat</td>
	        <td>Mi</td>
	        <td>Vi</td>
	    </tr>
	    <tr id="currentPrimePoints">
	        <td>Zvanja</td>
	        <td id="currentPrimePointsTeamA"></td>
	        <td id="currentPrimePointsTeamB"></td>
	    </tr>
	    <tr id="currentPoints">
	        <td>Trenutno dijeljenje</td>
	        <td id="currentPointsTeamA"></td>
	        <td id="currentPointsTeamB"></td>
	    </tr>
	    <tr id="currentTotal">
	        <td>Ukupno</td>
	        <td id="currentTotalPointsTeamA"></td>
	        <td id="currentTotalPointsTeamB"></td>
	    </tr>
	    <tr id="total">
	        <td>Rezultat</td>
	        <td id="totalPointsTeamA"></td>
	        <td id="totalPointsTeamB"></td>
	    </tr>
	</table>
	
	<div id="gameTable">
        <div id="playerTable">
            <div id="p1">
                <p><%= playerNames[0] %></p>
                <img id="p1card">
            </div>
            
            <div id="p2p3container">
                <div id="p2container">
                    <p><%= playerNames[1] %></p>
                    <img id="p2card">
                </div>
                <div id="p3container">
                    <img id="p3card">
                    <p><%= playerNames[2] %></p>
                </div>
            </div>

            <div id="p4">
                <img id="p4card">
                <p><%= playerNames[3] %></p>
            </div>
        </div>
        <div id="playerCards" style="padding-bottom: 2px">

        </div>
    </div>
</body>
</html>