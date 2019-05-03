package com.samuelbernard147.smarthomev2.alirkan;

import org.json.JSONArray;

public class RiwayatItems {
    private String author, time, humidity;

    RiwayatItems(JSONArray object, int posisi) {
        try {
//                String pemilik = object.getJSONObject(posisi).getString("author");
            String kelembapan = object.getJSONObject(posisi).getString("kelambapan_Tanah");
            String waktu = object.getJSONObject(posisi).getString("created_at");
//                this.author = pemilik;
            this.time = waktu.substring(11);
            this.humidity = kelembapan;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
