package com.test.warungbelajaruser.Model;

public class Absensi {
    private int pertemuan;
    private String status;

    public Absensi(int pertemuan, String status) {
        this.pertemuan = pertemuan;
        this.status = status;
    }

    public int getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(int pertemuan) {
        this.pertemuan = pertemuan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
