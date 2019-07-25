package cn.liu.webChat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * created by liufeng
 * 2019/7/25
 */
public class RoomMsg implements Serializable {
    private Integer id;
    private Integer room_id;
    private Integer user_id;
    private String msg;
    private Long time_str;
    private Date create_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTime_str() {
        return time_str;
    }

    public void setTime_str(Long time_str) {
        this.time_str = time_str;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "RoomMsg{" +
                "id=" + id +
                ", room_id=" + room_id +
                ", user_id=" + user_id +
                ", msg='" + msg + '\'' +
                ", time_str='" + time_str + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
