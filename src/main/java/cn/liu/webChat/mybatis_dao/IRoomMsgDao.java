package cn.liu.webChat.mybatis_dao;

import cn.liu.webChat.domain.RoomMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * created by liufeng
 * 2019/7/25
 */
public interface IRoomMsgDao {
    void saveMsg(RoomMsg roomMsg);

    List<RoomMsg> initMsg(@Param("roomId") Integer roomId, @Param("page") int page);

    List<RoomMsg> realTimeMsg(@Param("roomId") Integer roomId, @Param("lastTimeStr") Long lastTimeStr);
}
