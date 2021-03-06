package cn.liu.main.web;

import cn.liu.main.common.ResponseStatus;
import cn.liu.webChat.domain.Message;
import cn.liu.webChat.domain.RoomMsg;
import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.service.IChatMessageService;
import cn.liu.webChat.service.IMessageService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/7/24
 * 纯java实现的聊天（old version）
 */
@Controller
@RequestMapping("/msg")
public class MessageController {

    @Resource
    private IChatMessageService chatMessageService;

    @Deprecated
    @RequestMapping("sendMessage")
    @ResponseBody
    public String sendMessage(HttpServletRequest request, Integer roomId, String msg) {
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();
        if (userId == null) {
            return ResponseStatus.NOLOGIN;
        }
        Date date = new Date();
        RoomMsg userMessage = new RoomMsg();
        userMessage.setRoom_id(roomId);
        userMessage.setUser_id(userId);
        userMessage.setTime_str(date.getTime());
        userMessage.setCreate_time(date);
        userMessage.setMsg(msg);
        chatMessageService.saveMessage(userMessage);
        return ResponseStatus.SUCCESS;
    }

    @Deprecated
    @RequestMapping("receiveMsg")
    @ResponseBody
    public Map<String, Object> receiveMsg(HttpServletRequest request, long timeStr, Integer roomId) {
        Map<String, Object> result = new LinkedHashMap<>();
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();
        if (userId == null) {
            result.put("status", 2);
            result.put("message", "未登录....");
            return result;
        }
        List<Map<String, Object>> messages = chatMessageService.findMessage(roomId, userId, timeStr);
        if (!CollectionUtils.isEmpty(messages)) {
            Map<String, Object> lastMessage = messages.get(0);
            result.put("timeStr", lastMessage.get("time_str"));
            result.put("datas", messages);
            result.put("status", 0);
            result.put("message", "接收到消息....");
        } else {
            result.put("status", 1);
            result.put("message", "暂无消息更新....");
        }
        return result;
    }
}
