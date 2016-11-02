package com.example.developping.simpletask;

import io.realm.RealmObject;


public class Item extends RealmObject {
    String hobby;

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }


}
