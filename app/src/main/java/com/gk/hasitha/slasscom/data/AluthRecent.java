package com.gk.hasitha.slasscom.data;

/**
 * Created by hasitha on 1/20/18.
 */

public class AluthRecent {

    private int id;
    private String title;
    private String imgsrc;
    private String description;
    private String subTitle;

    public AluthRecent(int id, String title, String imgsrc, String description, String subTitle) {
        this.id = id;
        this.title = title;
        this.imgsrc = imgsrc;
        this.description = description;
        this.subTitle = subTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
