package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public static final String SCREEN_NAME = "screen_name";

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagLine;
    private int following;
    private int followers;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static User fromJSON(JSONObject object) {
        User u = new User();
        try {
            u.name = object.getString("name");
            u.uid = object.getLong("id");
            u.screenName = object.getString("screen_name");
            u.profileImageUrl = object.getString("profile_image_url");
            u.tagLine = object.getString("description");
            u.followers = object.getInt("followers_count");
            u.following = object.getInt("followings_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }

    public String getTagLine() {
        return tagLine;
    }

    public int getFollowing() {
        return following;
    }

    public int getFollowers() {
        return followers;
    }
}
