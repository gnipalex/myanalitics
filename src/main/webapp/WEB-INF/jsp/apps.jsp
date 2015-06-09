<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<tags:page title="Applications">

    <div id="tab-panel">
        <ul>
            <li class="active-tab">
                <a href="${pageContext.request.contextPath}/apps">Manage Applications</a>
            </li>
            <li>
                <a href="#">Second page</a>
            </li>
            <li>
                <a href="#">Third page</a>
            </li>
            <li>
                <a href="#">A very very long tab name</a>
            </li>
        </ul>
    </div>
    <article id="article-content">

        <h2>Here is the list of your applications</h2>

        <table border="1" id="apps-table">
            <tr>
                <th>№</th>
                <th>Name</th>
                <th>Domain</th>
                <th>Session break page</th>
                <th>Session TTL, min</th>
                <th>Cookie TTL, min</th>
                <th>Send activity in, sec</th>
                <th>Collecting activity on page</th>
            </tr>
            <tr>
                <td>1</td>
                <td>Vkontakte</td>
                <td>https://vk.com</td>
                <td>/logout</td>
                <td>10</td>
                <td>5</td>
                <td>30</td>
                <td>true</td>
            </tr>
        </table>

        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    </article>
</tags:page>