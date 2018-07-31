package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by drake on 7/18/18
 */

@Parcel
public class User {
    //Attributes
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;
    public String profileBannerUrl;

    public String tagLine;
    public int followersCount;
    public int followingCount;


    //deserialize the JSON
    public static  User fromJSON(JSONObject json) throws JSONException {
        User user = new User();

        //extract and fill the values
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        user.profileBannerUrl = json.getString("profile_banner_url")+"/mobile";

        user.tagLine = json.getString("description");
        user.followersCount = json.getInt("followers_count");
        user.followingCount = json.getInt("friends_count");
        return user;
    }

}

