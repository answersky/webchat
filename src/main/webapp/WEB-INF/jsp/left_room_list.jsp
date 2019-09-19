<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<ul class="friendlist-ul">
    <div class="chatRoom">
        <c:forEach items="${rooms}" var="room">
            <li class="room room_select" id="room_${room.id}" onclick="showChatMessage(${room.id})">
                <div style="height: 50px;width:215px">
                    <div class="avatar heardPic"></div>
                    <div style="padding: 15px 0 18px 60px;font-weight: 200;">
                        <span style="float: left">
                                ${room.room_name}
                        </span>
                        <div class="circle"></div>
                    </div>
                    <div style="clear: both"></div>
                </div>
            </li>
        </c:forEach>
    </div>
</ul>

