package com.vilect.asm.model;

public class NewsModel {

    private String title;
    private String des;
    private String pubDate;
    private String link;

    public NewsModel(String title, String des, String pubDate, String link) {
        this.title = title;
        this.des = des;
        this.pubDate = pubDate;
        this.link = link;
    }

    public NewsModel()
    {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
