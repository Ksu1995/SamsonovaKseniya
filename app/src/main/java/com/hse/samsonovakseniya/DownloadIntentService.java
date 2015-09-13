package com.hse.samsonovakseniya;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.SyncStateContract;
import android.util.Log;

import com.hse.samsonovakseniya.rss.NewsRecord;
import com.hse.samsonovakseniya.rss.Record;

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
        for (int i = 0; i < urls.length; i++) {
            URL url = null;
            try {
                url = new URL(urls[i]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = conn.getInputStream();
                    XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                    XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                    xmlPullParser.setInput(inputStream, null);
                    int event = xmlPullParser.getEventType();
                    String currentTag = "";
                    Record record = null;
                    while (event != XmlPullParser.END_DOCUMENT) {
                        switch (event) {
                            case XmlPullParser.START_DOCUMENT: {
                                Log.i(TAG, "START_DOCUMENT");
                                break;
                            }
                            case XmlPullParser.START_TAG:
                                Log.i(TAG, "START_TAG" + xmlPullParser.getName());
                                currentTag = xmlPullParser.getName();
                                if (xmlPullParser.getName().equals("item")) {
                                    record = new NewsRecord();
                                } else if (xmlPullParser.getName().equals("title")) {
                                    //record.setTitle(xmlPullParser.getText());
                                } else if (xmlPullParser.getName().equals("description")) {
                                    //record.setDescription(xmlPullParser.getText());
                                } else if (xmlPullParser.getName().equals("pubDate")) {
                                    //record.setDate(new SimpleDateFormat().parse(xmlPullParser.getText()));
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if (xmlPullParser.getName().equals("item")) {
                                    Log.i(TAG, record.toString());
                                    records.add(record);
                                }
                                break;
                            case XmlPullParser.TEXT:
                                String text = xmlPullParser.getText();
                                Log.i(TAG, "TEXT " + currentTag + text);
                                text.trim();
                                if (record == null || text.length() == 0 || text.equals("")) {
                                    break;
                                }
                                if (currentTag.equals("title")) {
                                    record.setTitle(text);
                                } else if (currentTag.equals("description")) {
                                    record.setDescription(text);
                                } else if (currentTag.equals("pubDate")) {
                                    try {
                                        record.setDate(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").parse(text));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                currentTag = "";
                                break;
                            default:
                                break;
                        }
                        xmlPullParser.next();
                        event = xmlPullParser.getEventType();
                    }
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        data.putParcelableArrayList(NEWS_RECORDS, (ArrayList<Record>) records);
        receiver.send(STATUS_FINISHED, data);
    }
}
