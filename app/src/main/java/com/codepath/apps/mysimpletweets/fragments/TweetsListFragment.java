package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public abstract class TweetsListFragment extends Fragment implements Updateable {

    public void update() {
        populateTimeline();
    }
    
    protected ArrayList<Tweet> tweets;
    protected TweetsArrayAdapter adapter;
    protected ListView lvTweets;
    protected long lastTweetId;
    private boolean mReloadStream;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        adapter = new TweetsArrayAdapter(getActivity(), tweets);

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(adapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            protected void onLoadMore(int page, int totalItemCount) {
                loadMoreDataFromApi();
            }
        });
        if (mReloadStream) {
            populateTimeline();
        }
        return v;
    }
    
    public void reloadStream() {
        mReloadStream = true;
        
        if (adapter != null) {
            populateTimeline();
        }
    }

    protected void showNetworkError() {
        Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_LONG).show();
    }

    protected void addTweets(JSONArray response) {
        final ArrayList<Tweet> collection = Tweet.fromJsonArray(response);
        final int size = collection.size();
        if (size > 0) {
            final Tweet lastTweet = collection.get(size - 1);
            lastTweetId = lastTweet.getUid();
        } else {
            lastTweetId = 0;
        }
        adapter.addAll(collection);
        adapter.notifyDataSetChanged();
    }

    protected abstract void loadMoreDataFromApi();

    protected void populateTimeline() {
        mReloadStream = false;
    }

    public void addAll(List<Tweet> allTweets) {
        adapter.addAll(allTweets);
    }

    public TweetsArrayAdapter getAdapter() {
        return adapter;
    }
}
