package com.insticator.entity;

public class User {
    private String username;
    private String password;
    private Integer points;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.points = 0;
    }
    public User(String username, String password, Integer points) {
        this(username,password);
        this.points = points;
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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}

