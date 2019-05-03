package com.samuelbernard147.soimah;

import org.json.JSONArray;

public class KartuItems {
    private int noKartu;
    private String nama, waktu;

    KartuItems(JSONArray object, int posisi) {
        try {
//            int kartu = object.getJSONObject(posisi).getInt("no_kartu");
//            String nama = object.getJSONObject(posisi).getString("nama");
            String waktu = object.getJSONObject(posisi).getString("created_at");
//            this.noKartu = kartu;
            this.nama = waktu;
            this.waktu = waktu;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getNoKartu() {
        return noKartu;
    }

    public void setNoKartu(int noKartu) {
        this.noKartu = noKartu;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
