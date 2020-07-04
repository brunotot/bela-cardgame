<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bela card game</title>
</head>
<body>
	
	<form method="post">
		<input type="text" name="nickname" placeholder="nickname" required /><br>
		<input type="text" name="roomid" placeholder="room id" /><br>
		<input type="submit" formaction="/create" value="Create room" /><br>
		<input type="submit" formaction="/join" value="Join room" />
	</form>
</body>
</html>