package cn.liu.webChat.domain;

import java.util.Date;

/**
 * Created by liuf on 2017/1/14.
 */
public class UserMessage {
    private Long id;
    private String username;
    private String message;
    private Date time;
    private Long timeStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(Long timeStr) {
        this.timeStr = timeStr;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                ", timeStr=" + timeStr +
                '}';
    }
}
