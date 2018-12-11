package com.test.warungbelajaruser.Model;

public class Jadwal {
    private String hari1;
    private String hari2;
    private String sesi_pertama;
    private String sesi_kedua;
    private String jam_pertama;
    private String jam_kedua;

    public Jadwal() {
    }

    public Jadwal(String hari1, String hari2, String sesi_pertama, String sesi_kedua, String jam_pertama, String jam_kedua) {
        this.hari1 = hari1;
        this.hari2 = hari2;
        this.sesi_pertama = sesi_pertama;
        this.sesi_kedua = sesi_kedua;
        this.jam_pertama = jam_pertama;
        this.jam_kedua = jam_kedua;
    }

    public String getHari1() {
        return hari1;
    }

    public void setHari1(String hari1) {
        this.hari1 = hari1;
    }

    public String getHari2() {
        return hari2;
    }

    public void setHari2(String hari2) {
        this.hari2 = hari2;
    }

    public String getSesi_pertama() {
        return sesi_pertama;
    }

    public void setSesi_pertama(String sesi_pertama) {
        this.sesi_pertama = sesi_pertama;
    }

    public String getSesi_kedua() {
        return sesi_kedua;
    }

    public void setSesi_kedua(String sesi_kedua) {
        this.sesi_kedua = sesi_kedua;
    }

    public String getJam_pertama() {
        return jam_pertama;
    }

    public void setJam_pertama(String jam_pertama) {
        this.jam_pertama = jam_pertama;
    }

    public String getJam_kedua() {
        return jam_kedua;
    }

    public void setJam_kedua(String jam_kedua) {
        this.jam_kedua = jam_kedua;
    }
}
