package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/**
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "blaXynQ4fVGVxDVLUVRVJwECA";
	public static final String REST_CONSUMER_SECRET = "w3xW4VuQqa9D0gkD8lxTEwdBEXTErSgPEFFaaqLXL3OpOVb4Q0";
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)
    private final AsyncHttpResponseHandler delegateHandler;

    public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
        delegateHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        };
    }
    
    public void createTweet(String message, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        
        RequestParams params = new RequestParams();
        params.put("status", message);
        getClient().post(apiUrl, params, handler);
    }

    public void getMentionsTimeLine(AsyncHttpResponseHandler handler) {
        getMentionsTimeLine(null, handler);
    }

    public void getHomeTimeLine(AsyncHttpResponseHandler handler) {
        getHomeTimeLine(null, handler);
    }
    
    public void getHomeTimeLine(Long maxId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        
        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (maxId != null) {
            params.put("max_id", maxId);
        }
        getClient().get(apiUrl, params, handler);
    }

    public void getMentionsTimeLine(Long maxId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (maxId != null) {
            params.put("max_id", maxId);
        }
        getClient().get(apiUrl, params, handler);
    }

    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
        getUserTimeline(screenName, null, handler);
    }

    public void getUserTimeline(String screenName, Long maxId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("screen_name", screenName);
        if (maxId != null) {
            params.put("max_id", maxId);
        }
        getClient().get(apiUrl, params, handler);
    }

    public void getUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, null, handler);
    }

    public void getUserInfo(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put(User.SCREEN_NAME, screenName);
        getClient().get(apiUrl, params, handler);
    }
}
