package cn.liu.webChat.mybatis_dao;

import cn.liu.webChat.domain.UserInfo;

/**
 * created by liufeng
 * 2019/7/25
 */
public interface IUserInfoDao {
    void saveUserInfo(UserInfo userInfo);

    UserInfo findUserInfo(Integer id);

    UserInfo findUserByUsername(String username);
}
