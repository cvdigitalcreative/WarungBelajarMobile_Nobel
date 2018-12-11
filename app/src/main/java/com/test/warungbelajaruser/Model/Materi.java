package com.test.warungbelajaruser.Model;

public class Materi {
    private int pertemuan;
    private String materi;
    private String url_materi;
    private String status;

    public Materi(int pertemuan, String materi, String url_materi, String status) {
        this.pertemuan = pertemuan;
        this.materi = materi;
        this.url_materi = url_materi;
        this.status = status;
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
}
