package com.hse.samsonovakseniya.gui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hse.samsonovakseniya.R;
import com.hse.samsonovakseniya.rss.Record;

import java.util.List;

/**
 * Created by User on 11.09.2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Record> mRecords;

    public RecyclerViewAdapter(List<Record> records) {
        mRecords = records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.record_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Record record = mRecords.get(i);
        viewHolder.getTitle().setText(record.getTitle());
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }
}
