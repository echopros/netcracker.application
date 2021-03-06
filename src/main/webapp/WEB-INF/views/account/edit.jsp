<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 25.04.2018
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
<head>
    <title>Settings</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="google-signin-client_id"
          content="829562763201-3hn7scp4j1c9u7hvlherebi8e56pv9va.apps.googleusercontent.com">
</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2" style="height:100vh;">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>

    <div class="col-md-10 content">
        <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
        <form:form method="POST" modelAttribute="user" action="/account/settings-user" enctype="multipart/form-data">
            <table class="table">
                <tr>
                    <td>Avatar:</td>
                    <td><img id="blah" class="img-circle" style="width: 200px;height: 200px" src="${user.photo}"
                    />
                        <input type="hidden" name="photo" value="${user.photo}"/>
                        <br><span class="btn btn-default btn-file">
    Browse <input type="file" name="photoFile" id="file" accept="image/*" onchange="readURL(this)">
</span>
                        <span class="has-error">${message}</span>
                    </td>
                </tr>
                <tr>
                    <th>Name*:</th>
                    <td>
                        <form:input type="hidden" path="id"/>
                        <form:input path="name" id="name" type="text" class="form-control"
                                    placeholder="Enter event name"/>

                    </td>
                    <td><form:errors path="name" cssClass="error"/></td>

                </tr>
                <tr>
                    <th>Surname*:</th>
                    <td>
                        <form:input path="surname" id="surname" type="text" class="form-control"
                                    placeholder="Enter event name"/>

                    </td>
                    <td><form:errors path="surname" cssClass="error"/></td>
                </tr>
                <tr>
                    <th>Birthday Date</th>
                    <td>
                        <form:input path="birthdayDate" id="dateEnd" type="date" class="form-control dateValid"
                                    placeholder="Enter event end date"/>
                    </td>
                </tr>
                <tr>
                    <th>Phone</th>
                    <td>
                        <form:input name="phone" class="form-control phone"
                                    type="phone"
                                    placeholder="Enter phone" path="phone"/>
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td>
                        <button class="btn btn-success" type="submit">
                            Save
                        </button>
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
</div>
<script src='${contextPath}/resources/js/imageUpload.js'></script>
<!-- Bootstrap core JavaScript-->
<script src='${contextPath}/resources/js/js/languages/jquery.validationEngine-en.js'></script>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
<script src="${contextPath}/resources/js/google.js"></script>
<script src='${contextPath}/resources/js/pamCode.js'></script>

</body>

</html>
