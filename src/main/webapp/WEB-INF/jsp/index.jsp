<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/sockjs.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/chat.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/websockt_heart_beat.js"></script>
    <title>webchat</title>
</head>
<body>
<div id="App">
    <div class="outer">
        <div class="left-box">
            <div class="selfinfo-avatar">
                <div class="avatar"></div>
            </div>
        </div>
        <div class="member-box">
            <div class="member-list">
                <span>已加入列表</span>
            </div>

            <div class="friendlist-list-box">
                <ul class="friendlist-ul">
                    <div class="chatRoom">
                        <c:forEach items="${rooms}" var="room">
                            <li class="room room_select">
                                <div style="height: 50px;width: 160px">
                                    <div class="avatar heardPic"></div>
                                    <div style="padding: 15px 0 18px 60px;font-weight: 200;">
                                        <span>${room.room_name}</span></div>
                                </div>
                            </li>
                        </c:forEach>
                    </div>
                </ul>
            </div>
        </div>
    </div>

    <div class="chat-box">
        <%@ include file="chat.jsp" %>
    </div>
</div>
</body>
</html>

