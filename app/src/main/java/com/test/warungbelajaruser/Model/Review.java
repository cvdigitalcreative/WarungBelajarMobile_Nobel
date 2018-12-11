package com.test.warungbelajaruser.Model;

public class Review {
    String pesan_nobel;
    String kesan_nobel;
    String review_mentor;
    int nilai_kepuasan;

    public Review() {
    }

    public Review(String pesan_nobel, String kesan_nobel, String review_mentor, int nilai_kepuasan) {
        this.pesan_nobel = pesan_nobel;
        this.kesan_nobel = kesan_nobel;
        this.review_mentor = review_mentor;
        this.nilai_kepuasan = nilai_kepuasan;
    }

    public String getPesan_nobel() {
        return pesan_nobel;
    }

    public void setPesan_nobel(String pesan_nobel) {
        this.pesan_nobel = pesan_nobel;
    }

    public String getKesan_nobel() {
        return kesan_nobel;
    }

    public void setKesan_nobel(String kesan_nobel) {
        this.kesan_nobel = kesan_nobel;
    }

    public String getReview_mentor() {
        return review_mentor;
    }

    public void setReview_mentor(String review_mentor) {
        this.review_mentor = review_mentor;
    }

    public int getNilai_kepuasan() {
        return nilai_kepuasan;
    }

    public void setNilai_kepuasan(int nilai_kepuasan) {
        this.nilai_kepuasan = nilai_kepuasan;
    }
}
