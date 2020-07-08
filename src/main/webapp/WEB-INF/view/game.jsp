<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.brunotot.belacardgame.util.*" %>
<%@ page import="com.brunotot.belacardgame.*" %>
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
			//console.log(card);
		}
		
		function connect() {
		    var socket = new SockJS('/websocket');
		    stompClient = Stomp.over(socket);
		    stompClient.connect({}, function (frame) {
		    	stompClient.subscribe('/topic/loadAllCards/<%= roomId %>', function (room) {
		    		var r = JSON.parse(room.body);
		    		var callerNickname = r.caller.nickname;
		    		var calledAdut = r.adut;
		    		document.getElementById('zvanje').style.display = 'inherit';
		    		document.getElementById('callerNickname').innerHTML = callerNickname;
		    		document.getElementById('calledAdut').src = "img/cards/" + calledAdut + ".png";
		    		loadCards();
		    	});
		    	stompClient.subscribe('/topic/game/<%= roomId %>', function (room) {
		    		var playerCards = document.getElementById('playerCards');
		    		var chooseAdut = document.getElementById('chooseAdut');
					var zvanjeDiv = document.getElementById('chooseZvanje');
					var r = JSON.parse(room.body);
					if (r.playerToMove.nickname.localeCompare('<%= nickname %>') === 0) {
						var currentState = r.currentState;
						if (currentState === <%= Constants.BIRANJE_ADUTA %>) {
							playerCards.style.pointerEvents = "none";
							chooseAdut.style.pointerEvents = "auto";
							chooseAdut.style.display = "inherit";
							var herc = document.getElementById('herc');
							var pik = document.getElementById('pik');
							var karo = document.getElementById('karo');
							var tref = document.getElementById('tref');
							var dalje = document.getElementById('dalje');
							herc.onclick = function () { moveToNextPlayer('<%= CardSuit.HERC %>'); };
							pik.onclick = function () { moveToNextPlayer('<%= CardSuit.PIK %>'); };
							karo.onclick = function () { moveToNextPlayer('<%= CardSuit.KARO %>'); };
							tref.onclick = function () { moveToNextPlayer('<%= CardSuit.TREF %>'); };
							dalje.onclick = function () { moveToNextPlayer('0'); };
						} else if (currentState === <%= Constants.BIRANJE_ZVANJA %>) {
							chooseZvanje.style.pointerEvents = "auto";
							chooseZvanje.style.display = "inherit";
							
							playerCards.style.pointerEvents = "auto";
							
							chooseAdut.style.pointerEvents = "none";
							chooseAdut.style.display = "none";
							var zovi = document.getElementById('zovi');
							var dalje2 = document.getElementById('dalje2');
							zovi.onclick = function () { moveToNextPlayer('zovi'); };
							dalje2.onclick = function () { moveToNextPlayer('dalje'); };
						} else {
							playerCards.style.pointerEvents = "auto";
						}
					} else {
						chooseZvanje.style.pointerEvents = "none";
						chooseZvanje.style.display = "none";
						chooseAdut.style.display = "none";
						chooseAdut.style.pointerEvents = "none";
						playerCards.style.pointerEvents = "none";
					}
		    	});
				stompClient.send("/app/msgGame/<%= roomId %>", {});
			});
		}
		
		function loadCards() {
			document.getElementById('playerCards').innerHTML = "";
			$.ajax({
                url: '/game/getCards',
                type: 'post',
                data: {
                	'roomId': '<%= roomId %>',
                	'nickname': '<%= nickname %>'
                },
                success:function(data) { 
                	var cards = JSON.parse(data);
        			for (var x in cards) {
        				var card = cards[x];
        				var imagePath = "img/cards/";
        				var imageNode = document.createElement("IMG");
        				imageNode.className = "customCard";
        				let suit = card.suit;
        				let rank = card.rank;
        				if (card.hidden) {
        					imageNode.src = imagePath + "SPUSTENA.png";	
        				} else {
        					imageNode.src = imagePath + card.rank + "_" + card.suit.charAt(0) + ".png";
        					imageNode.onclick = function() {
        						moveToNextPlayer(rank + "." + suit, this);
        					}
        				}
        				document.getElementById('playerCards').appendChild(imageNode);
        			}
                }
			});
		}
		
		function moveToNextPlayer(event, imageNode) {
			$.ajax({
                url: '/game/isStateZvanje',
                type: 'post',
                data: {
                	'roomId': '<%= roomId %>'
                },
                success:function(data) { 
					if (data === "true" && event !== "dalje" && event !== "zovi") {
						if (imageNode.style.marginTop !== "-20px") {
							imageNode.style.marginTop = "-20px";
						} else {
							imageNode.style.marginTop = "0";
						}
					} else {
						var children = document.getElementsByClassName('customCard');
						for (i = 0; i < children.length; i++) {
							children[i].style.marginTop = "0";
						}
						$.ajax({
			                url: '/game/moveToNextPlayer',
			                type: 'post',
			                data: {
			                	'roomId': '<%= roomId %>',
			                	'nickname': '<%= nickname %>',
			                	'event': event
			                },
			                success:function(data) { 
								if (data) {
									stompClient.send("/app/msgGame/<%= roomId %>", {});
								}
			                }
						});
					}
                }
			});
		}
	</script>
	<style>
        .adut {
            width: 95%;
            margin: 0;
            position: absolute;
            top: 50%;
            -ms-transform: translateY(-50%);
            transform: translateY(-50%);
        }

        .customButton {
            cursor: pointer;
        }
        
        .customButton:hover {
            border: 1px solid green;
            border-radius: 10px;
        }

        .borderColorBlack {
            border: 1px solid black;
            border-radius: 10px; 
        }

        .daljeButton {
            width: 80px; 
            padding: 4px; 
            margin: -10px auto 0 auto;
        }
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
                    	<div id="zvanje" style="display: none; position: absolute; bottom: 15px; left: 15px; margin: auto">
                    		<span id="callerNickname"></span>: <img id="calledAdut">
                    	</div>
                        <div style="display: none" class="adut" id="chooseAdut">
                            <img id="herc" src="img/cards/HERC.png">
                            <img id="pik" src="img/cards/PIK.png">
                            <img id="karo" src="img/cards/KARO.png">
                            <img id="tref" src="img/cards/TREF.png">
                            <p style="margin-top: 4px">Odaberi aduta</p>
                            <div id="dalje" class="customButton borderColorBlack daljeButton">DALJE</div>
                        </div>
                        <div style="display: none" class="adut" id="chooseZvanje">
                        	<p>Odaberi zvanje</p>
                            <div style="display: inline" id="zovi" class="customButton borderColorBlack daljeButton">ZOVI</div>
                            <div style="display: inline" id="dalje2" class="customButton borderColorBlack daljeButton">DALJE</div>
                        </div>
                        <div class="player0Style"><img src="img/cards/AS_P.png" style="display: none" id="playerCard0"></div>
                        <div class="player12Style">
                            <img style="display: none" id="playerCard1">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <img style="display: none" id="playerCard2">
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