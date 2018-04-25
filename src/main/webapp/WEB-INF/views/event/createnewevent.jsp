<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create New Event </title>

    <!-- Custom styles for this template-->    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

</head>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>


<div class="col-md-9 content">
    <div class="card card-register mx-auto mt-5 col-md-6">
        <div class="card-header">Create New Event</div>
        <div class="card-body">
            <%--action="/account/eventList/createNewEvent"--%>
            <form:form method="POST" modelAttribute="createNewEvent" class="forms_form">
                <div class="form-group">
                    <label>Event Name: </label>
                    <form:input path="name" id="name" type="text" class="form-control" placeholder="Enter event name"/>
                    <form:errors path="name" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Description: </label>
                    <form:input path="description" id="description" type="text" class="form-control"
                                placeholder="Enter event description"/>
                    <form:errors path="description" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Start_date: </label>
                    <form:input path="dateStart" id="dateStart" type="date" class="form-control"
                                placeholder="Enter event start date"/>
                    <form:errors path="dateStart" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>End_date: </label>
                    <form:input path="dateEnd" id="dateEnd" type="date" class="form-control"
                                placeholder="Enter event end date"/>
                    <form:errors path="dateEnd" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label>Event type: </label>
                    <form:select path="type" class="form-control">
                        <form:options items="${eventTypes}" itemValue="typeId" itemLabel="type"/>
                    </form:select>
                    <form:errors path="type" cssClass="error"/>
                </div>

                <form:input path="width" type="hidden" id="latitude"></form:input>
                <form:input path="longitude" type="hidden" id="longitude"></form:input>
                <div class="form-group">
                    <label>Event place</label>
                    <form:input path="eventPlaceName" id="eventPlaceName" type="text" class="form-control"/>
                    <form:errors path="eventPlaceName" cssClass="error"/>
                    <div id="map"></div>
                    <script src='${contextPath}/resources/js/pamCode.js'></script>
                    <script
                            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAFJb-oxFvvvPRvwubCZwYkPQC0rRUbtOM&callback=initMap&language=en">
                    </script>
                    <script>
                        setMarkerFromInput();
                    </script>
                </div>
                <input id="hidden" name="hidden" type="hidden" value="">
                <button id="create" class="btn btn-dark text-center">Create</button>
                <button id="draft" class="btn btn-dark text-center">Draft</button>
            </form:form>
        </div>
    </div>
</div>
<script>
    var hid = document.getElementById('hidden');
    var create = document.getElementById('create');
    var draft = document.getElementById('draft');
    create.onclick = function () {
        hid.value = false;
    };
    draft.onclick = function () {
        hid.value = true;
    };
</script>
</html>
