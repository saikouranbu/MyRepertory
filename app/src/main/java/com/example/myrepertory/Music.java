package com.example.myrepertory;

public class Music extends Object {
    private int id;
    private String name, artist;

    public Music(int id, String name, String artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        String str = name + " / " + artist;

        return str;
    }
}
