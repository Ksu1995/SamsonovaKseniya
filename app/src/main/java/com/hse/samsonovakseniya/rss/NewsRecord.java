package com.hse.samsonovakseniya.rss;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 11.09.2015.
 */
public class NewsRecord implements Record {

    private String mOrigin;
    private String mTitle;
    private String mDescription;
    private Date mDate;

    public NewsRecord() {
        mOrigin = "";
        mTitle = "";
        mDescription = "";
        mDate = new Date();
    }

    public NewsRecord(String origin, String title, String description, Date date){
        mOrigin = origin;
        mTitle = title;
        mDescription = description;
        mDate = date;
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");

        String result = getTitle() + "  ( " + sdf.format(getDate()) + " )";
        return result;
    }

    @Override
    public String getOrigin() {
        return mOrigin;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }

    @Override
    public Date getDate() {
        return mDate;
    }

    @Override
    public void setOrigin(String origin) {
        mOrigin = origin;
    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public void setDate(Date date) {
        mDate = date;
    }

}
