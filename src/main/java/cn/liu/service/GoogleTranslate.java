package cn.liu.service;


import com.alibaba.druid.support.json.JSONUtils;
import dev.xwolf.framework.common.utils.net.OkHttpUtils;
import dev.xwolf.framework.common.utils.string.Md5Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.security.provider.MD5;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuf on 2016/9/2.
 */
public class GoogleTranslate {
    public static void main(String[] args) {
//        System.out.println(Md5Util.md5("2015063000000001hello143566028812345678"));
        String text="hello";
    translate(text,"en","zh");
    }

    //有道翻译
    public static String translate(String text,String from,String to){
        String url="http://api.fanyi.baidu.com/api/trans/vip/translate";
        String q=text;
        String appId="20160903000028108";
        String salt="1435660287";
        String password="pDdLHYNRCYGAQrh8lUEc";
        String sign= Md5Util.md5(appId+q+salt+password);
        String requestUrl=url+"?q="+q+"&from="+from+"&to="+to+"&appid="+appId+"&salt="+salt+"&sign="+sign;
//        Document doc= JsoupUtils.getDocument("http://api.fanyi.baidu.com/api/trans/vip/translate?q=apple&from=en&to=zh&appid=2015063000000001&salt=&sign=f89f9594663708c1605f3d736d01d2d4");
        Document doc= JsoupUtils.getDocument(requestUrl);
        String json=doc.text();
            Map map= (Map) JSONUtils.parse(json);
            Object listMap=map.get("trans_result");
        if(listMap!=null){
            List<Map<String,String>> translateText= (List<Map<String, String>>) listMap;
            Map<String,String> dstMap=translateText.get(0);
            String dst=dstMap.get("dst");
            System.out.println(dst);
            return dst;
        }
            return null;
    }
}
