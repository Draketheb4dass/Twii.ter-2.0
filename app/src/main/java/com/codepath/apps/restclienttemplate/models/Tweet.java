package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by drake on 7/18/18
 */
@Parcel
public class Tweet {
    //Attributes
    public String body;
    public long uid; //DB id of the tweet
    public User user;
    public String createdAt;
    public ArrayList<String> extendedEntities;

    public Tweet() {} //Empty public constructor for parceler

    //deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();

        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        //tweet.extendedEntities = ExtendedEntities.fromJSON
        return tweet;

    }
    //deserialize JSONArray
    public static Tweet fromJSONArray(JSONArray jsonArray, int index) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonArray.getJSONObject(index).getString("text");
        tweet.createdAt = jsonArray.getJSONObject(index).getString("created_at");
        tweet.user = User.fromJSON(jsonArray.getJSONObject(index).getJSONObject("user"));
        return tweet;
    }
}
