package com.example.newsfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String STATIC_URL = "Invalid";
    private String feedUrl = "https://www.thehindu.com/feeder/default.rss";

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        if (savedInstanceState != null) {
            feedUrl = savedInstanceState.getString("url");
        }
        downloadUrl(feedUrl);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.news_home:
                feedUrl = "https://www.thehindu.com/feeder/default.rss";
                break;
            case R.id.sport:
                feedUrl = "https://www.thehindu.com/sport/feeder/default.rss";
                break;
            case R.id.entertainment:
                feedUrl = "https://www.thehindu.com/entertainment/feeder/default.rss";
                break;
            case R.id.refresh:
                STATIC_URL = "Refresh starts";
                break;
            default:
                return super.onOptionsItemSelected(item);
            //Nothing
        }
        downloadUrl(feedUrl);
        return true;

    }

    protected void downloadUrl(String url) {
        if (!url.equalsIgnoreCase(STATIC_URL)) {
            Log.d(TAG, "Link input start");
            Download download = new Download();
            download.execute(url);
            STATIC_URL = url;
            Log.d(TAG, "Link input end");
        } else {

        }
    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        feedUrl = savedInstanceState.getString("url");
//    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("url", feedUrl);
        super.onSaveInstanceState(outState);
    }

    private class Download extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "Async start");
            super.onPostExecute(s);
            Parser parser = new Parser();
            parser.listXml(s);

            Adapter adapter = new Adapter(MainActivity.this, R.layout.list_record, parser.getApplication());
            listView.setAdapter(adapter);
            Log.d(TAG, "Async End");

//           ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<FeedEntry>(MainActivity.this,R.layout.list_record,R.id.headline,parser.getApplication());
//           listView.setAdapter(arrayAdapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            String rssFeed = downLoad(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "RSS feed is null");
            }
            return rssFeed;
        }

        protected String downLoad(String urlPath) {
            StringBuilder xmlStore = new StringBuilder();
            try {
                URL url = new URL(urlPath);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                int chars;
                char[] input = new char[500];
                while (true) {
                    chars = bufferedReader.read(input);
                    if (chars < 0) {
                        break;
                    }
                    if (chars > 0) {
                        xmlStore.append(String.copyValueOf(input, 0, chars));
                    }
                }
                bufferedReader.close();
                return xmlStore.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "Malformed Exception ");
            } catch (IOException ioe) {
                Log.e(TAG, "IOException ");
            } catch (SecurityException s) {
                Log.d(TAG, "Security exception");
            }
            return null;
        }
    }

}

