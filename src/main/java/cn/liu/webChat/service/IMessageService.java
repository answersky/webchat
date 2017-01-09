package cn.liu.webChat.service;

import cn.liu.webChat.domain.Message;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by xcy
 * on 2016/12/15.
 * 收发消息的service
 */
public interface IMessageService {
    /**
     * 接受消息，存入队列，map中的key
     * userName，用户名
     * message，消息内容
     * createdTime,创建时间
     * @return 是否存入成功
     */
    boolean saveMessage(String roomId,String user,String message);

    /**
     * 获取消息
     * @param roomId 房间id
     * @param status 1代表第一次拿消息
     * @param timeStr 上一次拿消息的时间戳
     * @return 返回新消息
     */
    public Message getMessage(String roomId, int status, long timeStr);

}
