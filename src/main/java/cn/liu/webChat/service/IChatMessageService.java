package cn.liu.webChat.service;

import cn.liu.webChat.domain.RoomMsg;

import java.util.List;

/**
 * created by liufeng
 * 2019/7/25
 */
public interface IChatMessageService {

    void saveMessage(RoomMsg roomMsg);

    List<RoomMsg> initMessage(Integer roomId, int page);

    List<RoomMsg> findMessage(Integer roomId, Long lastTime);
}
