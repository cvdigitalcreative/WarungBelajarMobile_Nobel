package com.test.warungbelajaruser.Model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Nobel {
    private String nama;
    private String email;
    private String no_hp;
    private String qr_url;
    private String qr_file;

    @Exclude
    private Kursus kursus;
    private String UID;

    public Nobel() {
    }

    public Nobel(String nama, String email, String no_hp, String qr_url, String qr_file) {
        this.nama = nama;
        this.email = email;
        this.no_hp = no_hp;
        this.qr_url = qr_url;
        this.qr_file = qr_file;
    }

    public Nobel(String nama, String email, String no_hp, String qr_url, String qr_file, Kursus kursus, String UID) {
        this.nama = nama;
        this.email = email;
        this.no_hp = no_hp;
        this.qr_url = qr_url;
        this.qr_file = qr_file;
        this.kursus = kursus;
        this.UID = UID;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public Kursus getKursus() {
        return kursus;
    }

    public void setKursus(Kursus kursus) {
        this.kursus = kursus;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getQr_url() {
        return qr_url;
    }

    public void setQr_url(String qr_url) {
        this.qr_url = qr_url;
    }

    public String getQr_file() {
        return qr_file;
    }

    public void setQr_file(String qr_file) {
        this.qr_file = qr_file;
    }
}
