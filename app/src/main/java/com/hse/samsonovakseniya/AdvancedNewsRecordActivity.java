package com.hse.samsonovakseniya;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hse.samsonovakseniya.rss.Record;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
        final Record record = getIntent().getParcelableExtra(RECORD);

        mTitle = (TextView) findViewById(R.id.title_text);
        mImage = (ImageView) findViewById(R.id.news_image);
        mDescription = (TextView) findViewById(R.id.description_text);
        mDate = (TextView) findViewById(R.id.date_text);

        mTitle.setText(record.getTitle());
        mDate.setText(DateUtils.getRelativeTimeSpanString(record.getDate().getTime(), System.currentTimeMillis(),
                0, DateUtils.FORMAT_ABBREV_RELATIVE));
        mDescription.setText(record.getDescription());
        Picasso.with(this).load(record.getImageUrl()).into(mImage);
    }

}
