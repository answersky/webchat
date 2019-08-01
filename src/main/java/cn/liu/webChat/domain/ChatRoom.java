package cn.liu.webChat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * created by liufeng
 * 2019/7/25
 */
public class ChatRoom {
    private Integer id;
    private String room_name;
    private int limit_num;
    private String is_group;
    private Date create_time;
    private Date update_time;
    private String create_user;
    private String update_user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public int getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(int limit_num) {
        this.limit_num = limit_num;
    }

    public String getIs_group() {
        return is_group;
    }

    public void setIs_group(String is_group) {
        this.is_group = is_group;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id=" + id +
                ", room_name='" + room_name + '\'' +
                ", limit_num=" + limit_num +
                ", is_group='" + is_group + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", create_user='" + create_user + '\'' +
                ", update_user='" + update_user + '\'' +
                '}';
    }
}
