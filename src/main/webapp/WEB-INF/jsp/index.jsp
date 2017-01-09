<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/chat.js"></script>
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
                        <li class="room room_select">
                            <div style="height: 50px;width: 160px">
                                <div class="avatar heardPic"></div>
                                <div style="padding: 15px 0 18px 60px;font-weight: 200;"><span>chat</span></div>
                            </div>
                        </li>
                        <%--<li class="room">
                            <div style="height: 50px;width: 160px">
                                <div class="avatar heardPic"></div>
                                <div style="padding: 15px 0 18px 60px;font-weight: 200;"><span>DASDAS</span></div>
                            </div>
                        </li>
                        <li class="room">
                            <div style="height: 50px;width: 160px">
                                <div class="avatar heardPic"></div>
                                <div style="padding: 15px 0 18px 60px;font-weight: 200;"><span>DASDAS</span></div>
                            </div>
                        </li>
                        <li class="room">
                            <div style="height: 50px;width: 160px">
                                <div class="avatar heardPic"></div>
                                <div style="padding: 15px 0 18px 60px;font-weight: 200;"><span>DASDAS</span></div>
                            </div>
                        </li>--%>
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

