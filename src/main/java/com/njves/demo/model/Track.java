package com.njves.demo.model;

import java.util.Objects;

public class Track {
    private String artist;
    private String title;
    private long length;
    private String coverLink;

    private String fileLink;
    public Track() {

    }

    public Track(String artist) {
        this.artist = artist;
    }

    public Track(String artist, String title, long length, String coverLink, String fileLink) {
        this.artist = artist;
        this.title = title;
        this.length = length;
        this.coverLink = coverLink;
        this.fileLink = fileLink;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public String getFileLink() {
        return fileLink;
    }


    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return length == track.length && Objects.equals(artist, track.artist) && Objects.equals(title, track.title) && Objects.equals(coverLink, track.coverLink) && Objects.equals(fileLink, track.fileLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, title, length, coverLink, fileLink);
    }

    @Override
    public String toString() {
        return "Track{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", length=" + length +
                ", coverLink='" + coverLink + '\'' +
                ", fileLink='" + fileLink + '\'' +
                '}';
    }


}
