<%@ tag description="wraper for page" %>
<%@ taglib prefix="static" tagdir="/WEB-INF/tags/static" %>
<%@ taglib prefix="parts" tagdir="/WEB-INF/tags/parts" %>
<%@ attribute name="title" required="true"
	description="Title of the page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${title}</title>
<static:includeCss />
<static:includeJS />
</head>
<body>
	<parts:upperLine />
	<div id="content"  class="centeredWraper">
		<jsp:doBody />
	</div>
</body>
</html>


