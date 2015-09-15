package com.hse.samsonovakseniya.rss;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by User on 15.09.2015.
 */
public class RssPullParser {

    public RssPullParser(XmlPullParser xmlPullParser) {
        mXmlPullParser = xmlPullParser;
        mCurrentTag = "";
        mOrigin = null;
        try {
            mEvent = xmlPullParser.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private int mEvent;
    private String mCurrentTag;
    private String mOrigin;
    private XmlPullParser mXmlPullParser;
    private Record mRecord;
    private final static String TAG = RssPullParser.class.getSimpleName();

    public void setEvent() {
        try {
            this.mEvent = mXmlPullParser.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentTag(String currentTag) {
        mCurrentTag = currentTag;
    }

    public Record getRecord() {
        return mRecord;
    }

    public int getEvent() {
        return mEvent;
    }

    public String getOrigin() {
        return mOrigin;
    }

    @Nullable
    public void onTagStart() {
        if (mCurrentTag.equals("item")) {
            mRecord = new NewsRecord();
        }
        if (mRecord != null && mCurrentTag.equals("enclosure")) {
            mRecord.setImageUrl(mXmlPullParser.getAttributeValue(null, "url"));
        }
    }

    @NonNull
    public void onTagText() {
        String text = mXmlPullParser.getText();
        Log.i(TAG, "TEXT " + mCurrentTag + text);
        if (mRecord == null || text.length() == 0 || text.equals("")) {
            if (mCurrentTag.equals("link") && mOrigin == null){
                mOrigin = text;
            }
            return;
        }
        switch (mCurrentTag) {
            case "title":
                mRecord.setTitle(text);
                break;
            case "description":
                mRecord.setDescription(text);
                break;
            case "pubDate":
                try {
                    mRecord.setDate(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").parse(text));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
