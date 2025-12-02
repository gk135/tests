package com.gitlab.rmarzec.framework.model;

public class YTTile {
    private String title;
    private String channel;
    private String length;

    public YTTile(String title, String channel, String length) {
        this.title = title;
        this.channel = channel;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public boolean isLive() {
        return "live".equalsIgnoreCase(length);
    }

    @Override
    public String toString() {
        return  "title='" + title + '\'' +
                ", channel='" + channel + '\'' +
                ", length='" + length + '\'' ;

    }
}
