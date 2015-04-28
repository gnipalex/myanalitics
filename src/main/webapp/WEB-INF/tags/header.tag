<%@ tag description="Includes static resources and title"%>
<%@ attribute name="title" required="true"
	description="Title of the page"%>

<%@ taglib prefix="static" tagdir="/WEB-INF/tags/static"%>

<title>${title}</title>
<static:includeCss />
<static:includeJS />
