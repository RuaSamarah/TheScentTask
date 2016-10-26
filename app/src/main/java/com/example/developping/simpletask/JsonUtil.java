package com.example.developping.simpletask;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
    public static String toJSon(Bundle bundle) throws JSONException {
        try {
            String name = (String) bundle.getString("userName");
            String job = (String) bundle.getString("userJob");
            String about = (String) bundle.getString("userAbout");
            String friends = (String) bundle.getString("userFriends");

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("name", name);
            jsonObj.put("job", job);
            jsonObj.put("about", about);
            jsonObj.put("friends", friends);

            String[] resultArr = bundle.getStringArray("selectedItems");
            JSONArray jsonhobbies = new JSONArray();
            JSONObject item = new JSONObject();
            for (int i = 0; i < resultArr.length; i++) {
                item.put(""+i, resultArr[i]);
                jsonhobbies.put(item);
            }

            jsonObj.put("hobbies", jsonhobbies);


            return jsonObj.toString();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}