package com.test.warungbelajaruser.Model;

import java.util.ArrayList;

public class Nobel {
    private String nama;
    private String email;
    private String no_hp;

    public Nobel() {
    }

    public Nobel(String nama, String email, String no_hp) {
        this.nama = nama;
        this.email = email;
        this.no_hp = no_hp;
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
}
