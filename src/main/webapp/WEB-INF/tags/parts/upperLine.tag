<%@ tag
        description="contains info about account and links to login/logout page"
        body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="upperline">
    <header class="centeredWraper">

        <div class="header-title">
            <a class="site-name" href="${pageContext.request.contextPath}">Web Analytics</a>
        </div>

        <div class="header-content">
            <c:choose>
                <c:when test="${ not empty sessionUser}">

                    <div class="header-content-container">
                        <div class="centeredBlock">
                            <label title="Select application to get analytics for" id="select-menu-label">
                                <span class="custom-dropdown custom-dropdown--white">
                                    <select class="custom-dropdown__select custom-dropdown__select--white">
                                        <option>Application #1</option>
                                        <option>Application #2</option>
                                        <option>Application #3</option>
                                    </select>
                                </span>
                            </label>
                        </div>
                    </div>

                    <div class="header-content-container">
                        <div id="avatar" class="centeredBlock"></div>
                    </div>

                    <div class="header-content-container">
                        <span id="user-name">${sessionUser.login}</span>
                    </div>

                    <div class="header-content-container">
                        <a id="exit-link" class="centeredBlock" href="${pageContext.request.contextPath}/account/logout" title="Log out"></a>
                    </div>

                    <c:url value="/account/info" var="accountInfoUrl"/>
                    <%--<a href="${accountInfoUrl}" title="show account page">You are welcome user :)</a>--%>
                </c:when>
                <c:otherwise>
                    <c:url value="/account/login" var="loginUrl"/>
                    <c:url value="/account/register" var="registerUrl"/>
                    <form action="${loginUrl}" method="post" id="login-form">
                        <div class="header-content-container">
                            <input type="text" class="centeredBlock" name="login" placeholder="Login"/>
                        </div>
                        <div class="header-content-container">
                            <input type="password" class="centeredBlock" name="password" placeholder="Password"/>
                        </div>
                        <div class="header-content-container">
                            <input type="submit" class="centeredBlock myButton" value="Login"/>
                        </div>
                        <div class="header-content-container">
                            <a class="header-link" href="${registerUrl}">I have no account</a>
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>

    </header>
</div>