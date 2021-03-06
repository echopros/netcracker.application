<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create New Folder</title>

    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
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
        <div class="card card-register">
            <div class="card-header"></div>
            <div class="card-body">

                <form:form method="POST" modelAttribute="createFolder" class="forms_form">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Folder Name*: </label>
                            <form:input path="name" id="name" type="text" class="form-control"
                                        placeholder="Enter folder name"/>
                            <form:errors path="name" cssClass="error"/>
                        </div>
                        <button id="create" class="btn btn-success text-center">Create</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
