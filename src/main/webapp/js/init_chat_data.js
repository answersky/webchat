/**
 * 页面数据加载
 */


//加载聊天室列表
function initChatRoom(roomId) {
    $.ajax({
        url: "/findChatRooms",
        type: "POST",
        data: {roomId: roomId},
        dataType: "html",
        async: false,
        success: function (data) {
            $("div.friendlist-list-box").html(data);
        }
    });
}

//加载聊天消息
function showChatMessage(roomId) {
    readMessage(roomId);
    $.ajax({
        url: "/findChatMessage",
        type: "POST",
        data: {roomId: roomId},
        dataType: "html",
        async: true,
        success: function (data) {
            $("div.chat-box").html(data);
        }
    });
}

//新消息提醒
function changeBgc(roomId) {
    $("#room_" + roomId).find("div.circle").show();
}

//点击阅读消息后消除背景色
function readMessage(roomId) {
    $("#room_" + roomId).find("div.circle").hide();
}