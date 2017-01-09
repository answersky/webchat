package cn.liu.webChat.service;

import cn.liu.main.Main;
import cn.liu.webChat.domain.Message;
import cn.liu.webChat.service.IMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by asus-p on 2016/12/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Main.class })
public class IMessageServiceImplTest {
    @Resource
    IMessageService messageService;

    @Test
    public void testSave(){
        messageService.saveMessage("12","user2","好忙啊!");
    }

    @Test
    public void testGet(){
        Message message = messageService.getMessage("room_id_12",2,1483774714172l);
        System.out.println(message);
    }

}
