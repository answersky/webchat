<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>webchat</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/css/login_css.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#login_sub").click(function () {
                login();
            });
            $("#sign_up").click(function () {
                window.location.href = "/sign_up";
            })
        });

        /*回车事件*/
        function EnterPress(e) { //传入 event
            var e = e || window.event;
            if (e.keyCode == 13) {
                login();
            }
        }

        function login() {
            var username = $("#name").val();
            var password = $("#pwd").val();
            if (username.trim().length == 0) {
                $("#tip").text("用户名不能为空");
                $("#tipModal").modal();
                $("#name").focus();
                return;
            }
            if (password.trim().length == 0) {
                $("#tip").text("密码不能为空");
                $("#tipModal").modal();
                $("#pwd").focus();
                return;
            }
            $.ajax({
                type: "post",
                url: "/toLogin",
                data: {
                    username: username,
                    password: password
                },
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: function (data) {
                    if (data.status == '0') {
                        window.location.href = "/index";
                    } else {
                        $("#tip").text(data.message);
                        $("#tipModal").modal();
                    }
                }
            });
        }
    </script>
</head>
<body>
<div id="login_center">
    <div id="login_area">
        <div id="login_box">
            <div id="login_form">
                <form id="submitForm" class="form-group" method="post">
                    <div id="login_tip">
                        <span id="login_err" class="sty_txt2"></span>
                    </div>

                    <div>
                        用户名：<input type="text" name="username" class="username form-control" id="name">
                    </div>

                    <div>
                        密<span style="margin-left: 15px"></span>码：<input type="password" name="password"
                                                                         class="pwd form-control"
                                                                         id="pwd"
                                                                         onkeypress="EnterPress(event)"
                                                                         onkeydown="EnterPress()">
                    </div>

                    <div id="datesheet_box">
                        <input type="checkbox" class="form-control" name=""
                               style="width: 15px;float: left;margin-top:-1px;">

                        <div class="datesheet_n">&nbsp;自动登录
                            <div class="datesheet_n" style="float:right;margin-right: 115px;">忘记密码
                            </div>
                        </div>
                    </div>
                    <div id="btn_area">
                        <input type="button" class="login_btn" id="login_sub" value="登  录">
                        <input type="button" class="login_btn" id="sign_up" value="注  册">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../tip.jsp" %>
</body>
</html>
