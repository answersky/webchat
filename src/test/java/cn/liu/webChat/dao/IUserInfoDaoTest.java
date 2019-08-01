package cn.liu.webChat.dao;

import cn.liu.main.Main;
import cn.liu.main.utils.DESTest;
import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.mybatis_dao.IUserInfoDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * created by liufeng
 * 2019/7/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
public class IUserInfoDaoTest {
    @Resource
    private IUserInfoDao userInfoDao;

    @Test
    public void saveUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("lftest");
        String password = "123456";
        String miyao = "answer12345678910lfxmsky";
        String miwen = DESTest.encrypt(password, miyao);
        userInfo.setPassword(miwen);
        userInfo.setAddress("湖北");
        userInfo.setAge(25);
        userInfo.setNickname("lf");
        userInfo.setCreate_time(new Date());
        userInfoDao.saveUserInfo(userInfo);
    }

    @Test
    public void findUserInfo() {
        UserInfo userInfo = userInfoDao.findUserByUsername("answer");
        System.out.println(userInfo);
    }
}
