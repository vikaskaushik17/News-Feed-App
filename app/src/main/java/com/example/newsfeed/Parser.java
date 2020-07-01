package com.example.newsfeed;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {


    private List<FeedEntry> application = new ArrayList<>();
    private static final String TAG = "Parser";

    public List<FeedEntry> getApplication() {
        return application;
    }

    protected boolean listXml(String xml) {
        FeedEntry feedEntry = null;
        boolean isEntry = true;
        boolean status = true;
        String textValue = "";

        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(xml));

            int event = xmlPullParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String tagName = xmlPullParser.getName();
                switch (event) {

                    case XmlPullParser.START_TAG:
                        if ("item".equalsIgnoreCase(tagName)) {
                            Log.d(TAG, "Start Tag Enter");
                            feedEntry = new FeedEntry();
                            isEntry = false;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xmlPullParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (!isEntry) {
                            if ("item".equalsIgnoreCase(tagName)) {
                                application.add(feedEntry);
                                Log.d(TAG, "END Tag ");
                            } else if ("title".equalsIgnoreCase(tagName)) {
                                feedEntry.setHeadLine(textValue);
                            } else if ("category".equalsIgnoreCase(tagName)) {
                                feedEntry.setPlace("Category : " + textValue);
                            } else if ("description".equalsIgnoreCase(tagName)) {
                                textValue = textValue.trim();
                                feedEntry.setDescription(textValue);
                            } else if ("pubDate".equalsIgnoreCase(tagName)) {
                                feedEntry.setTime("Updated : " + textValue);
                            }
                        }
                        break;
                    default:
                        //Nothing else to do
                }
                event = xmlPullParser.next();
            }
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;

    }

}
