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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/game/scoretable.css">
    <link rel="stylesheet" type="text/css" href="css/main/main.css">
    <link rel="stylesheet" type="text/css" href="css/game/gametable.css">
    <link rel="stylesheet" type="text/css" href="css/game/chat.css">
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
					} else {
						button.style.pointerEvents = "none";
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
	<br>
    <div class="wrapper">
        <div class="row">
            <div class="col-md-2 col-sm-1">
                <div class="textarea">
                    <p><span style="color: grey">[18:58:21]</span> <span style="color: blue">test1:</span><br><span style="color: black">This is a message.</span></p>
                    <p><span style="color: grey">[18:58:21]</span> <span style="color: blue">test1:</span><br><span style="color: black">This is a message.</span></p>
                    <p><span style="color: grey">[18:58:21]</span> <span style="color: blue">test1:</span><br><span style="color: black">This is a message.</span></p>
                    <p><span style="color: grey">[18:58:21]</span> <span style="color: blue">test1:</span><br><span style="color: black">This is a message.</span></p>
                    <p><span style="color: grey">[18:58:21]</span> <span style="color: blue">test1:</span><br><span style="color: black">This is a message.</span></p>
                </div>
                <input type="text" id="message" />
                <button id="submitMessageButton" onclick="submitMessage()">X</button>
            </div>
            <div class="col-md-8 col-sm-10">
                <div class="row"><div class="col-md-12 col-sm-12" style="text-align: center"><div class="playerBox autoMargin"><p class="playerBoxP"><%= playerNames[0] %></p></div></div></div><br>

                <div class="row">
                    <div class="col-md-2 col-sm-1 textCenter container-center"><div class="playerBox vertical-center"><p class="playerBoxP"><%= playerNames[1] %></p></div></div>
                    <div id="tableArea" class="col-md-8 col-sm-10">
                        <div class="player0Style"><img src="img/cards/AS_P.png" style="display: none" id="playerCard0"></div>
                        <div class="player12Style">
                            <img style="display: none" id="playerCard1" src="img/cards/AS_P.png">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <img style="display: none" id="playerCard2" src="img/cards/AS_P.png">
                        </div>
                        <div class="player3Style"><img src="img/cards/AS_P.png" style="display: none" id="playerCard3"></div>
                    </div>
                    <div class="col-md-2 col-sm-1 container-center"><div class="playerBox vertical-center"><p class="playerBoxP"><%= playerNames[2] %></p></div></div>
                </div><br>

                <div class="row"><div class="col-md-12 textCenter"><div class="playerBox autoMargin"><p class="playerBoxP"><%= playerNames[3] %></p></div></div></div><br>
            </div>
            <div class="col-md-2 col-sm-1">
                <table id="scoreTable">
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
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div id="playerCards">
                    
                </div>
            </div>
        </div>
    </div>
</body>
</html>