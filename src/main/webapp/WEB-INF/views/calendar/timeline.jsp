<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 24.04.2018
  Time: 02:29
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'/>
    <title>Calendar</title>
    <link href='${contextPath}/resources/calendar/css/fullcalendar.min.css' rel='stylesheet'/>
    <script src='${contextPath}/resources/calendar/js/moment.min.js'></script>
    <script src='${contextPath}/resources/calendar/js/jquery.min.js'></script>
    <script src='${contextPath}/resources/calendar/js/fullcalendar.min.js'></script>
    <script>

        $(document).ready(function () {

            $('#calendar').fullCalendar({
                themeSystem: 'bootstrap3',
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'agendaWeek,agendaDay,listMonth'
                },
                columnHeaderFormat: 'dddd D',
                slotLabelFormat: 'HH:mm',
                timeFormat: 'HH:mm',
                editable: false,
                defaultView: 'agendaWeek',
                eventLimit: true, // allow "more" link when too many events
                events: function (start, end, timezone, callback) {
                    $.ajax({
                        url: '/account/getTimeline',
                        dataType: 'json',
                        data: {
                            // our hypothetical feed requires UNIX timestamps
                            checkedFriends: JSON.stringify(${checkedFriends}),
                            start: start.unix(),
                            end: end.unix()
                        },
                        success: function (doc) {
                            callback(doc);
                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                            console.log(${checkedFriends})
                            console.log(start);
                            console.log(end);
                            console.log(xhr);
                            console.log(ajaxOptions);
                            console.log(thrownError);
                            alert(xhr.status);
                            alert(thrownError);
                        }
                    });
                },
            });

        });

    </script>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2" style="height:100vh;">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>
    <div class="col-md-10 content">
        <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
        <div class="row">
            <div class="col-md-8">
                <div id='calendar'></div>
            </div>
            <div class="col-md-3">
                <form action="/account/timeline" method="post">
                    <input class="btn btn-success" type="submit" value="Submit">
                    <ul class="list-group timelineList">
                        <c:forEach items="${list_friends}" var="friend">
                            <li class="list-group-item"><input type="checkbox" name="checkedFriends"
                                                               value="${friend.id}"> ${friend.name} ${friend.surname}<BR>
                            </li>
                        </c:forEach>
                    </ul>
                </form>

            </div>
        </div>
    </div>
</div>

<script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
</body>
</html>


