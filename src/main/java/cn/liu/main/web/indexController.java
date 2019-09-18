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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuf on 2016/9/3.
 */
@Controller
public class indexController {
    @Resource
    private IMessageService messageService;
    @Resource
    private IChatRoomService chatRoomService;
    @Resource
    private IChatMessageService chatMessageService;

    public static List<String> roomList = new ArrayList<>();

    @RequestMapping("index")
    public String index(Model model, HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        List<Map<String, Object>> chatRooms = chatRoomService.findChatRoom(userId);
        if (!CollectionUtils.isEmpty(chatRooms)) {
            Map<String, Object> chatRoom = chatRooms.get(0);
            Integer roomId = Integer.valueOf(String.valueOf(chatRoom.get("id")));
            model.addAttribute("roomId", roomId);
            model.addAttribute("room", chatRoom);
            List<Map<String, Object>> roomMsgs = chatMessageService.initMessage(roomId, 0);
            if (!CollectionUtils.isEmpty(roomMsgs)) {
                Map<String, Object> roomMsg = roomMsgs.get(0);
                model.addAttribute("timeStr", roomMsg.get("time_str"));
                //保证消息展示的顺序是正常（按照时间先后）
                roomMsgs = Lists.reverse(roomMsgs);
                model.addAttribute("messages", roomMsgs);
            } else {
                model.addAttribute("timeStr", "0");
            }

        }

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        String username= (String) request.getSession().getAttribute("username");
        model.addAttribute("username",username);
        model.addAttribute("nickname", userInfo.getNickname());
        model.addAttribute("rooms", chatRooms);
        return "index";
    }


    @RequestMapping("checkRoom")
    @ResponseBody
    public Map<String, Map<String, Map<String, String>>> checkRoom() {
        Map<String, Map<String, Map<String, String>>> maps = new LinkedHashMap<>();
        // 接收消息（消息带上聊天室id）
//        LinkedList<Map<String, String>> list = messageService.getQueryMessage();
        List<Map<String, String>> list = new ArrayList<>();
//        LinkedList<Map<String,String>> list= messageService.getMessage("room_user1_user2");
        if (list.size() < 1) {
            return maps;
        }
        String room = list.get(0).get("roomId");
        String user = list.get(0).get("user");
        String msg = list.get(0).get("message");
        //判断当前聊天室是否已经存在
        //已经存在
        if (roomList.contains(room)) {
            //接收消息
            Map<String, Map<String, String>> map = new LinkedHashMap<>();
            Map<String, String> msgMap = new LinkedHashMap<>();
            msgMap.put(user, msg);
            map.put(room, msgMap);
            maps.put("receiveMsg", map);
            return maps;
        } else {
            //记录下房间id
            roomList.add(room);
            //新增聊天室
            Map<String, Map<String, String>> map = new LinkedHashMap<>();
            Map<String, String> msgMap = new LinkedHashMap<>();
            msgMap.put(user, msg);
            map.put(room, msgMap);
            maps.put("addRoom", map);
            return maps;
        }
    }


    @RequestMapping("addRoom")
    public String addRoom(Model model, String roomId, String user, String msg) {
        model.addAttribute("roomId", roomId);
        System.out.println(user);
        System.out.println(msg);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:dd:ss");
        Date date = new Date();
        model.addAttribute("user", user);
        model.addAttribute("msg", msg);
        model.addAttribute("time", sdf.format(date));
        return "chat";
    }


}
