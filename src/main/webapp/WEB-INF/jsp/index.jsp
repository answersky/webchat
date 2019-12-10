<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<head>
    <link rel="icon" href="${pageContext.request.contextPath}/css/img/bee.jpg" type="image/x-icon"/>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/init_chat_data.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/websockt_heart_beat.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/pic_upload.js"></script>
    <title>webchat</title>
</head>
<body>
<div id="App">
    <div class="outer">
        <div class="left-box">
            <div class="selfinfo-avatar">
                <c:if test="${user.photo_address!=null && user.photo_address!=''}">
                    <div class="defalut_avatar" style="background-image: url('${user.photo_address}')"
                         onclick="showUserInfo()"></div>
                </c:if>
                <c:if test="${user.photo_address==null}">
                    <div class="avatar" onclick="showUserInfo()"></div>
                </c:if>
            </div>
            <div class="login_out">
                <i class="fa fa-power-off fa-2x"></i>
            </div>
        </div>
        <div class="member-box">
            <div class="member-list">
                <div>
                    <i class="fa fa-search" id="i-advanced-search-i" onclick="searchUser()"></i>
                    <input type="text" id="i-advanced-search" placeholder="输入关键词检索朋友" onfocus="hideSearchResult()">
                </div>
                <div id="search_result">
                    <ul class="friendlist-ul">
                        <div class="chatRoom">

                        </div>
                    </ul>
                </div>
                <span>已加入列表</span>
            </div>

            <%--左侧聊天列表--%>
            <div class="friendlist-list-box">

            </div>
        </div>
    </div>

    <div class="chat-box">

    </div>
    <div class="right-info">

    </div>
</div>

<%--提示框--%>
<%@ include file="tip.jsp" %>
</body>
</html>

