<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%
	String roomId = (String) request.getAttribute("roomId");
	String nickname = (String) request.getAttribute("nickname");
%>
<head>
<meta charset="ISO-8859-1">
<title>Room</title>
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>  
<script>
	var stompClient = null;

	window.onload = function() {
		connect();
	}

	function connect() {
	    var socket = new SockJS('/websocket');
	    stompClient = Stomp.over(socket);
	    stompClient.connect({nickname: "<%= nickname %>"}, function (frame) {
	        console.log('Connected: ' + frame);
	        $.ajax({
                url: '/game/start',
                type: 'post',
                data: {
                	'roomId': '<%= roomId %>',
                	'nickname': '<%= nickname %>'
                },
                success:function(data) { 
                	if (data) {
                		stompClient.subscribe('/topic/greetings/<%= roomId %>', function (greeting) {

                		});
	        		    stompClient.send("/app/hello/<%= roomId %>", {'nickname': '<%= nickname %>'});
	        		    	        		    
	        		    stompClient.subscribe('/topic/updateRoom/<%= roomId %>', function (room) {
							var r = JSON.parse(room.body);
							if (r.player1) document.getElementById('p1').innerHTML = "<span style='color: green'>" + r.player1.nickname + "</span>";
							else document.getElementById('p1').innerHTML = "<span style='color: red'>[waiting for player...]</span>";
							
							if (r.player2) document.getElementById('p2').innerHTML = "<span style='color: green'>" + r.player2.nickname + "</span>";
							else document.getElementById('p2').innerHTML = "<span style='color: red'>[waiting for player...]</span>";
							
							if (r.player3) document.getElementById('p3').innerHTML = "<span style='color: green'>" + r.player3.nickname + "</span>";
							else document.getElementById('p3').innerHTML = "<span style='color: red'>[waiting for player...]</span>";
							
							if (r.player4) document.getElementById('p4').innerHTML = "<span style='color: green'>" + r.player4.nickname + "</span>";
							else document.getElementById('p4').innerHTML = "<span style='color: red'>[waiting for player...]</span>";
	        	        	
	        		    	if (r.player1) {
	        		    		if (r.player1.nickname.localeCompare('<%= nickname %>') === 0) {
	        		    			if (r.player2 && r.player3 && r.player4) {
	        		    				var append = document.createElement('BUTTON');
	        		    				append.innerHTML = "Start game";
	        		    				append.onclick = function () {
	        		    					console.log("test");
	        		    				}
	        		    				var node = document.getElementById('buttons');
	        		    				node.appendChild(append);
	        		    			} else {
	        		    				var list = document.getElementById('buttons');
	        		    				if (list.childNodes[2]) {
	        		    					list.removeChild(list.childNodes[2]);
	        		    				}
	        		    			}
	        		    		}
	        		    	}
	        		    
	        		    });
	        		    stompClient.send("/app/hello3/<%= roomId %>", {});
                	}
                }
            });
	        
	    });
	}
	
	function disconnect() {
		if (stompClient !== null) {
	        stompClient.disconnect();
	    }
		location.href = "http://localhost:8080/";
	}
	
	
	
</script>
</head>
<body>

	<div style="text-align: center; width: 100%;">
		<h1 style="text-align: center">
			Room: <%= roomId %>
		</h1>
		<h3 style="text-align: center">
			Player: <%= nickname %>
		</h3>
	</div>

	<div id="players" style="border: 1px solid black">
    	<div style="padding: 10px; border: 1px solid black; text-align: center"><p id="p1"></p></div>
    	<div style="padding: 10px; border: 1px solid black; text-align: center"><p id="p2"></p></div>
    	<div style="padding: 10px; border: 1px solid black; text-align: center"><p id="p3"></p></div>
    	<div style="padding: 10px; border: 1px solid black; text-align: center"><p id="p4"></p></div>
    </div>
	<br>
	<div id="buttons" style="width: 100%; text-align: center">
		<button onclick="disconnect()">Disconnect</button>
	</div>
</body>
</html>