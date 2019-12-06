<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<div class="modal fade" id="tipModal" tabindex="-1" role="dialog"
     aria-labelledby="modalLabel" aria-hidden="true" style="width: 229px;margin: 0px auto;">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 229px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="modalLabel">
                    温馨提示
                </h4>
            </div>
            <div class="modal-body">
                <div class="panel">
                    <div class="panel-body">
                        <div class="row" style="margin-bottom: 10px;">
                            <p id="tip">xxxxxxxxxxxxxxxx</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--修改个人信息--%>
<div class="modal fade" id="updateInfoModal" tabindex="-1" role="dialog"
     aria-labelledby="updateModalLabel" aria-hidden="true" style="width: 350px;margin: 0px auto;">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 350px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="updateModalLabel">
                    个人信息
                </h4>
            </div>
            <div class="modal-body">
                <div class="panel">
                    <div class="panel-body">
                        <div class="row" style="margin-bottom: 10px;">
                            <c:if test="${user.photo_address!=null && user.photo_address!=''}">
                                <div class="defalut_avatar"
                                     style="margin: -20px auto 0;width: 100px;height: 100px;background-image: url('${user.photo_address}')"
                                ></div>
                            </c:if>
                            <c:if test="${user.photo_address==null}">
                                <div class="avatar" style="margin: -20px auto 0;width: 100px;height: 100px;"
                                ></div>
                            </c:if>
                            <label for="headpic">
                                <input type="button" id="btn" style="margin-top: 5px;padding: 5px 10px;background: #00c4f0;color: #FFF;
                                border: none;border-radius: 5px;margin-left: 120px;" value="点我上传"><span
                                    id="text"></span>
                                <input type="file" id="headpic" onchange="fileUpload('headpic')">
                            </label>
                        </div>
                        <div class="row" style="margin: 0 auto;">
                            <label>昵称</label>
                            <input type="text" id="nickname" maxlength="7" value="${user.nickname}">
                        </div>
                        <div class="row" style="margin: 0 auto;">
                            <label>年龄</label>
                            <input type="text" id="age" value="${user.age}">
                        </div>
                        <div class="row" style="margin: 0 auto;">
                            <label>电话</label>
                            <input type="text" id="phone" value="${user.phone}">
                        </div>
                        <div class="row" style="margin: 0 auto;">
                            <label>地址</label>
                            <input type="text" id="address" value="${user.address}">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>