package cn.liu.main.web;

import cn.liu.webChat.service.IMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * created by liufeng
 * 2019/7/24
 */
@Controller
@RequestMapping("/msg")
public class MessageController {
    @Resource
    private IMessageService messageService;

    @RequestMapping("sendMessage")
    @ResponseBody
    public String sendMessage(String roomId, String user, String msg) {
        boolean flag = messageService.saveMessage(roomId, user, msg);
        return String.valueOf(flag);
    }
}
