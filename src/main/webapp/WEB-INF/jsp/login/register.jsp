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
            $("#register").click(function () {
                register();
            })
        });

        function register() {
            var username = $("#name").val();
            var password = $("#pwd").val();
            var phone = $("#phone").val();
            var nickname = $("#nickname").val();
            var age = $("#age").val();
            var address = $("#address").val();
            if (username.trim().length == 0) {
                $("#tip").text("用户名不能为空");
                $("#tipModal").modal();
                $("#name").focus();
                return;
            }
            if (username.length < 2) {
                $("#tip").text("用户名长度至少是2个字符");
                $("#tipModal").modal();
                $("#name").focus();
                return;
            }
            //验证用户名  只能是字母数字下划线组成
            var reg = /^[_a-zA-Z0-9]+$/;
            if (!reg.test(username)) {
                $("#tip").text("用户名只能是字母数字下划线组成");
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
            if (nickname.trim().length == 0) {
                $("#tip").text("昵称不能为空");
                $("#tipModal").modal();
                $("#nickname").focus();
                return;
            }
            //验证手机号格式
            if (phone.trim().length > 0) {
                var regex = /^1[3456789]\d{9}$/;
                if (!regex.test(phone)) {
                    $("#tip").text("手机号格式不正确");
                    $("#tipModal").modal();
                    $("#phone").focus();
                    return;
                }
            }
            //验证年龄是否是数字
            if (age.trim().length > 0) {
                var ageRegex = /^[1-9]\d*$/;
                if (!ageRegex.test(age)) {
                    $("#tip").text("请输入正常的年龄值");
                    $("#tipModal").modal();
                    $("#age").focus();
                    return;
                }
            }

            var data = {
                username: username,
                password: password,
                phone: phone,
                nickname: nickname,
                age: age,
                address: address
            };
            $.ajax({
                type: "post",
                url: "/register",
                data: {
                    user: JSON.stringify(data)
                },
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: function (data) {
                    if (data.status == '0') {
                        window.location.href = "/login";
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
                        <span id="login_err" class="sty_txt2">用户注册</span>
                    </div>

                    <div>
                        用户名：<input type="text" name="username" class="username form-control" id="name">
                    </div>

                    <div>
                        密<span style="margin-left: 15px"></span>码：<input type="password" name="password"
                                                                         class="pwd form-control"
                                                                         id="pwd">
                    </div>
                    <div>
                        昵<span style="margin-left: 15px"></span>称：<input type="text" name="nickname"
                                                                         maxlength="7"
                                                                         class="pwd form-control"
                                                                         id="nickname">
                    </div>
                    <div>
                        手机号：<input type="text" name="phone" class="pwd form-control"
                                   id="phone">
                    </div>

                    <div>
                        年<span style="margin-left: 15px"></span>龄：<input type="text" name="age" class="pwd form-control"
                                                                         id="age">
                    </div>
                    <div>
                        地<span style="margin-left: 15px"></span>址：<input type="text" name="address"
                                                                         class="pwd form-control"
                                                                         id="address">
                    </div>

                    <div id="btn_area">
                        <input type="button" class="login_btn" id="register" value="立即注册">
                        <input type="button" class="login_btn" id="cancel" value="取  消">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../tip.jsp" %>
</body>
</html>
