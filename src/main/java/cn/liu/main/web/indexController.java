package cn.liu.main.web;

import cn.liu.webChat.domain.ChatRoom;
import cn.liu.webChat.domain.Message;
import cn.liu.webChat.domain.RoomMsg;
import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.mybatis_dao.IChatRoomDao;
import cn.liu.webChat.service.IChatMessageService;
import cn.liu.webChat.service.IChatRoomService;
import cn.liu.webChat.service.IMessageService;
import com.google.common.collect.Lists;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuf on 2016/9/3.
 */
@Controller
public class indexController {
    @Resource
    private IChatRoomService chatRoomService;
    @Resource
    private IChatMessageService chatMessageService;


    @RequestMapping("index")
    public String index(Model model, HttpServletRequest request) {
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return "redirect:/login";
        }

        return "index";
    }

    @RequestMapping("/findChatRooms")
    public String findChatRooms(Model model, HttpServletRequest request, Integer roomId) {
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return "redirect:/login";
        }
        Integer userId = user.getId();
        List<ChatRoom> chatRooms = chatRoomService.findChatRoom(userId);
        //如果roomId不为空，则需要置顶当前roomId
        if (roomId != null && !CollectionUtils.isEmpty(chatRooms)) {
            int index = 0;
            for (int i = 0; i < chatRooms.size(); i++) {
                Integer rId = chatRooms.get(i).getId();
                if (roomId.equals(rId)) {
                    index = i;
                    break;
                }
            }
            //置顶
            Collections.swap(chatRooms, index, 0);
        }

        model.addAttribute("rooms", chatRooms);
        return "left_room_list";
    }

    @RequestMapping("/findChatMessage")
    public String findChatMessage(Model model, HttpServletRequest request, Integer roomId) {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (userInfo == null) {
            return "redirect:/login";
        }
        ChatRoom chatRoom = chatRoomService.findChatRoomById(roomId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("room", chatRoom);
        String username = userInfo.getUsername();
        model.addAttribute("username", username);
        model.addAttribute("nickname", userInfo.getNickname());
        List<Map<String, Object>> roomMsgs = chatMessageService.initMessage(roomId, 0);
        if (!CollectionUtils.isEmpty(roomMsgs)) {
            //保证消息展示的顺序是正常（按照时间先后）
            roomMsgs = Lists.reverse(roomMsgs);
            model.addAttribute("messages", roomMsgs);
        }
        return "chat";
    }


}
