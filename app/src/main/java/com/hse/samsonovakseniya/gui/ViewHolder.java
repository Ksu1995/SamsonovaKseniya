package com.hse.samsonovakseniya.gui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hse.samsonovakseniya.R;

/**
 * Created by User on 11.09.2015.
 */
public class ViewHolder extends RecyclerView.ViewHolder {


    private TextView mTitle;
    private ImageView mImage;
    private TextView mDate;
    private TextView mOrigin;

    public ViewHolder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.title_text);
        mImage = (ImageView) itemView.findViewById(R.id.news_image);
        mDate = (TextView) itemView.findViewById(R.id.date_text);
        mOrigin = (TextView) itemView.findViewById(R.id.origin_text);
    }

    public TextView getTitle() {
        return mTitle;
    }

    public TextView getDate() {
        return mDate;
    }

    public TextView getOrigin() {
        return mOrigin;
    }

    public ImageView getImage() {
        return mImage;
    }

}
