package com.ldev.bimq.model;

public class users {

    String alamatEmail;
    String namaLengkap;

    public users(){}

    public users(String alamatEmail, String namaLengkap) {
        this.alamatEmail = alamatEmail;
        this.namaLengkap = namaLengkap;
    }

    public String getAlamatEmail() {
        return alamatEmail;
    }

    public void setAlamatEmail(String Email) {
        this.alamatEmail = Email;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String FullName) {
        this.namaLengkap = FullName;
    }



}
