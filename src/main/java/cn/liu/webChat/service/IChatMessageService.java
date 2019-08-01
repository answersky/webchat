package cn.liu.webChat.service;

import cn.liu.webChat.domain.RoomMsg;

import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/7/25
 */
public interface IChatMessageService {

    void saveMessage(RoomMsg roomMsg);

    List<Map<String, Object>> initMessage(Integer roomId, int page);

    List<Map<String, Object>> findMessage(Integer roomId, Integer userId, Long lastTime);

}
