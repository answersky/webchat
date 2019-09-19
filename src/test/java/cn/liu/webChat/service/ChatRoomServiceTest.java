package cn.liu.webChat.service;

import cn.liu.main.Main;
import cn.liu.webChat.domain.ChatRoom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/7/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
public class ChatRoomServiceTest {
    @Resource
    private IChatRoomService chatRoomService;

    @Test
    public void addFriend() {
        chatRoomService.createSingleChatRoom(1, 2);
    }

    @Test
    public void findRoom() {
        List<ChatRoom> rooms = chatRoomService.findChatRoom(1);
        System.out.println(rooms);
    }
}
