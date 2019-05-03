package com.samuelbernard147.smarthomev2.soimah;

import org.json.JSONArray;

public class KartuItems {
    private String noKartu, nama, waktu;

    KartuItems(JSONArray object, int posisi) {
        try {
            int kartu = object.getJSONObject(posisi).getInt("no_kartu");
            String nama = object.getJSONObject(posisi).getString("nama");
            String waktu = object.getJSONObject(posisi).getString("created_at");
            String jam = waktu.substring(11);

            this.noKartu = Integer.toString(kartu);
            this.nama = nama;
            this.waktu = jam;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNoKartu() {
        return noKartu;
    }

    public void setNoKartu(String noKartu) {
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
