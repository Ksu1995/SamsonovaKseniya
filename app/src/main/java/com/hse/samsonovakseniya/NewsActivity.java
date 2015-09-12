package com.hse.samsonovakseniya;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.hse.samsonovakseniya.gui.RecyclerViewAdapter;
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

public class NewsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //List<Record> records = new ArrayList<>();


        /*RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(records);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);*/
        Intent intent = new Intent(getApplicationContext(), DownloadIntentService.class);
        intent.putExtra(DownloadIntentService.URL_EXTRA, "http://lenta.ru/rss");
        startService(intent);
    }


@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*switch (resultCode) {
            case DownloadIntentService.INVALID_URL_CODE:
                break;
            case DownloadIntentService.ERROR_CODE:
                break;
            case DownloadIntentService.RESULT_CODE:
                List<Record> records = new ArrayList<>();
                int event = 0;
                try {
                    event = xmlPullParser.getEventType();
                    Record record = null;
                    while (event != XmlPullParser.END_DOCUMENT) {

                        switch (event) {
                            case XmlPullParser.START_DOCUMENT:
                            case XmlPullParser.START_TAG:
                                if (xmlPullParser.getName().equals("item")) {
                                    record = new NewsRecord();
                                } else if (xmlPullParser.getName().equals("title")) {
                                    record.setTitle(xmlPullParser.getText());
                                } else if (xmlPullParser.getName().equals("description")) {
                                    record.setDescription(xmlPullParser.getText());
                                } else if (xmlPullParser.getName().equals("pubDate")) {
                                    record.setDate(new SimpleDateFormat().parse(xmlPullParser.getText()));
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if (xmlPullParser.getName().equals("item")) {
                                    records.add(record);
                                }
                                break;
                            case XmlPullParser.TEXT:
                                break;
                            default:
                                break;
                        }

                        xmlPullParser.next();
                    }
                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(records);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);
                    RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(itemAnimator);

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }*/
    super.onActivityResult(requestCode, resultCode, data);
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
