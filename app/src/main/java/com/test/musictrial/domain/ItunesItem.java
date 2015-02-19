package com.test.musictrial.domain;

/**
 * Created by albert on 17/02/15.
 */
public class ItunesItem {
    private int trackId;
    private String title;
    private String artist;
    private String collection;
    private String date;
    private String thumbnail;
    private long duration;
    private String genere;
    private double price;
    private String currency;
    private String streamUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** Itunes item *****\n");
        stringBuilder.append("title=" + this.getTitle() + "\n");
        stringBuilder.append("artist=" + this.getArtist() + "\n");
        stringBuilder.append("collection=" + this.getCollection() + "\n");
        stringBuilder.append("duration=" + this.getDuration() + "\n");
        stringBuilder.append("genere=" + this.getGenere() + "\n");
        stringBuilder.append("price=" + this.getPrice() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }
}
