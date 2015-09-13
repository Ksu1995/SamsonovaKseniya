package com.hse.samsonovakseniya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hse.samsonovakseniya.rss.Record;

/**
 * Created by User on 13.09.2015.
 */
public class AdvancedNewsRecordActivity extends Activity {

    private TextView mTitle;
    private ImageView mImage;
    private TextView mDescription;
    private TextView mDate;
    public static final String RECORD = "record";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_record_view);
        Record record = savedInstanceState.getParcelable(RECORD);

        mTitle = (TextView) findViewById(R.id.title_text);
        mImage = (ImageView) findViewById(R.id.news_image);
        mDescription = (TextView) findViewById(R.id.description_text);
        mDate = (TextView) findViewById(R.id.date_text);

        mTitle.setText(record.getTitle());
        mDate.setText(record.getDate().toString());
        mDescription.setText(record.getDescription());

    }

}
