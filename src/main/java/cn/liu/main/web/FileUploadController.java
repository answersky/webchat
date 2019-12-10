package cn.liu.main.web;

import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.service.IUserInfoService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * created by liufeng
 * 2019/9/27
 */
@RequestMapping("/file")
@Controller
public class FileUploadController {
    private Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    @Resource
    private Environment environment;
    @Resource
    private IUserInfoService userInfoService;

    /**
     * 上传头像
     *
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String url = null;
        //文件存放的路径
        String path = environment.getProperty("save_img_path");
        String host = environment.getProperty("visit_img_url");

        if (!file.isEmpty()) {
            FileOutputStream fos = null;
            try {
                String fName = formatPicName(file);
                byte[] bytes = file.getBytes();
                fos = new FileOutputStream(path + fName);
                fos.write(bytes);
                url = host + fName;

                //更新用户的图片地址
                UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
                user.setPhoto_address(url);
                userInfoService.updateInfo(user);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return url;
    }

    /**
     * 上传聊天图片
     *
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/chatPicUpload")
    @ResponseBody
    public String chatPicUpload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String url = null;
        //文件存放的路径
        String path = environment.getProperty("save_chat_img_path");
        String host = environment.getProperty("visit_chat_img_url");

        if (!file.isEmpty()) {
            FileOutputStream fos = null;
            try {
                String fName = formatPicName(file);
                byte[] bytes = file.getBytes();
                fos = new FileOutputStream(path + fName);
                fos.write(bytes);
                url = host + fName;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return url;
    }

    private String formatPicName(MultipartFile file) {
        String fName = "";
        String orgiginalFileName = file.getOriginalFilename();
        if (orgiginalFileName.contains(".")) {
            int indexdot = orgiginalFileName.indexOf(".");
            String suffix = orgiginalFileName.substring(indexdot);
            String uuid = UUID.randomUUID().toString();
            fName = uuid + suffix;
        }
        return fName;
    }
}
