<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>webchat</title>
    <link href="${pageContext.request.contextPath}/css/login_css.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript">

        if (window != top) {
            top.location.href = location.href;
        }


        $(document).ready(function () {
            $("#login_sub").click(function () {
                var username = $("#name").val();
                var password = $("#pwd").val();
                if (username.trim().length == 0) {
                    $("#user_flag").html("用户名不能为空！");
                    $("#name").focus();
                    return;
                } else {
                    $("#user_flag").html("");
                }
                if (password.trim().length == 0) {
                    $("#password_flag").html("密码不能为空！");
                    $("#pwd").focus();
                    return;
                } else {
                    $("#password_flag").html("");
                }

                $("#submitForm").submit();

            })
        })

        /*回车事件*/
        function EnterPress(e) { //传入 event
            var e = e || window.event;
            if (e.keyCode == 13) {
                $("#submitForm").attr("action", "/toLogin").submit();
            }
        }
    </script>
</head>
<body>
<div id="login_center">
    <div id="login_area">
        <div id="login_box">
            <div id="login_form">
                <form id="submitForm" action="/toLogin" method="post">
                    <div id="login_tip">
                        <span id="login_err" class="sty_txt2"></span>
                    </div>

                    <div>
                        用户名：<input type="text" name="username" class="username" id="name">
                    </div>

                    <div class="Prompt" id="user_flag">${loginMsg}</div>


                    <div>
                        密<span style="margin-left: 15px"></span>码：<input type="password" name="password" class="pwd"
                                                                         id="pwd"
                                                                         onkeypress="EnterPress(event)"
                                                                         onkeydown="EnterPress()">
                    </div>
                    <div class="Prompt" id="password_flag"></div>


                    <div id="datesheet_box">
                        <input type="checkbox" name="" style="width: 15px;float: left;margin-top:4px;">

                        <div class="datesheet_n">&nbsp;自动登录
                            <div class="datesheet_n" style="float:right;margin-right: 115px;">忘记密码
                            </div>
                        </div>
                    </div>
                    <div id="btn_area">
                        <input type="button" class="login_btn" id="login_sub" value="登  录">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
