package cn.liu.webChat.service.impl;

import cn.liu.webChat.domain.ChatRoom;
import cn.liu.webChat.domain.RoomUser;
import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.mybatis_dao.IChatRoomDao;
import cn.liu.webChat.mybatis_dao.IUserInfoDao;
import cn.liu.webChat.service.IChatRoomService;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/7/25
 */
@Service("chatRoomService")
public class ChatRoomServiceImpl implements IChatRoomService {
    @Resource
    private IChatRoomDao chatRoomDao;
    @Resource
    private IUserInfoDao userInfoDao;

    private static final String singleChat = "0";
    private static final String groupChat = "1";

    /**
     * 第一次创建聊天室（加好友）
     *
     * @param adminId
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer createSingleChatRoom(Integer adminId, Integer userId) {
        UserInfo userInfo = userInfoDao.findUserInfo(adminId);
        ChatRoom chatRoom = new ChatRoom();
        UserInfo obj = userInfoDao.findUserInfo(userId);
        //个人聊天
        chatRoom.setRoom_name(userInfo.getUsername() + obj.getUsername());
        //上限2人
        chatRoom.setLimit_num(2);
        chatRoom.setIs_group(singleChat);
        chatRoom.setCreate_time(new Date());
        chatRoom.setCreate_user(userInfo.getUsername());
        chatRoomDao.saveChatRoom(chatRoom);
        addMember(chatRoom.getId(), adminId, userInfo.getUsername(), "1");
        addMember(chatRoom.getId(), userId, userInfo.getUsername(), "0");
        return chatRoom.getId();

    }

    /**
     * 创建群聊
     *
     * @param roomId
     * @param adminId
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer createGroupChatRoom(Integer roomId, Integer adminId, Integer userId) {
        UserInfo userInfo = userInfoDao.findUserInfo(adminId);
        ChatRoom chatRoom = new ChatRoom();
        FastDateFormat fastDateFormat = FastDateFormat.getInstance("mm:ss");
        String time = fastDateFormat.format(new Date());
        time = time.replace(":", "");
        //建群(默认群名为创建人id+时间戳)
        chatRoom.setRoom_name(userInfo.getUsername() + "_group" + time);
        //群上限200人
        chatRoom.setLimit_num(200);
        chatRoom.setIs_group(groupChat);
        chatRoom.setCreate_time(new Date());
        chatRoom.setCreate_user(userInfo.getUsername());
        chatRoomDao.saveChatRoom(chatRoom);
        //先将原来聊天室的人员加入到群聊天室，然后将新的人员加入到聊天室
        List<RoomUser> roomUsers = chatRoomDao.findRoomUserByRoomId(roomId);
        for (RoomUser roomUser : roomUsers) {
            String is_admin = roomUser.getIs_admin();
            Integer roomUserUserId = roomUser.getUser_id();
            addMember(chatRoom.getId(), roomUserUserId, userInfo.getUsername(), is_admin);
        }
        addMember(chatRoom.getId(), userId, userInfo.getUsername(), "0");
        return chatRoom.getId();
    }

    /**
     * 添加群成员
     *
     * @param roomId
     * @param userId
     * @param username 邀请成员的账号
     */
    @Override
    public void addMember(Integer roomId, Integer userId, String username, String is_admin) {
        RoomUser roomUser = new RoomUser();
        roomUser.setRoom_id(roomId);
        roomUser.setUser_id(userId);
        roomUser.setCreate_time(new Date());
        roomUser.setCreate_user(username);
        roomUser.setIs_admin(is_admin);
        chatRoomDao.saveRoomUser(roomUser);
    }

    @Override
    public List<ChatRoom> findChatRoom(Integer userId) {
        return chatRoomDao.findChatRooms(userId);
    }

    @Override
    public ChatRoom findChatRoomById(Integer roomId) {
        return chatRoomDao.findChatRoomById(roomId);
    }

    @Override
    public Integer checkRoom(Integer adminId, Integer userId) {
        List<Integer> rooms = chatRoomDao.findChatRoomsNotGroup(adminId);
        List<Integer> roomList = chatRoomDao.findChatRoomsNotGroup(userId);
        //取交集
        rooms.retainAll(roomList);
        if (CollectionUtils.isEmpty(rooms)) {
            return null;
        }
        return rooms.get(0);
    }

    @Override
    public boolean checkRoomOnline(Integer userId) {
        Map<Integer, WebSocketSession> sessionMap = ChatMessageHandler.sessionMap;
        if (!CollectionUtils.isEmpty(sessionMap)) {
            return sessionMap.containsKey(userId);
        }
        return false;
    }

    @Override
    public List<Integer> findRoomUserByRoomIdNoCurrent(Integer roomId, Integer userId) {
        return chatRoomDao.findRoomUserByRoomIdNoCurrent(roomId, userId);
    }
}
