package com.library;

public class Buku { 
    private int idBuku;
    private String judul;
    private String kategori;
    private String pengarang;
    private int tahunTerbit;
    
    // Konstruktor defaultbagia
    public Buku() {
    }
    
    // Konstruktor dengan parameter
    public Buku(int idBuku, String judul, String kategori, String pengarang, int tahunTerbit) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.kategori = kategori;
        this.pengarang = pengarang;
        this.tahunTerbit = tahunTerbit;
    }
    
    // Konstruktor tanpa id untuk insert baru
    public Buku(String judul, String kategori, String pengarang, int tahunTerbit) {
        this.judul = judul;
        this.kategori = kategori;
        this.pengarang = pengarang;
        this.tahunTerbit = tahunTerbit;
    }
    
    // Getter dan setter
    public int getIdBuku() {
        return idBuku;
    }
    
    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }
    
    public String getJudul() {
        return judul;
    }
    
    public void setJudul(String judul) {
        this.judul = judul;
    }
    
    public String getKategori() {
        return kategori;
    }
    
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    
    public String getPengarang() {
        return pengarang;
    }
    
    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }
    
    public int getTahunTerbit() {
        return tahunTerbit;
    }
    
    public void setTahunTerbit(int tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }
    
    @Override
    public String toString() {
        return judul;
    }
} 