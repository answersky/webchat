package cn.liu.webChat.service.impl;

import cn.liu.webChat.domain.RoomMsg;
import cn.liu.webChat.mybatis_dao.IChatRoomDao;
import cn.liu.webChat.mybatis_dao.IRoomMsgDao;
import cn.liu.webChat.service.IChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/7/25
 */
@Service("chatMessageService")
public class ChatMessageServiceImpl implements IChatMessageService {


    @Resource
    private IChatRoomDao chatRoomDao;
    @Resource
    private IRoomMsgDao roomMsgDao;

    @Override
    public void saveMessage(RoomMsg roomMsg) {
        roomMsgDao.saveMsg(roomMsg);
    }

    @Override
    public List<Map<String, Object>> initMessage(Integer roomId, int page) {
        return roomMsgDao.initMsg(roomId, page);
    }

    @Override
    public List<Map<String, Object>> findMessage(Integer roomId, Integer userId, Long lastTime) {
        return roomMsgDao.realTimeMsg(roomId, userId, lastTime);
    }
}
