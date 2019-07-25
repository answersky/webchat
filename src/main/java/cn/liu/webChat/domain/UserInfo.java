package cn.liu.webChat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * created by liufeng
 * 2019/7/25
 */
public class UserInfo implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String nickname;
    private int age;
    private String address;
    private String photo_address;
    private Date create_time;
    private Date update_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto_address() {
        return photo_address;
    }

    public void setPhoto_address(String photo_address) {
        this.photo_address = photo_address;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", photo_address='" + photo_address + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
