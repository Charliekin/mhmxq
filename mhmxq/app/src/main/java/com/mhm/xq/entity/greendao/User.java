package com.mhm.xq.entity.greendao;

import com.mhm.xq.entity.base.BaseEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class User extends BaseEntity {
    @Id
    private Long id;
    private String name;
    private String mobile;
    private String email;
    private String nick_name;
    private String icon;
    @NotNull
    private int age;
    @NotNull
    private int gender;
    @NotNull
    private long ct;

    @Generated(hash = 763647269)
    public User(Long id, String name, String mobile, String email, String nick_name,
                String icon, int age, int gender, long ct) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.nick_name = nick_name;
        this.icon = icon;
        this.age = age;
        this.gender = gender;
        this.ct = ct;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNick_name() {
        return this.nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getCt() {
        return this.ct;
    }

    public void setCt(long ct) {
        this.ct = ct;
    }

}
