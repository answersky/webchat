<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!--隐藏信息-->
<input type="hidden" id="username" value="${username}">
<input type="hidden" id="timeStr" value="${timeStr}">


<!--头部-->
<div id="chat-head">
    <div class="chat-avatar"></div>
    <span class="headbar-room-name">${username}</span>
</div>
<!--聊天框-->
<div id="chat-content${roomId}" style="padding-bottom: 2px;min-height: 850px;">
    <c:forEach items="${maps}" var="map">
        <c:if test="${fn:split(map.key,'_')[0]!=username}">
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
                            ${fn:split(map.key,"_")[0]}
                    </span>
                    <span>${time}</span>
                </span>
                        <div class="message">
                    <span>
                            ${map.value}
                    </span>
                            <div class="triangle-left-outer"></div>
                            <div class="triangle-left-inner"></div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        </c:forEach>


    <!--本人聊天记录 -->
   <%-- <div data-flex="dir:right" class="message-list-item">
        <div data-flex="dir:right" data-flex-box="0" class="message-container" style="height: auto">
            <div data-flex-box="0" data-flex="main:top cross:top" class="avatar-container" style="float: right">
                <div>
                    <div class="avatar"
                         style="width: 39px; height: 39px; background-image: url('http://oajmk96un.bkt.clouddn.com/lolo.jpg');"></div>
                </div>
            </div>
            <div style="padding: 0px 50px; width: 100%; text-align: right;">
                <span class="message-nickname-box" style="display: block;">
                    <span class="message-nickname">xcf</span>
                    <span>15:18:05</span>
                </span>
                <div class="message">
                    <span>ssssssddddddddddddddddddddddd</span>
                    <div class="triangle-right-outer"></div>
                    <div class="triangle-right-inner"></div>
                </div>
            </div>
        </div>
    </div>--%>

</div>

<div id="icon${roomId}" class="icon" style="display: none;">
    <ul>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="懵逼" src="${pageContext.request.contextPath}/css/img/1.png">
        </li>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="疑问" src="${pageContext.request.contextPath}/css/img/10.png">
        </li>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="美味" src="${pageContext.request.contextPath}/css/img/11.png">
        </li>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="没兴趣" src="${pageContext.request.contextPath}/css/img/12.png">
        </li>
    </ul>
    <ul>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="发呆" src="${pageContext.request.contextPath}/css/img/13.png">
        </li>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="流鼻血" src="${pageContext.request.contextPath}/css/img/15.png">
        </li>
        <li class="faceLi" onclick="sendImg(this,${roomId})">
            <img title="赞" src="${pageContext.request.contextPath}/css/img/18.png">
        </li>
    </ul>
</div>
<!--对话输入框-->
<div id="chat-input${roomId}">
    <div style="height: 32px">
        <div class="face" onclick="clickIcon(${roomId})"
             style="float: left;margin-left:5px;width: 40px;height: 40px;"></div>
    <div class="input-content" style="float: left;">
        <input onfocus="hideIcon()" id="inputBox${roomId}"
               onkeyup="sendInfo(${roomId})"
               type="text" placeholder="请输入">
    </div>
        <div class="send" onclick="sendBtn(${roomId})"
             style="float: left;margin-left:5px;width: 40px;height: 40px;"></div>
    </div>

    <%--<p id="inputBox" style="width:980px; height:100px; border:1px solid #dddddd;background-color: #ffffff" contenteditable="true"></p>--%>

</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("div.chat-box").click(function () {
            $("#icon").hide();
        });

        $("div.member-box").click(function () {
            $("#icon").hide();
        });
    });


</script>


