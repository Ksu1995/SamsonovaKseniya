package com.hse.samsonovakseniya.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hse.samsonovakseniya.AdvancedNewsRecordActivity;
import com.hse.samsonovakseniya.DownloadIntentService;
import com.hse.samsonovakseniya.R;
import com.hse.samsonovakseniya.rss.Record;
import com.squareup.picasso.Picasso;

import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by User on 11.09.2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Record> mRecords;
    private View.OnClickListener mOnClickListener;
    private Context mContext;

    public RecyclerViewAdapter(List<Record> records, View.OnClickListener onClickListener, Context context) {
        mRecords = records;
        mOnClickListener = onClickListener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.record_view, viewGroup, false);
        v.setOnClickListener(mOnClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Record record = mRecords.get(i);
        viewHolder.getTitle().setText(record.getTitle());
        viewHolder.getDate().setText(DateUtils.getRelativeTimeSpanString(record.getDate().getTime(), System.currentTimeMillis(),
                        0, DateUtils.FORMAT_ABBREV_RELATIVE));
        Picasso.with(mContext).load(record.getImageUrl()).into(viewHolder.getImage());
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

}
