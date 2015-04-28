<%@ taglib prefix="parts" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<parts:header title="Home page" />
</head>
<body>
	<div id="content">
		<h2>This is home page of MVC app</h2>
		<p>Current time is ${currentTime}</p>
		<p>Your session id ${sessionId}</p>
	</div>
</body>
</html>