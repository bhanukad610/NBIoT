package com.gk.hasitha.slasscom.data;

/**
 * Created by hasitha on 8/17/17.
 */

public class CategoryData {

    public String id,cat_id,category,extra, img_url, paperName;

    public CategoryData(String id, String cat_id, String category, String extra, String img_url, String paperName) {
        this.id = id;
        this.cat_id = cat_id;
        this.category = category;
        this.extra = extra;
        this.img_url = img_url;
        this.paperName = paperName;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
