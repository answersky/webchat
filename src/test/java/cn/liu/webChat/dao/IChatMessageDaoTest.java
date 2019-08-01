package cn.liu.webChat.dao;

import cn.liu.main.Main;
import cn.liu.webChat.domain.RoomMsg;
import cn.liu.webChat.domain.UserMessage;
import cn.liu.webChat.mybatis_dao.IRoomMsgDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuf on 2017/1/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
public class IChatMessageDaoTest {
    @Resource
    private IRoomMsgDao chatMessageDao;

    @Test
    public void saveMessage() throws Exception {
        Date date = new Date();
        RoomMsg userMessage = new RoomMsg();
        userMessage.setRoom_id(1);
        userMessage.setUser_id(2);
        userMessage.setTime_str(date.getTime());
        userMessage.setCreate_time(date);
        userMessage.setMsg("你好");
        chatMessageDao.saveMsg(userMessage);
    }

    @Test
    public void initMessage() {
        List<Map<String, Object>> list = chatMessageDao.initMsg(1, 0);
        System.out.println(list);
    }

    @Test
    public void reciveMessage() {

    }

}