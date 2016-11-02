package com.example.developping.simpletask;

import io.realm.RealmModel;

/**
 * Created by ruaasamara on 11/2/2016 AD.
 */
public class Item implements RealmModel {
    String hobby;

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }


}
