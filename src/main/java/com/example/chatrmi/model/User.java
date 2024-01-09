package com.example.chatrmi.model;

public class User {
    private int id;
    private String userName;
    private String email;
    private String password;
    private int online;
    private String avatar;
    private String ip;
    private int port;

    public User() {

    }

    public User(int id, String userName, String email, String password, int online, String avatar, String ip,
                int port) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.online = online;
        this.avatar = avatar;
        this.ip = ip;
        this.port = port;
    }

    public User(String userName,String password) {
        this.userName = userName;

        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
