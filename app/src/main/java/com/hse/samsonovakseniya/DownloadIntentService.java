package com.hse.samsonovakseniya;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hse.samsonovakseniya.rss.NewsRecord;
import com.hse.samsonovakseniya.rss.Record;
import com.hse.samsonovakseniya.rss.RssPullParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11.09.2015.
 */
public class DownloadIntentService extends IntentService {

    private static final String TAG = DownloadIntentService.class.getSimpleName();

    public static final String URLs_EXTRA = "url";
    public static final String RECEIVER = "receiver";
    public static final String NEWS_RECORDS = "news_records";


    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;

    public DownloadIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(STATUS_RUNNING, Bundle.EMPTY);
        final Bundle data = new Bundle();
        String[] urls = intent.getStringArrayExtra(URLs_EXTRA);
        List<Record> records = new ArrayList<>();
        for (String urlStr : urls) {
            Log.i("EXTRA_URL   ", urlStr);
            URL url = null;
            try {
                url = new URL(urlStr);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent","");
                Log.i("RES_CODE", ""+conn.getResponseCode());
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = conn.getInputStream();
                    XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                    XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                    xmlPullParser.setInput(inputStream, null);
                    RssPullParser rssPullParser = new RssPullParser(xmlPullParser);

                    while (rssPullParser.getEvent() != XmlPullParser.END_DOCUMENT) {
                        switch (rssPullParser.getEvent()) {
                            case XmlPullParser.START_DOCUMENT: {
                                Log.i(TAG, "START_DOCUMENT");
                                break;
                            }
                            case XmlPullParser.START_TAG:
                                Log.i(TAG, "START_TAG" + xmlPullParser.getName());
                                rssPullParser.setCurrentTag(xmlPullParser.getName());
                                rssPullParser.onTagStart();
                                break;
                            case XmlPullParser.END_TAG:
                                if (xmlPullParser.getName().equals("item")) {
                                    Log.i(TAG, rssPullParser.getRecord().toString());
                                    rssPullParser.getRecord().setOrigin(rssPullParser.getOrigin());
                                    records.add(rssPullParser.getRecord());
                                }
                                break;
                            case XmlPullParser.TEXT:
                                rssPullParser.onTagText();
                                rssPullParser.setCurrentTag("");
                                break;
                            default:
                                break;
                        }
                        xmlPullParser.next();
                        rssPullParser.setEvent();
                    }
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        data.putParcelableArrayList(NEWS_RECORDS, (ArrayList<Record>) records);
        receiver.send(STATUS_FINISHED, data);
    }

}
