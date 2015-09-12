package com.hse.samsonovakseniya.rss;

import java.util.Date;

/**
 * Created by User on 11.09.2015.
 */
public interface Record {

    public String getOrigin();
    public String getTitle();
    public String getDescription();
    public Date getDate();

    public void setOrigin(String origin);
    public void setTitle(String title);
    public void setDescription(String description);
    public void setDate(Date date);

}
