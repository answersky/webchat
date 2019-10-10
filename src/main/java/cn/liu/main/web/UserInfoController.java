package cn.liu.main.web;

import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.service.IChatRoomService;
import cn.liu.webChat.service.IUserInfoService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            Integer userId = user.getId();
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
    public Map<String, Object> createSingleRoom(HttpServletRequest request, Integer userId) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            Integer adminId = user.getId();
            if (adminId == null) {
                result.put("status", 2);
                result.put("message", "未登录....");
                return result;
            }
            Integer roomId = chatRoomService.createSingleChatRoom(adminId, userId);
            result.put("status", 0);
            result.put("data", roomId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", 1);
            result.put("message", "创建会话异常");
        }
        return result;
    }

    @RequestMapping("checkRoom")
    @ResponseBody
    public Map<String, Object> checkRoom(HttpServletRequest request, Integer userId) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            Integer adminId = user.getId();
            if (adminId == null) {
                result.put("status", 2);
                result.put("message", "未登录....");
                return result;
            }
            Integer roomId = chatRoomService.checkRoom(adminId, userId);
            result.put("status", 0);
            if (roomId != null) {
                result.put("isExist", true);
            } else {
                result.put("isExist", false);
            }
            result.put("data", roomId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", 1);
            result.put("message", "创建会话异常");
        }
        return result;
    }

    /**
     * 建群
     *
     * @param request
     * @param userId
     * @return
     */
    @RequestMapping("/createGroupRoom")
    @ResponseBody
    public Map<String, Object> createGroupRoom(HttpServletRequest request, Integer roomId, Integer userId) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            Integer adminId = user.getId();
            if (adminId == null) {
                result.put("status", 2);
                result.put("message", "未登录....");
                return result;
            }
            Integer groupId = chatRoomService.createGroupChatRoom(roomId, adminId, userId);
            result.put("status", 0);
            result.put("data", groupId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", 1);
            result.put("message", "创建会话异常");
        }
        return result;
    }


    /**
     * 好友列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/friendList")
    @ResponseBody
    public Map<String, Object> friendList(HttpServletRequest request, Integer roomId) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            Integer adminId = user.getId();
            if (adminId == null) {
                result.put("status", 2);
                result.put("message", "未登录....");
                return result;
            }
            List<UserInfo> friendList = new ArrayList<>();
            List<UserInfo> userInfos = userInfoService.friends(adminId);
            List<Integer> ids = chatRoomService.findRoomUserByRoomIdNoCurrent(roomId, adminId);
            if (!CollectionUtils.isEmpty(userInfos) && !CollectionUtils.isEmpty(ids)) {
                for (UserInfo userInfo : userInfos) {
                    Integer userId = userInfo.getId();
                    if (!ids.contains(userId)) {
                        friendList.add(userInfo);
                    }
                }
            }

            result.put("status", 0);
            result.put("data", friendList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", 1);
            result.put("message", "获取好友列表异常");
        }
        return result;
    }

    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public Map<String, Object> updateUserInfo(String nickname, String age, String phone, String address) {
        Map<String, Object> result = new LinkedHashMap<>();

        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setNickname(nickname);
            if (!StringUtils.isEmpty(phone)) {
                //校验手机号
                String regexp = "^1[3456789]\\d{9}$";
                if (!Pattern.matches(regexp, phone)) {
                    result.put("message", "手机号格式不正确");
                    result.put("status", "2");
                    return result;
                }
                userInfo.setPhone(phone);
            }
            if (!StringUtils.isEmpty(age)) {
                String re = "^[1-9]\\d*$";
                if (!Pattern.matches(re, age)) {
                    result.put("message", "请输入正常的年龄值");
                    result.put("status", "2");
                    return result;
                }
                userInfo.setAge(Integer.parseInt(age));
            }

            userInfo.setAddress(address);
            userInfoService.updateInfo(userInfo);
            result.put("message", "更新成功");
            result.put("status", "0");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", 1);
            result.put("message", "更新用户信息异常");
        }
        return result;
    }

    @RequestMapping("/findMember")
    public String findMember(Model model, Integer roomId) {
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        int currentId = user.getId();
        List<Map<String, Object>> members = userInfoService.findRoomMember(roomId);
        //判断当前用户是否是群主
        boolean is_admin_flag = false;
        if (!CollectionUtils.isEmpty(members)) {
            for (Map<String, Object> map : members) {
                Integer id = Integer.parseInt(String.valueOf(map.get("id")));
                String is_admin = (String) map.get("is_admin");
                //判断是否在线
                boolean online = chatRoomService.checkRoomOnline(id);
                map.put("online", online);

                if (currentId == id && "2".equals(is_admin)) {
                    is_admin_flag = true;
                }
            }
        }
        model.addAttribute("members", members);
        model.addAttribute("is_admin", is_admin_flag);
        return "right_group_list";
    }
}
