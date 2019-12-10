<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<ul class="friendlist-ul">
    <div class="chatRoom" style="margin-left: 5px;">
        <c:if test="${members!=null && members.size()>0}">
            <c:forEach items="${members}" var="member">
                <li class="room room_select" id="room" style="background: #f8f9fa;margin-top: 5px;float: none;">
                    <div style="height: 50px;width:215px">
                        <div class="avatar heardPic"></div>
                        <div style="padding: 15px 0 18px 60px;font-weight: 200;">
                        <span style="float: left">
                                ${member.nickname}
                        </span>
                                <%--在线--%>
                            <c:if test="${member.online}">
                                <i class="fa fa-desktop" style="color: #069AFF;"></i>
                            </c:if>
                                <%--不在线--%>
                            <c:if test="${!member.online}">
                                <i class="fa fa-desktop"></i>
                            </c:if>
                                <%--群主--%>
                            <c:if test="${member.is_admin==2}">
                                <i class="fa fa-cogs groupMembericon" style="color: #069AFF;"></i>
                            </c:if>
                                <%--管理员--%>
                            <c:if test="${member.is_admin==1}">
                                <c:if test="${is_admin}">
                                    <i class="fa fa-times fa-lg groupMembericon" style="color: #ff0000;"></i>
                                </c:if>
                                <i class="fa fa-cog groupMembericon" style="color: #069AFF;"></i>
                            </c:if>
                                <%--普通成员--%>
                            <c:if test="${member.is_admin==0}">
                                <c:if test="${is_admin}">
                                    <i class="fa fa-times fa-lg groupMembericon" style="color: #ff0000;"></i>
                                </c:if>
                                <i class="fa fa-user groupMembericon" style="color: #069AFF;"></i>
                            </c:if>
                        </div>
                        <div style="clear: both"></div>
                    </div>
                </li>
            </c:forEach>
        </c:if>
    </div>
</ul>

