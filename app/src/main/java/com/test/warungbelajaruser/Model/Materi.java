package com.test.warungbelajaruser.Model;

public class Materi {
    private int pertemuan;
    private String materi;
    private String url_materi;
    private String status;
    private String nama_modul;

    public Materi(int pertemuan, String materi, String url_materi, String status, String nama_modul) {
        this.pertemuan = pertemuan;
        this.materi = materi;
        this.url_materi = url_materi;
        this.status = status;
        this.nama_modul = nama_modul;
    }

    public int getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(int pertemuan) {
        this.pertemuan = pertemuan;
    }

    public String getMateri() {
        return materi;
    }

    public void setMateri(String materi) {
        this.materi = materi;
    }

    public String getUrl_materi() {
        return url_materi;
    }

    public void setUrl_materi(String url_materi) {
        this.url_materi = url_materi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNama_modul() {
        return nama_modul;
    }

    public void setNama_modul(String nama_modul) {
        this.nama_modul = nama_modul;
    }
}
