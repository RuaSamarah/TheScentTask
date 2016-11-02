package com.example.developping.simpletask;

import io.realm.RealmList;
import io.realm.RealmObject;


public class User extends RealmObject{


    private String userName;
    private String userJob;
    private String userAbout;
    private String userFriends;

    public RealmList<Item> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(RealmList<Item> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public RealmList<Item> selectedItems;

    public String getUserAbout() {
        return userAbout;
    }

    public void setUserAbout(String userAbout) {
        this.userAbout = userAbout;
    }

    public String getUserFriends() {
        return userFriends;
    }

    public void setUserFriends(String userFriends) {
        this.userFriends = userFriends;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



}
