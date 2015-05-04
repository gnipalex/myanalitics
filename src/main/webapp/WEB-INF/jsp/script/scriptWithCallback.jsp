<%@ page contentType="text/javascript; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty callback}">
function ${callback} () {
	return ${resultMap} ;
}
</c:if>