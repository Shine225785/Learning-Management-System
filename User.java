package com.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "user_uid", length = 50, nullable = false)
    private String uid;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    public User() {
    }

    public User(String uid, String password) {
        this.uid = uid;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
