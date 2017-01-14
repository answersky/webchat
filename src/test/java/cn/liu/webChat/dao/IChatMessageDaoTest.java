package cn.liu.webChat.dao;

import cn.liu.main.Main;
import cn.liu.webChat.domain.UserMessage;
import cn.liu.webChat.mybatis_dao.IChatMessageDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by liuf on 2017/1/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
public class IChatMessageDaoTest {
    @Resource
    private IChatMessageDao chatMessageDao;

    @Test
    public void saveMessage() throws Exception {
        Date date = new Date();
        UserMessage userMessage = new UserMessage();
        userMessage.setUsername("mm");
        userMessage.setMessage("hello");
        userMessage.setTime(date);
        userMessage.setTimeStr(date.getTime());
        chatMessageDao.saveMessage(userMessage);
    }

    @Test
    public void initMessage() {
        List<UserMessage> list = chatMessageDao.findInitMessage();
        System.out.println(list);
    }

    @Test
    public void reciveMessage() {
        List<UserMessage> list = chatMessageDao.reciveMessage(1484390620011l);
        System.out.println(list);
    }

}