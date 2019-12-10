$(document).ready(function () {
    //去掉绑定的焦点移出事件
    $('#btn').unbind('blur');
    $('#headpic').unbind('blur');
});

//图像上传功能
function fileUpload(fileId) {
    var fd = new FormData();
    var file = $("#" + fileId).get(0).files[0];
    if (!validateFile(file)) {
        return;
    }
    fd.append("file", file);
    $.ajax({
        url: "/file/fileUpload",
        type: "POST",
        processData: false,
        contentType: false,
        data: fd,
        success: function (result) {
            console.log(result);
            if (result) {
                $("#updateInfoModal div.defalut_avatar").css("background-image", "url('" + result + "')");
                $("#updateInfoModal div.avatar").css("background-image", "url('" + result + "')");
                $("div.selfinfo-avatar div.defalut_avatar").css("background-image", "url('" + result + "')");
                $("div.selfinfo-avatar div.avatar").css("background-image", "url('" + result + "')");
            }
        }
    });
}

function validateFile(file) {
    if (!file) {
        $("#tip").text("请选择图片");
        $("#tipModal").modal();
        return false;
    }
    var size = file.size;
    var type = file.type;
    //大于3M的文件不允许上传
    if (size > 2 * 1024 * 1024) {
        $("#tip").text("请上传小于2M的图片");
        $("#tipModal").modal();
        return false;
    }
    var picType = (type.substr(type.lastIndexOf("/"))).toLowerCase();
    if (picType != "/jpg" && picType != "/gif" && picType != "/jpeg" && picType != "/png") {
        $("#tip").text("请上传图片文件");
        $("#tipModal").modal();
        return false;
    }
    return true;
}


//聊天框发送图片
function chatPicUpload(fileId) {
    var fd = new FormData();
    var file = $("#" + fileId).get(0).files[0];
    if (!validateFile(file)) {
        return;
    }
    fd.append("file", file);
    $.ajax({
        url: "/file/chatPicUpload",
        type: "POST",
        processData: false,
        contentType: false,
        data: fd,
        success: function (result) {
            console.log("chat img url:" + result);
            if (result) {
                var mes = "<img width='300' height='400' src='" + result + "'>";
                //发送消息
                var roomId = $("#roomId").val();
                sendMsg(roomId, mes);
            }
        }
    });
}