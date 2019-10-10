package cn.liu.webChat.service.impl;

import cn.liu.webChat.domain.ChatRoom;
import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.mybatis_dao.IUserInfoDao;
import cn.liu.webChat.service.IChatRoomService;
import cn.liu.webChat.service.IUserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/7/29
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {
    @Resource
    private IUserInfoDao userInfoDao;
    @Resource
    private IChatRoomService chatRoomService;

    @Override
    public UserInfo findUserByUsername(String username) {
        return userInfoDao.findUserByUsername(username);
    }

    @Override
    public List<UserInfo> findUserByNickname(String nickname) {
        return userInfoDao.findUserByNickname(nickname);
    }

    @Override
    public void saveUser(UserInfo user) {
        userInfoDao.saveUserInfo(user);
    }

    /**
     * 好友列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserInfo> friends(Integer userId) {
        List<UserInfo> userInfos = new ArrayList<>();
        List<ChatRoom> chatRooms = chatRoomService.findChatRoom(userId);
        if (!CollectionUtils.isEmpty(chatRooms)) {
            List<Integer> roomIds = new ArrayList<>();
            for (ChatRoom chatRoom : chatRooms) {
                Integer rId = chatRoom.getId();
                roomIds.add(rId);
            }
            userInfos = userInfoDao.findFrineds(roomIds, userId);
        }
        return userInfos;
    }

    @Override
    public void updateInfo(UserInfo userInfo) {
        userInfoDao.updateInfo(userInfo);
    }

    /**
     * 查看聊天室成员
     *
     * @param roomId
     * @return
     */
    @Override
    public List<Map<String, Object>> findRoomMember(Integer roomId) {
        return userInfoDao.findRoomMember(roomId);
    }
}
