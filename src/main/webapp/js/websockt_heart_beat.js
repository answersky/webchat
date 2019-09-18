/**
 * websocket 心跳检测
 */

var socket_url = 'http://' + document.location.host + '/sockjs/webSocketServer';
//1秒检测一次心跳
window.setInterval(function () {
    if (socket.readyState != 1) {
        console.log("websock 重连....");
        socket = new SockJS(socket_url);
    }
}, 1000);