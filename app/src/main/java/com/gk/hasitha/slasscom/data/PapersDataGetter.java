package com.gk.hasitha.slasscom.data;

/**
 * Created by hasitha on 7/19/17.
 */

public class PapersDataGetter {
    private String id, paperID,paperName,paperRealName;

    public PapersDataGetter(String id, String paperID, String paperName, String paperRealName) {
        this.id = id;
        this.paperID = paperID;
        this.paperName = paperName;
        this.paperRealName = paperRealName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaperID() {
        return paperID;
    }

    public void setPaperID(String paperID) {
        this.paperID = paperID;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getPaperRealName() {
        return paperRealName;
    }

    public void setPaperRealName(String paperRealName) {
        this.paperRealName = paperRealName;
    }
}
