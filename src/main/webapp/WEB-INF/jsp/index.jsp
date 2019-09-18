<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/font-awesome-4.7.0/font-awesome-4.7.0/css/font-awesome.css">
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
                <div>
                    <i class="fa fa-search" id="i-advanced-search-i" onclick="searchUser()"></i>
                    <input type="text" id="i-advanced-search" placeholder="输入关键词检索朋友">
                </div>
                <div id="search_result">
                    <ul class="friendlist-ul">
                        <div class="chatRoom">

                        </div>
                    </ul>
                </div>
                <span>已加入列表</span>
            </div>

            <div class="friendlist-list-box">
                <ul class="friendlist-ul">
                    <div class="chatRoom">
                        <c:forEach items="${rooms}" var="room">
                            <li class="room room_select">
                                <div style="height: 50px;width:215px">
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
        <c:if test="${rooms!=null && rooms.size()>0}">
            <%@ include file="chat.jsp" %>
        </c:if>
    </div>
</div>

<%--提示框--%>
<%@ include file="tip.jsp" %>
</body>
</html>

