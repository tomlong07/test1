package com.finalprm.fuze.Util;

import java.io.Serializable;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Builder
//@Getter
//@Setter
//@ToString
public class User implements Serializable {

    private String userId;
    private String name;
    private String phone;
//    private String age;
    private String notificationKey;

    private Boolean selected = false;

    public User() {
    }
    public User(String userId){
        this.userId = userId;
    }
    public User(String userId, String name, String phone){
        this.userId = userId;
        this.name = name;
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }
    public String getPhone() {
        return phone;
    }
    public String getName() {
        return name;
    }
    public String getNotificationKey() {
        return notificationKey;
    }
    public Boolean getSelected() {
        return selected;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setNotificationKey(String notificationKey) {
        this.notificationKey = notificationKey;
    }
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

}
