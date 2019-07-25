package cn.liu.webChat.service.impl;

import cn.liu.webChat.domain.ChatRoom;
import cn.liu.webChat.domain.RoomUser;
import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.mybatis_dao.IChatRoomDao;
import cn.liu.webChat.mybatis_dao.IUserInfoDao;
import cn.liu.webChat.service.IChatRoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    public void createSingleChatRoom(Integer adminId, Integer userId) {
        UserInfo userInfo = userInfoDao.findUserInfo(adminId);
        ChatRoom chatRoom = new ChatRoom();
        UserInfo obj = userInfoDao.findUserInfo(userId);
        //个人聊天
        chatRoom.setRoom_name(obj.getUsername());
        //上限2人
        chatRoom.setLimit(2);
        chatRoom.setIs_group(singleChat);
        chatRoom.setCreate_time(new Date());
        chatRoom.setCreate_user(userInfo.getUsername());
        chatRoomDao.saveChatRoom(chatRoom);
        addMember(chatRoom.getId(), adminId, userInfo.getUsername(), "1");
        addMember(chatRoom.getId(), userId, userInfo.getUsername(), "0");

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
    public void createGroupChatRoom(Integer roomId, Integer adminId, Integer userId) {
        UserInfo userInfo = userInfoDao.findUserInfo(adminId);
        ChatRoom chatRoom = new ChatRoom();
        //建群
        chatRoom.setRoom_name(adminId + userId + "");
        //群上限200人
        chatRoom.setLimit(200);
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
}
