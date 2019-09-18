package cn.liu.main.web;

import cn.liu.main.common.ResponseStatus;
import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.service.IChatRoomService;
import cn.liu.webChat.service.IUserInfoService;
import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/9/18
 */
@RequestMapping("/userInfo")
@Controller
public class UserInfoController {
    @Resource
    private IUserInfoService userInfoService;
    @Resource
    private IChatRoomService chatRoomService;

    @RequestMapping("/findUserByNickname")
    @ResponseBody
    public Map<String, Object> findUserByNickname(HttpServletRequest request, String nickname) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                result.put("status", 2);
                result.put("message", "未登录....");
                return result;
            }
            List<UserInfo> userInfos = userInfoService.findUserByNickname(nickname);
            result.put("datas", userInfos);
            result.put("status", 0);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", 2);
            result.put("message", "服务异常");
        }
        return result;
    }

    @RequestMapping("/createSingleRoom")
    @ResponseBody
    public String createSingleRoom(HttpServletRequest request, Integer userId) {
        try {
            Integer adminId = (Integer) request.getSession().getAttribute("userId");
            if (adminId == null) {
                return ResponseStatus.NOLOGIN;
            }
            chatRoomService.createSingleChatRoom(adminId, userId);
            return ResponseStatus.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseStatus.FAIL;
    }
}
