package cn.liu.webChat.domain;

import java.util.Map;

/**
 * Created by liuf on 2017/1/7.
 */
public class Message {
    /**
     * 最近一次拿消息的时间戳
     */
    private long time;
    /**
     * 消息
     */
    private Map<String,String> map;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Message{" +
                "time=" + time +
                ", map=" + map +
                '}';
    }
}
