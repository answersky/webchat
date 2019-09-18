var socket = new SockJS('http://' + document.location.host + '/sockjs/webSocketServer');
$(document).ready(function () {
    //聊天窗口切换点击事件
    $(".room").click(function (e) {
        $(".room").removeClass("room_select");
        $(this).addClass("room_select");

        //TODO 切换聊天窗口

    });

    //隐藏左侧菜单
    $("#chat-head").click(function () {
        if ($(".outer").is(":hidden")) {
            $(".outer").show();
            $(".chat-box").removeAttr("style");
            $("#chat-head").removeAttr("style");
        } else {
            $(".outer").hide();
            $(".chat-box").css("width", "100%");
            $("#chat-head").css("width", "100%");
        }
    });

});

//打开连接
socket.onopen = function () {
    console.log('websocket 连接成功');
};

//接收消息
socket.onmessage = function (msg) {
    var info = JSON.parse(msg.data);
    console.log("msg", msg.data);
    var roomId = info.roomId;
    var nickname = info.nickname;
    var time = info.create_time;
    var message = info.message;
    receiveMsg(roomId, nickname, time, message);
};

//连接关闭
socket.onclose = function () {
    console.log("websocket close");
};

//发送快捷键 回车
function sendInfo(roomId) {
    if (event.keyCode == 13) {
        console.log("触发输入框回车事件，发送信息.");
        //发送信息
        var msg = $("#inputBox" + roomId).val();
        msg = isImg(msg);
        sendMsg(roomId, msg);
    }
}

//发送信息按钮
function sendBtn(roomId) {
    var msg = $("#inputBox" + roomId).val();
    msg = isImg(msg);
    sendMsg(roomId, msg);
}

//表情消息发送
function sendImg(own, roomId) {
    var img = $(own).html();
    sendMsg(roomId, img);
}

//表情按钮
function clickIcon(roomId) {
    if ($("#icon" + roomId).is(":hidden")) {
        $("#icon" + roomId).show();
    } else {
        $("#icon" + roomId).hide();
    }
}

function hideIcon() {
    $("#icon").hide();
}

//输入文字发送表情
function isImg(msg) {
    if (msg == '/懵逼') {
        return '<img title="懵逼" src="/css/img/1.png">';
    }
    if (msg == '/疑问' || msg == '/?') {
        return '<img title="疑问" src="/css/img/10.png">';
    }
    if (msg == '/美味' || msg == '/吃') {
        return '<img  title="美味" src="/css/img/11.png">';
    }
    if (msg == '/没兴趣') {
        return '<img title="没兴趣" src="/css/img/12.png">';
    }
    if (msg == '/发呆') {
        return '<img title="发呆" src="/css/img/13.png">';
    }
    if (msg == '/流鼻血' || msg == '/色') {
        return '<img  title="流鼻血" src="/css/img/15.png">';
    }
    if (msg == '/赞' || msg == '/棒' || msg == 'good') {
        return '<img title="赞" src="/css/img/18.png">';
    }
    return msg;
}

function sendMsg(roomId, msg) {
    var me = $("#nickname").val();
    var time = new Date().Format("yyyy-MM-dd hh:mm:ss");
    if (msg.length > 0) {
        var model = "<div data-flex='dir:right' class='message-list-item'>" +
            "<div data-flex='dir:right' data-flex-box='0' class='message-container' style='height: auto'>" +
            "<div data-flex-box='0' data-flex='main:top cross:top' class='avatar-container' style='float: right'>" +
            "<div>" +
            "<div class='avatar' style='width: 39px; height: 39px; background-image: url(&quot;/css/img/toux.jpg&quot;);'></div>" +
            "</div>" +
            "</div>" +
            "<div style='padding: 0px 50px; width: 100%; text-align: right;'>" +
            "<span class='message-nickname-box' style='display: block;'>" +
            "<span class='message-nickname'>" + me + " </span>" +
            "<span>" + time + "</span>" +
            "</span>" +
            "<div class='message'>" +
            "<span>" + msg + "</span>" +
            "<div class='triangle-right-outer'></div>" +
            "<div class='triangle-right-inner'></div>" +
            "</div> </div> </div> </div>";

        $("#chat-content" + roomId).append(model);
        checkScrollStyle(roomId);
        scrollBottom(roomId);
        $("#inputBox" + roomId).val("");

        sendToMsg(roomId, msg);
    }
}

function sendToMsg(roomId, msg) {
    if (socket.readyState == 1) {
        var data = {
            roomId: roomId,
            msg: msg
        };
        //调用后台handleTextMessage方法
        socket.send(JSON.stringify(data));
    } else {
        alert("连接失败!");
    }
}


