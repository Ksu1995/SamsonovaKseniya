package com.hse.samsonovakseniya.rss;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.text.ParseException;
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
    private String mImageUrl;

    public NewsRecord() {
        mOrigin = "";
        mTitle = "";
        mDescription = "";
        mDate = new Date();
        mImageUrl = "";
    }

    @Override
    public String toString() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

        String result = getTitle() + "  ( " + simpleDateFormat.format(getDate()) + " )" + getDescription();
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
    public String getImageUrl() {
        return mImageUrl;
    }

   /* public Bitmap getImage() {
        return mImage;
    }*/

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

    @Override
    public void setImageUrl(String url) {
        mImageUrl = url;
        //createImageFromUrl();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {mOrigin,
                mTitle,
                mDescription,
                new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(mDate),
                mImageUrl});

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public  NewsRecord createFromParcel(Parcel in) {
            return new  NewsRecord(in);
        }

        public  NewsRecord[] newArray(int size) {
            return new  NewsRecord[size];
        }
    };

    private NewsRecord(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        mOrigin = data[0];
        mTitle = data[1];
        mDescription = data[2];
        try {
            mDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").parse(data[3]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mImageUrl = data[4];
    }

    @Override
    public int compareTo(@NonNull Object another) {
        Date datel =  this.getDate();
        Date dater = ((NewsRecord) another).getDate();
        return dater.compareTo(datel);
    }
}
