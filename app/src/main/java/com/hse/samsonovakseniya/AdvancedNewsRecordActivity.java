package com.hse.samsonovakseniya;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hse.samsonovakseniya.rss.Record;
import com.squareup.picasso.Picasso;

/**
 * Created by User on 13.09.2015.
 */
public class AdvancedNewsRecordActivity extends Activity {

    public static final String RECORD = "record";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_record_view);
        final Record record = getIntent().getParcelableExtra(RECORD);

        TextView title = (TextView) findViewById(R.id.title_text);
        ImageView image = (ImageView) findViewById(R.id.news_image);
        TextView description = (TextView) findViewById(R.id.description_text);
        TextView date = (TextView) findViewById(R.id.date_text);
        TextView origin = (TextView) findViewById(R.id.origin_text);

        title.setText(record.getTitle());
        date.setText(DateUtils.getRelativeTimeSpanString(record.getDate().getTime(), System.currentTimeMillis(),
                0, DateUtils.FORMAT_ABBREV_RELATIVE));
        description.setText(record.getDescription());
        if (!record.getImageUrl().isEmpty()) {
            Picasso.with(this).load(record.getImageUrl()).into(image);
        }
        origin.setText(record.getOrigin());
    }

}
