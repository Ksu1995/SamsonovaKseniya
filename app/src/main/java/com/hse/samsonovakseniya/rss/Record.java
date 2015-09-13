package com.hse.samsonovakseniya.rss;

import android.graphics.Bitmap;
import android.os.Parcelable;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by User on 11.09.2015.
 */
public interface Record extends Parcelable, Comparable {

    String getOrigin();
    String getTitle();
    String getDescription();
    Date getDate();
    String getImageUrl();

    void setOrigin(String origin);
    void setTitle(String title);
    void setDescription(String description);
    void setDate(Date date);
    void setImageUrl(String url);

}
