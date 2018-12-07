package com.me.ui.sample.library.entity;

/**
 * @author kylingo
 * @since 2018/12/07 16:11
 */
public class UserEntity {

    private String username;
    private String nickname;
    private int age;

    public UserEntity() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserEntity(int age, String nickname, String username) {
        this.age = age;
        this.nickname = nickname;
        this.username = username;
    }
}
