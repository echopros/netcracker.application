<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: anyat
  Date: 21.04.2018
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Outgoing requests</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2" style="height:100vh;">
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>


<div class="col-md-10 content">
    <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
    <p>
        <a class="btn btn-primary" data-toggle="collapse" href="/account/friends" role="button">All Friends</a>
        <a class="btn btn-primary" data-toggle="collapse" href="/account/friends/incoming" role="button">Incoming
            requests</a>
        <a class="btn btn-primary" data-toggle="collapse" href="/account/friends/outgoing" role="button">Outgoing
            requests</a>
    </p>
    <h1>${message}</h1>
    <table class="table">
        <c:forEach var="friend" items="${outgoingList}">
            <tr>
                <td><img class="img-circle" style="width: 50px;height: 50px"
                         src="<c:url value="${friend.photo}"/>"></td>
                <td><a href="/account/${friend.id}">${friend.name} ${friend.surname}</a></td>
                <td>
                    <form action="/account/friends/cancel-request" method="POST">
                        <button type="submit"  class="btn btn-success">
                            <input type="hidden" name="friendId" value=${friend.id} />
                            Cancel request </span>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>



</body>
</html>
