package com.hse.samsonovakseniya.gui;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hse.samsonovakseniya.AdvancedNewsRecordActivity;
import com.hse.samsonovakseniya.R;
import com.hse.samsonovakseniya.rss.Record;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by User on 11.09.2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Record> mRecords;
    private Context mContext;

    public RecyclerViewAdapter(List<Record> records, Context context) {
        mRecords = records;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).
                inflate(R.layout.record_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Record record = mRecords.get(i);
        viewHolder.getTitle().setText(record.getTitle());
        viewHolder.getDate().setText(DateUtils.getRelativeTimeSpanString(record.getDate().getTime(), System.currentTimeMillis(),
                0, DateUtils.FORMAT_ABBREV_RELATIVE));
        if(!record.getImageUrl().isEmpty()) {
            Picasso.with(mContext).load(record.getImageUrl()).into(viewHolder.getImage());
        }
        viewHolder.getOrigin().setText(record.getOrigin());
        viewHolder.itemView.setOnClickListener(new OnClickListener(mRecords.get(i), mContext));

    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    static class OnClickListener implements View.OnClickListener {

        Record mRecord;
        Context mContext;

        public OnClickListener(Record record, Context context) {
            mRecord = record;
            mContext = context;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), AdvancedNewsRecordActivity.class);
            intent.putExtra(AdvancedNewsRecordActivity.RECORD, mRecord);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

}
