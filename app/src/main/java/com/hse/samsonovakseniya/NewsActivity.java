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

import com.baoyz.widget.PullRefreshLayout;
import com.hse.samsonovakseniya.gui.RecyclerViewAdapter;
import com.hse.samsonovakseniya.rss.Record;
import java.util.Collections;
import java.util.List;

public class NewsActivity extends Activity implements DownloadResultsReceiver.Receiver {

    private DownloadResultsReceiver mReceiver;
    private PullRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private static final String[] sUrls = new String[]{
            "http://lenta.ru/rss",
            "http://www.gazeta.ru/export/rss/lenta.xml"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mReceiver = new DownloadResultsReceiver(new Handler());
        mReceiver.setReceiver(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(itemAnimator);
        mRefreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        mRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = new Intent(getApplicationContext(), DownloadIntentService.class);
                intent.putExtra(DownloadIntentService.URLs_EXTRA, sUrls);
                intent.putExtra(DownloadIntentService.RECEIVER, mReceiver);
                startService(intent);
            }
        });

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
                break;
            case DownloadIntentService.STATUS_FINISHED :
                List<Record> records = data.
                        getParcelableArrayList(DownloadIntentService.NEWS_RECORDS);
                Collections.sort(records);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(records, getApplicationContext());
                mRecyclerView.setAdapter(adapter);
                break;
        }
        mRefreshLayout.setRefreshing(false);
    }
}
