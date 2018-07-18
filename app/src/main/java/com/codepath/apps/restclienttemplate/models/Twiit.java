package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by drake on 7/18/18
 */
public class Twiit {
    //Attributes
    public String body;
    public long uid; //DB id of the tweet
    public User user;
    public String createdAt;

    //deserialize the JSON
    public static Twiit fromJSON(JSONObject jsonObject) throws JSONException{
        Twiit twiit= new Twiit();

        //extract the values from JSON
        twiit.body = jsonObject.getString("text");
        twiit.uid = jsonObject.getLong("id");
        twiit.createdAt = jsonObject.getString("created_at");
        twiit.user = User.fromJSON(jsonObject.getJSONObject("user"));
        return twiit;

    }
}
