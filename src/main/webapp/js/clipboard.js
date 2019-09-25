/*
    粘贴板发送图片
 */
//查找box元素,检测当粘贴时候,
document.querySelector('#inputBox').addEventListener('paste', function (e) {
    //判断是否是粘贴图片
    if (e.clipboardData && e.clipboardData.items[0].type.indexOf('image') > -1) {
        var reader = new FileReader();
        var file = e.clipboardData.items[0].getAsFile();
        reader.onload = function (event) {
            // event.target.result 即为图片的Base64编码字符串
            var base64_str = event.target.result;
            console.log("发送截图base64");
            var mes = "<img width='300' height='400' src='" + base64_str + "'>";
            //发送消息
            var roomId = $("#roomId").val();
            sendMsg(roomId, mes);
        };
        reader.readAsDataURL(file);
    }
}, false);