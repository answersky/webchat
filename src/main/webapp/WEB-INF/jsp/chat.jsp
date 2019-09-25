
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!--隐藏信息-->
<input type="hidden" id="username" value="${username}">
<input type="hidden" id="nickname" value="${nickname}">
<input type="hidden" id="roomId" value="${room.id}">


<!--头部-->
<div id="chat-head">
    <div class="chat-avatar"></div>
    <span class="headbar-room-name">${room.room_name}</span>
    <%--在线--%>
    <c:if test="${online!=null && online}">
        <i class="fa fa-desktop" style="color: #069AFF;"></i>
    </c:if>
    <%--不在线--%>
    <c:if test="${online!=null && !online}">
        <i class="fa fa-desktop"></i>
    </c:if>

</div>
<!--聊天框-->
<div id="chat-content${roomId}" class="chat-scroll">
    <c:forEach items="${messages}" var="message">
        <c:if test="${message.username!=username}">
            <!--对方聊天记录 -->
            <div data-flex="dir:left" class="message-list-item">
                <div data-flex="dir:left" data-flex-box="0" class="message-container" style="height: auto">
                    <div data-flex-box="0" data-flex="main:top cross:top" class="avatar-container"
                         style="float: left">
                        <div>
                            <div class="avatar"
                                 style="width: 39px; height: 39px;
                                         background-image: url('${pageContext.request.contextPath}/css/img/toux.jpg');"></div>
                        </div>
                    </div>
                    <div style="padding: 0px 50px; width: 100%; text-align: left;">
                <span class="message-nickname-box" style="display: block;">
                    <span class="message-nickname">
                            ${message.nickname}
                    </span>
                    <span>${message.create_time}</span>
                </span>
                        <div class="message">
                    <span>
                        <%--不包含图片则原样输入html代码--%>
                        <c:if test="${fn:contains(message.msg,'<img')==false}">
                            ${fn:escapeXml(message.msg)}
                        </c:if>
                        <c:if test="${fn:contains(message.msg,'<img')==true}">
                            ${message.msg}
                        </c:if>
                    </span>
                            <div class="triangle-left-outer"></div>
                            <div class="triangle-left-inner"></div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${message.username==username}">
            <!--本人聊天记录 -->
            <div data-flex="dir:right" class="message-list-item">
                <div data-flex="dir:right" data-flex-box="0" class="message-container" style="height: auto">
                    <div data-flex-box="0" data-flex="main:top cross:top" class="avatar-container" style="float: right">
                        <div>
                            <div class="avatar"
                                 style="width: 39px; height: 39px;
                                         background-image: url('${pageContext.request.contextPath}/css/img/toux.jpg');"></div>
                        </div>
                    </div>
                    <div style="padding: 0px 50px; width: 100%; text-align: right;">
                         <span class="message-nickname-box" style="display: block;">
                             <span class="message-nickname"> ${message.nickname}</span>
                             <span>${message.create_time}</span>
                         </span>
                        <div class="message">
                            <span>
                                <c:if test="${fn:contains(message.msg,'<img')==false}">
                                    ${fn:escapeXml(message.msg)}
                                </c:if>
                                <c:if test="${fn:contains(message.msg,'<img')==true}">
                                    ${message.msg}
                                </c:if>
                            </span>
                            <div class="triangle-right-outer"></div>
                            <div class="triangle-right-inner"></div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>

</div>

<%--默认自带的表情icon--%>
<div id="icon" class="icon" style="display: none;">
    <ul>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="懵逼" src="${pageContext.request.contextPath}/css/img/face_icon/1.png">
        </li>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="疑问" src="${pageContext.request.contextPath}/css/img/face_icon/10.png">
        </li>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="美味" src="${pageContext.request.contextPath}/css/img/face_icon/11.png">
        </li>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="没兴趣" src="${pageContext.request.contextPath}/css/img/face_icon/12.png">
        </li>
    </ul>
    <ul>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="发呆" src="${pageContext.request.contextPath}/css/img/face_icon/13.png">
        </li>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="流鼻血" src="${pageContext.request.contextPath}/css/img/face_icon/15.png">
        </li>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="赞" src="${pageContext.request.contextPath}/css/img/face_icon/18.png">
        </li>
    </ul>
</div>
<!--对话输入框-->
<div id="chat-input">
    <div style="clear: both"></div>
    <div>
        <div class="face" onclick="clickIcon(${roomId})"
             style="float: left;margin-left:5px;width: 40px;height: 40px;"></div>
    <div class="input-content" style="float: left;">
        <input onfocus="hideIcon(${roomId},${room.is_group})" id="inputBox"
               onkeyup="sendInfo(${roomId})" onmousedown="readMessage(${roomId})"
               type="text" placeholder="请输入">
    </div>
        <div class="send" onclick="sendBtn(${roomId})"
             style="float: left;margin-left:5px;width: 40px;height: 40px;"></div>
    </div>

</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/clipboard.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var roomId = $("#roomId").val();
        checkScrollStyle(roomId);
        scrollBottom(roomId);
    });


</script>


