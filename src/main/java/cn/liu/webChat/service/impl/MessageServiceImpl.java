package cn.liu.webChat.service.impl;

import cn.liu.webChat.dao.IRedisDao;
import cn.liu.webChat.domain.Message;
import cn.liu.webChat.service.IMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by xcy
 * on 2016/12/15.
 */
@Service
public class MessageServiceImpl implements IMessageService{

    @Resource
    IRedisDao redisDao;

    @Override
    public boolean saveMessage(String roomId,String user,String message) {
        Date date = new Date();
        Map<String, String> map = new HashMap<>();
        map.put("roomId", "room_id_"+roomId);
        map.put("roomName", "room_user1_user2");
        map.put("user", user+"_"+date.getTime());
        map.put("message", message);

        try {
            redisDao.insertToRedis(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取消息
     * @param roomId 房间id
     * @param status 1代表第一次拿消息
     * @param timeStr 上一次拿消息的时间戳
     * @return 返回新消息
     */
    @Override
    public Message getMessage(String roomId, int status, long timeStr){
        Message message=new Message();
        Map<String,String> map= (Map<String, String>) redisDao.findFromRedis(roomId);
        if(map!=null && map.size()>0){
            if(status==1){
                //初次进入聊天室，获取全部的聊天记录
                //获取最新的时间戳
                long time=getTimes(map);
                message.setTime(time);
                message.setMap(map);

            }else {
                //根据时间戳来获取消息，那上次时间戳以后的全部消息记录
                Map<String,String> infoMap=new LinkedHashMap<>();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String user = entry.getKey();
                    String msg = entry.getValue();
                    long time=Long.parseLong(user.split("_")[1]);
                    //新消息
                    if(time>timeStr){
                        infoMap.put(user,msg);
                    }
                }
                long times=getTimes(infoMap);
                message.setMap(infoMap);
                message.setTime(times);
            }
        }

        return message;
    }

    private long getTimes(Map<String,String> map){
        Set<String> keys=map.keySet();
        int i=0;
        long time=0;
        for(String key:keys){
            if(i==(keys.size()-1)){
                time=Long.parseLong(key.split("_")[1]);
            }
            i++;
        }
        return time;
    }

}
