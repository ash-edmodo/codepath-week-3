package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private final Context context;

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        Tweet tweet = getItem(position);
        
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);

        final User user = tweet.getUser();
        tvName.setText(user.getScreenName()+ " : " + user.getName());
        tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(tweet.getCreatedAtDate().getTime()));
        tvBody.setText(tweet.getBody());

        ivProfile.setImageResource(0);
        Picasso.with(getContext()).load(user.getProfileImageUrl()).into(ivProfile);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra(User.SCREEN_NAME, user.getScreenName());
                context.startActivity(i);
            }
        });
        return convertView;
    }
}
