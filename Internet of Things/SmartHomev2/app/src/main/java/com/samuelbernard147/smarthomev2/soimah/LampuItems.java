package com.samuelbernard147.smarthomev2.soimah;

import org.json.JSONArray;

public class LampuItems {
    private int id, status;
    private String ruangan, gambar;

    LampuItems(JSONArray object, int posisi) {
        try {
            int id = object.getJSONObject(posisi).getInt("id");
            int status = object.getJSONObject(posisi).getInt("status");
            String gambar = object.getJSONObject(posisi).getString("gambar");
            String ruangan = object.getJSONObject(posisi).getString("ruang");

            this.id = id;
            this.ruangan = ruangan;
            this.gambar = gambar;
            this.status = status;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
