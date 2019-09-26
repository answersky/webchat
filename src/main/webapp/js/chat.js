var socket = new SockJS('http://' + document.location.host + '/sockjs/webSocketServer');
$(document).ready(function () {
    initChatRoom();

    //退出
    $("div.login_out").click(function () {
        window.location.href = "/logout";
    });

    //聊天窗口切换点击事件
    $(".room").click(function (e) {
        $(".room").removeClass("room_select");
        $(this).addClass("room_select");
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
    var type = info.type;
    var roomId = info.roomId;
    //会话置顶
    initChatRoom(roomId);
    //消息提醒
    changeBgc(roomId);

    if (type == 0) {
        //新消息
        var nickname = info.nickname;
        var time = info.create_time;
        var message = info.message;
        //判断当前聊天会话界面是否属于当前聊天室
        var currentRoomId = $("#roomId").val();
        if (roomId == currentRoomId) {
            //直接页面展示消息
            receiveMsg(roomId, nickname, time, message);
        }
    }


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
        var msg = $("#inputBox").val();
        msg = isImg(msg);
        sendMsg(roomId, msg);
    }
}

//发送信息按钮
function sendBtn(roomId) {
    var msg = $("#inputBox").val();
    msg = isImg(msg);
    sendMsg(roomId, msg);
}

//表情消息发送
function sendImg(own, roomId) {
    $("#icon").hide();
    var img = $(own).html();
    sendMsg(roomId, img);
}

//表情按钮
function clickIcon() {
    if ($("#icon").is(":hidden")) {
        $("#icon").show();
    } else {
        $("#icon").hide();
    }
}

function hideIcon(roomId, is_group) {
    $("#icon").hide();
    //非群聊校验是否在线
    if (is_group == 0) {
        checkOnline(roomId);
    }
}

//检查是否在线
function checkOnline(roomId) {
    $.ajax({
        type: "post",
        url: "/checkOnline",
        data: {
            roomId: roomId
        },
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        success: function (data) {
            if (data) {
                $("#chat-head i").css("color", "#069AFF");
            } else {
                $("#chat-head i").css("color", "rgba(157, 155, 156, 0.9)");
            }
        }
    });
}

//输入文字发送表情
function isImg(msg) {
    if (msg == '/懵逼') {
        return '<img title="懵逼" src="/css/img/face_icon/1.png">';
    }
    if (msg == '/疑问' || msg == '/?') {
        return '<img title="疑问" src="/css/img/face_icon/10.png">';
    }
    if (msg == '/美味' || msg == '/吃') {
        return '<img  title="美味" src="/css/img/face_icon/11.png">';
    }
    if (msg == '/没兴趣') {
        return '<img title="没兴趣" src="/css/img/face_icon/12.png">';
    }
    if (msg == '/发呆') {
        return '<img title="发呆" src="/css/img/face_icon/13.png">';
    }
    if (msg == '/流鼻血' || msg == '/色') {
        return '<img  title="流鼻血" src="/css/img/face_icon/15.png">';
    }
    if (msg == '/赞' || msg == '/棒' || msg == 'good') {
        return '<img title="赞" src="/css/img/face_icon/18.png">';
    }
    return msg;
}

function sendMsg(roomId, msg) {
    msg = changeHtml(msg);
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
            "<div class='message'>";
        if (msg.indexOf("<img") < 0) {
            var html = "<span><pre>" + msg + "</pre></span>";
        } else {
            var html = "<span>" + msg + "</span>";
        }
        model = model + html;
        var html2 = "<div class='triangle-right-outer'></div>" +
            "<div class='triangle-right-inner'></div>" +
            "</div> </div> </div> </div>";
        model = model + html2;
        $("#chat-content" + roomId).append(model);
        checkScrollStyle(roomId);
        scrollBottom(roomId);
        $("#inputBox").val("");

        sendToMsg(roomId, msg);
    }
}

function changeHtml(msg) {
    if (msg.indexOf("<img") < 0) {
        msg = msg.replace(/</g, "&lt;");
        msg = msg.replace(/>/g, "&gt;");
    }
    return msg;
}

//type:0 表示只发送聊天消息  type:1 表示通知对方创建新的会话
function sendToMsg(roomId, msg) {
    if (socket.readyState == 1) {
        var data = {
            roomId: roomId,
            msg: msg,
            type: 0
        };
        //调用后台handleTextMessage方法
        socket.send(JSON.stringify(data));
    } else {
        $("#tip").text("连接失败");
        $("#tipModal").modal();
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


//接收消息
function receiveMsg(roomId, user, time, msg) {
    msg = changeHtml(msg);
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
            "<div class='message'>";
        if (msg.indexOf("<img") < 0) {
            var html2 = "<span><pre>" + msg + "</pre></span>";
        } else {
            var html2 = "<span>" + msg + "</span>";
        }
        html = html + html2;
        var html3 = "<div class='triangle-left-outer'></div>" +
            "<div class='triangle-left-inner'></div>" +
            "</div></div></div></div>";
        html = html + html3;
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

//隐藏搜索结果
function hideSearchResult() {
    $("#search_result").hide();
}

function createRoom(userId) {
    //判断新建的会话是否已经存在
    $.ajax({
        type: "post",
        url: "/userInfo/checkRoom",
        data: {
            userId: userId
        },
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        success: function (data) {
            if (data.status == '0') {
                $("#search_result").hide();
                var roomId = data.data;
                var isExist = data.isExist;
                if (isExist) {
                    //重新加载会话列表 当前新建会话置顶
                    initChatRoom(roomId);
                } else {
                    addRoom(userId);
                }

            } else {
                $("#tip").text(data.message);
                $("#tipModal").modal();
            }
        }
    });

}

function addRoom(userId) {
    $.ajax({
        type: "post",
        url: "/userInfo/createSingleRoom",
        data: {
            userId: userId
        },
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        success: function (data) {
            if (data.status == '0') {
                var roomId = data.data;
                //重新加载会话列表 当前新建会话置顶
                initChatRoom(roomId);
                //初始化新聊天窗口
                showChatMessage(roomId);

                //通知对方有新的会话产生  消息内容为 new_room
                if (socket.readyState == 1) {
                    var info = {
                        roomId: roomId,
                        userId: userId,
                        type: 1
                    };
                    //调用后台handleTextMessage方法
                    socket.send(JSON.stringify(info));
                } else {
                    $("#tip").text("连接失败");
                    $("#tipModal").modal();
                }

            } else {
                $("#tip").text(data.message);
                $("#tipModal").modal();
            }
        }
    });
}


//-----------------------------------建群--------------------------------------
function addGroup() {
    var roomId = $("#roomId").val();
    //获取好友列表
    $.ajax({
        type: "post",
        url: "/userInfo/friendList",
        data: {
            roomId: roomId
        },
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        success: function (data) {
            if (data.status == '0') {
                var friendList = data.data;
                if (friendList != null && friendList.length > 0) {
                    $("div.friends").html("");
                    for (var i = 0; i < friendList.length; i++) {
                        //展示选择项
                        var username = friendList[i].username;
                        var userId = friendList[i].id;
                        var html = '<li class="room room_select" onclick="createGroup(' + userId + ')">' +
                            '                                    <div style="height: 50px;width:180px">' +
                            '                                        <div class="avatar heardPic"></div>' +
                            '                                        <span style="line-height: 50px;margin-left: 10px;">' + username + '</span>' +
                            '                                    </div>' +
                            '                                </li>';
                        $("div.friends").append(html);
                    }
                    $("#groupModal").modal();
                }
            } else {
                $("#tip").text(data.message);
                $("#tipModal").modal();
            }
        }
    });

}

function createGroup(userId) {
    $("#groupModal").modal("hide");
    var roomId = $("#roomId").val();
    $.ajax({
        type: "post",
        url: "/userInfo/createGroupRoom",
        data: {
            userId: userId,
            roomId: roomId
        },
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        success: function (data) {
            if (data.status == '0') {
                var roomId = data.data;
                //重新加载会话列表 当前新建会话置顶
                initChatRoom(roomId);
                //初始化新聊天窗口
                showChatMessage(roomId);

                //通知对方有新的会话产生  消息内容为 new_room
                if (socket.readyState == 1) {
                    var info = {
                        roomId: roomId,
                        type: 1
                    };
                    //调用后台handleTextMessage方法
                    socket.send(JSON.stringify(info));
                } else {
                    $("#tip").text("连接失败");
                    $("#tipModal").modal();
                }

            } else {
                $("#tip").text(data.message);
                $("#tipModal").modal();
            }
        }
    });
}