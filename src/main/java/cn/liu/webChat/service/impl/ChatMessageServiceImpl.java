package cn.liu.webChat.service.impl;

import cn.liu.webChat.domain.RoomMsg;
import cn.liu.webChat.mybatis_dao.IChatRoomDao;
import cn.liu.webChat.service.IChatMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * created by liufeng
 * 2019/7/25
 */
@Service("chatMessageService")
public class ChatMessageServiceImpl implements IChatMessageService {
    @Resource
    private IChatRoomDao chatRoomDao;

    @Override
    public void saveMessage(RoomMsg roomMsg) {

    }

    @Override
    public List<RoomMsg> initMessage(Integer roomId, int page) {
        return null;
    }

    @Override
    public List<RoomMsg> findMessage(Integer roomId, Long lastTime) {
        return null;
    }
}
