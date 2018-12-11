package com.test.warungbelajaruser.Model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Kursus {
    private String status;
    private String ruangan;
    private String mentor;
    private String paket;
    private String tanggal_batch;
    private int batch;
    private int harga;
    private int harga_dibayar;

    @Exclude
    private String UID;
    private String jenis_kursus;
    private Jadwal catatan_jadwal;
    private ArrayList<Absensi> catatan_absensi;
    private ArrayList<Materi> catatan_materi;

    public Kursus() {
    }

    public Kursus(String status, String ruangan, String mentor, String paket, String tanggal_batch, int batch, int harga, int harga_dibayar, String UID, String jenis_kursus, Jadwal catatan_jadwal, ArrayList<Absensi> catatan_absensi, ArrayList<Materi> catatan_materi) {
        this.status = status;
        this.ruangan = ruangan;
        this.mentor = mentor;
        this.paket = paket;
        this.tanggal_batch = tanggal_batch;
        this.batch = batch;
        this.harga = harga;
        this.harga_dibayar = harga_dibayar;
        this.UID = UID;
        this.jenis_kursus = jenis_kursus;
        this.catatan_jadwal = catatan_jadwal;
        this.catatan_absensi = catatan_absensi;
        this.catatan_materi = catatan_materi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public Jadwal getCatatan_jadwal() {
        return catatan_jadwal;
    }

    public void setCatatan_jadwal(Jadwal catatan_jadwal) {
        this.catatan_jadwal = catatan_jadwal;
    }

    public ArrayList<Absensi> getCatatan_absensi() {
        return catatan_absensi;
    }

    public void setCatatan_absensi(ArrayList<Absensi> catatan_absensi) {
        this.catatan_absensi = catatan_absensi;
    }

    public ArrayList<Materi> getCatatan_materi() {
        return catatan_materi;
    }

    public void setCatatan_materi(ArrayList<Materi> catatan_materi) {
        this.catatan_materi = catatan_materi;
    }

    public int getHarga_dibayar() {
        return harga_dibayar;
    }

    public void setHarga_dibayar(int harga_dibayar) {
        this.harga_dibayar = harga_dibayar;
    }

    public String getTanggal_batch() {
        return tanggal_batch;
    }

    public void setTanggal_batch(String tanggal_batch) {
        this.tanggal_batch = tanggal_batch;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public String getJenis_kursus() {
        return jenis_kursus;
    }

    public void setJenis_kursus(String jenis_kursus) {
        this.jenis_kursus = jenis_kursus;
    }
}
