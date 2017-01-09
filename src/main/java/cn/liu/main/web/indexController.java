package cn.liu.main.web;

import cn.liu.webChat.domain.Message;
import cn.liu.webChat.service.IMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    public static List<String> roomList = new ArrayList<>();

    @RequestMapping("index")
    public String index(Model model, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:dd:ss");
        Date date = new Date();
        roomList = new ArrayList<>();

        Message message=getMessage(1,0);
        if(message!=null){
            Map<String,String> mapMap=message.getMap();
            long timeStr=message.getTime();
            model.addAttribute("maps",mapMap);
            model.addAttribute("timeStr",timeStr);
        }

        model.addAttribute("time", sdf.format(date));
        String username= (String) request.getSession().getAttribute("username");
        model.addAttribute("username",username);
        model.addAttribute("roomId","12");
        return "index";
    }

    private Message getMessage(int status,long time){
        // 接收消息（消息带上聊天室id）
        Message message= messageService.getMessage("room_id_12",status,time);
        return message;
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


    @RequestMapping("receiveMsg")
    @ResponseBody
    public  Map<String, String> receiveMsg(long timeStr) {
        Map<String, String> map=new LinkedHashMap<>();
        Message message=getMessage(2,timeStr);
        if(message!=null){
            map=message.getMap();
            if(map.size()>0){
                long time=message.getTime();
                map.put("timeStr",time+"");
                return map;
            }
        }
        return null;
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


    @RequestMapping("sendMessage")
    @ResponseBody
    public String sendMessage(String roomId, String user, String msg) {
        boolean flag = messageService.saveMessage(roomId,user,msg);
        return String.valueOf(flag);
    }

}
