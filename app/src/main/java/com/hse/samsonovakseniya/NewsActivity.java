package com.hse.samsonovakseniya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hse.samsonovakseniya.gui.RecyclerViewAdapter;
import com.hse.samsonovakseniya.rss.Record;
import java.util.Collections;
import java.util.List;

public class NewsActivity extends Activity implements DownloadResultsReceiver.Receiver {

    private DownloadResultsReceiver mReceiver;
    private ProgressBar mProgress;
    private RecyclerView mRecyclerView;
    private List<Record> mRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mReceiver = new DownloadResultsReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(getApplicationContext(), DownloadIntentService.class);
        intent.putExtra(DownloadIntentService.URLs_EXTRA, new String[] {
                "http://lenta.ru/rss", "http://www.gazeta.ru/export/rss/lenta.xml"});
        intent.putExtra(DownloadIntentService.RECEIVER, mReceiver);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new DownloadResultsReceiver(new Handler());
        mReceiver.setReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mReceiver.setReceiver(null);
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

    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        switch (resultCode) {
            case DownloadIntentService.STATUS_RUNNING :
                mProgress.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Service started with data: "
                             , Toast.LENGTH_SHORT).show();
                break;
            case DownloadIntentService.STATUS_FINISHED :
                mProgress.setVisibility(View.INVISIBLE);
                mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
                mRecords = data.
                        <Record>getParcelableArrayList(DownloadIntentService.NEWS_RECORDS);
                Collections.sort(mRecords);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(mRecords, getApplicationContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);
                RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setItemAnimator(itemAnimator);
                Toast.makeText(this, "Service finished with data: "
                        , Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
