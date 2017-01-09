package cn.liu.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhp on 2016/1/25.
 */
public class JsoupUtils {
    /**
     * 默认无参数的get请求
     *
     * @param url
     * @return
     */
    public static Document getDocument(final String url) {
        return getDocument(url, null, false);
    }

    /**
     * 默认get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static Document getDocument(final String url, final Map<String, String> params) {
        return getDocument(url, params, false);
    }

    /**
     *
     * @param url
     *          目标的url地址
     * @param params
     *          参数（map《String:String》）
     * @param isPost
     *          true=post 是否是post请求
     * @return
     */
    /**
     * @param url    目标的url地址
     * @param params 参数（map《String:String》）
     * @param isPost true=post 是否是post请求
     * @return
     */
    public static Document getDocument(final String url, final Map<String, String> params, final boolean isPost) {
        return getDocument(url, params, isPost, null);
    }

    public static Document getDocument(final String url, final Map<String, String> params, final boolean isPost, final Map<String, String> cookies) {
        Connection connection = Jsoup.connect(url);
        if (params != null && params.size() > 0) {
            connection.data(params);// 设置请求的参数
        }
        connection = setHeader(connection, url, cookies);
        Document document = null;
        try {
            // 根据ispost设置 来发送
            if (!isPost) {
                document = connection.get();
            } else {
                document = connection.post();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 设置头信息，connection 链接
     *
     * @param connection 链接
     * @param url        链接
     * @param cookies    cookies信息
     * @return
     */
    private static Connection setHeader(final Connection connection, final String url, Map cookies) {
        // 增加header
        if (cookies == null) {
            cookies = new HashMap();
        }
        connection.userAgent("Mozilla/5.0")
                // 火狐的头信息
                .header("Referer", url)
                        // 来源地址
                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                        // 模拟浏览器的语言设置
                .ignoreContentType(true).header("Accept-Encoding", "gzip, deflate, sdch").header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Cache-Control", "max-age=0").header("Connection", "keep-alive").cookie("cookie", getCookie()).ignoreHttpErrors(true)// 忽略错误
                .timeout(10 * 1000).cookies(cookies);// 设置超时时间 10秒
        return connection;
    }

    private static String getCookie() {
        final String cookie = "BAIDUID=A2E906B86BBA9083A8D7750F416CF05B:FG=1; BIDUPSID=CD7B59859308250C3BB15F9FEC2D2F85; PSTM=1455875954; MCITY=-%3A; BDUSS=jZNdX4tQTc5Q05LVE9GUUdNVEdhZWZjZTBBZDBXWHdELVRyMGM2eG9zMk1vUkJYQVFBQUFBJCQAAAAAAAAAAAEAAAD-GNYicXNjZmRlcjI0NAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIwU6VaMFOlWf; BD_UPN=12314353; ispeed_lsm=2; H_PS_645EC=0987aRlwHz8UpcepFAbRfrTxNwAqhbDZ%2BWPlrbERQnUM4vcpJ5m7TN%2FeW0NkhUObgR2V; WWW_ST=1458615413243; BD_CK_SAM=1; BDSVRTM=253; H_PS_PSSID=18880_1462_18205_17001_15505_12414_18088_18017; __bsi=13307284694396886923_00_13_R_N_255_0303_C02F_N_I_I_0";
        return cookie;
    }

    /**
     * 抓取url对应的html
     *
     * @param url
     * @return
     */
    public static String getHtml(final String url) {
        return getDocument(url).html();
    }

    /**
     * @param url
     * @return
     */
    public static int getUrlStatus(final String url) {
        try {
            Connection connection = Jsoup.connect(url);
            connection = setHeader(connection, url, null);
            return connection.execute().statusCode();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
