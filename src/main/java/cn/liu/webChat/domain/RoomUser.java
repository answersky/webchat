package cn.liu.webChat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * created by liufeng
 * 2019/7/25
 */
public class RoomUser implements Serializable {
    private Integer id;
    private Integer room_id;
    private Integer user_id;
    private Date create_time;
    private String is_admin;
    private String create_user;

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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    @Override
    public String toString() {
        return "RoomUser{" +
                "id=" + id +
                ", room_id=" + room_id +
                ", user_id=" + user_id +
                ", create_time=" + create_time +
                ", is_admin='" + is_admin + '\'' +
                ", create_user='" + create_user + '\'' +
                '}';
    }
}
