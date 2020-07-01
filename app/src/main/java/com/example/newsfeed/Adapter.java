package com.example.newsfeed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter extends ArrayAdapter<FeedEntry> {

    private int layoutResource;
    private List<FeedEntry> applications;
    private LayoutInflater layoutInflater;
    private static final String TAG = "Adapter";

    public Adapter(Context context, int layoutResource, List<FeedEntry> applications) {
        super(context, layoutResource);
        this.layoutInflater = LayoutInflater.from(context);
        this.applications = applications;
        this.layoutResource = layoutResource;
    }

    @Override
    public int getCount() {
        return applications.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "adapter started");
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FeedEntry feedEntry = applications.get(position);

        viewHolder.headline.setText(feedEntry.getHeadLine());
        viewHolder.place.setText(feedEntry.getPlace());
        viewHolder.description.setText(feedEntry.getDescription());
        viewHolder.time.setText(feedEntry.getTime());
        Log.d(TAG, "adapter end");

        return convertView;
    }
}

class ViewHolder {
    final TextView headline;
    final TextView place;
    final TextView description;
    final TextView time;

    public ViewHolder(View v) {
        headline = v.findViewById(R.id.headline);
        place = v.findViewById(R.id.place);
        description = v.findViewById(R.id.description);
        time = v.findViewById(R.id.timeNews);
    }
}
