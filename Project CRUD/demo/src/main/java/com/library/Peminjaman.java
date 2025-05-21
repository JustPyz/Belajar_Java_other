package com.library;

import java.util.Date;

/**
 * Entity class untuk representasi data peminjaman buku.
 */
public class Peminjaman {
    private int idPeminjaman;
    private int idBuku;
    private String namaPeminjam;
    private Date tanggalPinjam;
    private Date tanggalKembali;
    
    /**
     * Constructor untuk membuat objek peminjaman baru (tanpa ID)
     */
    public Peminjaman(int idBuku, String namaPeminjam, Date tanggalPinjam, Date tanggalKembali) {
        this.idBuku = idBuku;
        this.namaPeminjam = namaPeminjam;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
    }
    
    /**
     * Constructor untuk membuat objek peminjaman dengan ID yang sudah ada
     */
    public Peminjaman(int idPeminjaman, int idBuku, String namaPeminjam, Date tanggalPinjam, Date tanggalKembali) {
        this.idPeminjaman = idPeminjaman;
        this.idBuku = idBuku;
        this.namaPeminjam = namaPeminjam;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
    }
    
    // Getter and Setter
    public int getIdPeminjaman() {
        return idPeminjaman;
    }
    
    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }
    
    public int getIdBuku() {
        return idBuku;
    }
    
    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }
    
    public String getNamaPeminjam() {
        return namaPeminjam;
    }
    
    public void setNamaPeminjam(String namaPeminjam) {
        this.namaPeminjam = namaPeminjam;
    }
    
    public Date getTanggalPinjam() {
        return tanggalPinjam;
    }
    
    public void setTanggalPinjam(Date tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }
    
    public Date getTanggalKembali() {
        return tanggalKembali;
    }
    
    public void setTanggalKembali(Date tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }
    
    @Override
    public String toString() {
        return "Peminjaman [idPeminjaman=" + idPeminjaman + 
               ", idBuku=" + idBuku + 
               ", namaPeminjam=" + namaPeminjam + 
               ", tanggalPinjam=" + tanggalPinjam + 
               ", tanggalKembali=" + tanggalKembali + "]";
    }
}
