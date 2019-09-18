package cn.liu.webChat.mybatis_dao;

import cn.liu.webChat.domain.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * created by liufeng
 * 2019/7/25
 */
public interface IUserInfoDao {
    void saveUserInfo(UserInfo userInfo);

    UserInfo findUserInfo(Integer id);

    UserInfo findUserByUsername(String username);

    List<UserInfo> findUserByNickname(@Param("nickname") String nickname);
}