//聊天出现滚动条时，保证滚动条处于最底部
function scrollBottom(roomId) {
    var div = document.getElementById('chat-content' + roomId);
    div.scrollTop = div.scrollHeight;
}

//控制滚动条样式，在没有超出范围高度的时候隐藏滚动条样式
function checkScrollStyle(roomId) {
    var height = $("#chat-content" + roomId).height();
    if (height > 560) {
        $("#chat-content" + roomId).css({"overflow-y": "scroll", "max-height": "560px"});
    }
}

//时间格式化
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};


/*=========================实时监控是否有新的聊天加入=====================================*/
/*var checkRoom = window.setInterval(function () {
    var me=$("#username").val();
    var timeStr=$("#timeStr").val();
    var roomId = $("#roomId").val();
    console.log("定时器检查是否有新消息，上一次的时间戳："+timeStr);
    // 定时接收消息
    $.ajax({
        type: "post",
        url: "/msg/receiveMsg",
        data:{
            timeStr: timeStr,
            roomId: roomId
        },
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        success: function (data) {
            if(data!=null && typeof(data)!="undefined" && data!=''){
                var status = data['status'];
                var message = data['message'];
                console.log(message);
                if (status == '0') {
                    var time = data['timeStr'];
                    $("#timeStr").val(time);
                    var msgList = data['datas'];
                    for (var i = 0; i < msgList.length; i++) {
                        var msg = msgList[i];
                        var user = msg.nickname;
                        receiveMsg(roomId, user, msg);
                    }
                }

            }
        }
    });

}, 3000);*/

//接收消息
function receiveMsg(roomId, user, time, msg) {
    if (msg.length > 0) {
        var html = "<div data-flex='dir:left' class='message-list-item'>" +
            "<div data-flex='dir:left' data-flex-box='0' class='message-container' style='height: auto'>" +
            "<div data-flex-box='0' data-flex='main:top cross:top' class='avatar-container' style='float: left'>" +
            "<div>" +
            "<div class='avatar'" +
            "style='width: 39px; height: 39px; background-image: url(&quot;/css/img/toux.jpg&quot;);'></div>" +
            "</div>" +
            "</div>" +
            "<div style='padding: 0px 50px; width: 100%; text-align: left;'>" +
            "<span class='message-nickname-box' style='display: block;'>" +
            "<span class='message-nickname'>" + user + " </span>" +
            "<span>" + time + "</span>" +
            "</span>" +
            "<div class='message'>" +
            "<span>" + msg + "</span>" +
            "<div class='triangle-left-outer'></div>" +
            "<div class='triangle-left-inner'></div>" +
            "</div></div></div></div>";

        $("#chat-content" + roomId).append(html);
        checkScrollStyle(roomId);
        scrollBottom(roomId);
    }
}

//头部搜索
function searchUser() {
    var key = $.trim($("#i-advanced-search").val());
    if (key == null || key == '') {
        $("#tip").text("请输入朋友昵称");
        $("#tipModal").modal();
        return;
    }

    $.ajax({
        type: "post",
        url: "/userInfo/findUserByNickname",
        data: {
            nickname: key
        },
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        success: function (data) {
            if (data != null && typeof(data) != "undefined" && data != '') {
                var status = data['status'];
                if (status == '0') {
                    var userinfos = data['datas'];
                    $("#search_result div.chatRoom").html("");
                    for (var i = 0; i < userinfos.length; i++) {
                        //展示选择项
                        var username = userinfos[i].username;
                        var userId = userinfos[i].id;
                        var html = '<li class="room room_select" onclick="createRoom(' + userId + ')">' +
                            '                                    <div style="height: 50px;width:180px">' +
                            '                                        <div class="avatar heardPic"></div>' +
                            '                                        <span>' + username + '</span>' +
                            '                                    </div>' +
                            '                                </li>';
                        $("#search_result div.chatRoom").append(html);
                    }
                    $("#search_result").show();
                }
                if (status == '2') {
                    $("#tip").text(data['message']);
                    $("#tipModal").modal();
                }

            }
        }
    });
}

function createRoom(userId) {
    $.ajax({
        type: "post",
        url: "/userInfo/createSingleRoom",
        data: {
            userId: userId
        },
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        success: function (data) {
            if (data == '0') {
                //todo 展示会话
            } else {
                $("#tip").text("创建对话失败");
                $("#tipModal").modal();
            }
        }
    });
}