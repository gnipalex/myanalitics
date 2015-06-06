<%@ tag
	description="contains info about account and links to login/logout page"
	body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="upperline">
	<div class="centeredWraper">
	<table>
		<tr>
			<c:choose>
				<c:when test="${ not empty sessionUser }">
					<td>
						<c:url value="/account/info" var="accountInfoUrl"/>
						<a href="${accountInfoUrl}" title="show account page">You are welcome user :)</a>
					</td>
					<td>
						<c:url value="/account/logout" var="logoutUrl"/>
						<a href="${logoutUrl}">Sign out</a>
					</td>
				</c:when>
				<c:otherwise>
					<td style="margin-right: 50px;">Please sign in!</td>
						<td>
							<c:url value="/account/login" var="loginUrl"/>
							<form action="${loginUrl}" method="post">
								<input type="text" name="login" placeholder="login" /> 
								<input type="password" name="password" placeholder="password" /> 
								<input type="submit" value="go" />
							</form>
						</td>
						<td>
						<c:url value="/account/register" var="registerUrl"/>
						<a href="${registerUrl}">I have no account</a>
					</td>
				</c:otherwise>
			</c:choose>
		</tr>
	</table>
	</div>
</div>