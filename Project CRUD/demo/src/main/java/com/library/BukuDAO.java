package com.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BukuDAO {
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/perpustakaan";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "....";
    
    // Konstruktor
    public BukuDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Koneksi ke database berhasil!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Koneksi ke database gagal: " + e.getMessage());
        }
    }
    
    // Metode untuk menambah buku
    public boolean insertBuku(Buku buku) {
        String query = "INSERT INTO buku (judul, kategori, pengarang, tahun_terbit) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getKategori());
            ps.setString(3, buku.getPengarang());
            ps.setInt(4, buku.getTahunTerbit());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error saat menambah buku: " + e.getMessage());
            return false;
        }
    }
    
    // Metode untuk mengupdate buku
    public boolean updateBuku(Buku buku) {
        String query = "UPDATE buku SET judul=?, kategori=?, pengarang=?, tahun_terbit=? WHERE id_buku=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getKategori());
            ps.setString(3, buku.getPengarang());
            ps.setInt(4, buku.getTahunTerbit());
            ps.setInt(5, buku.getIdBuku());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error saat mengupdate buku: " + e.getMessage());
            return false;
        }
    }
    
    // Metode untuk menghapus buku
    public boolean deleteBuku(int id) {
        String query = "DELETE FROM buku WHERE id_buku=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error saat menghapus buku: " + e.getMessage());
            return false;
        }
    }
    
    // Metode untuk mendapatkan semua buku
    public List<Buku> getAllBuku() {
        List<Buku> bukuList = new ArrayList<>();
        String query = "SELECT * FROM buku";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("id_buku"));
                buku.setJudul(rs.getString("judul"));
                buku.setKategori(rs.getString("kategori"));
                buku.setPengarang(rs.getString("pengarang"));
                buku.setTahunTerbit(rs.getInt("tahun_terbit"));
                
                bukuList.add(buku);
            }
        } catch (SQLException e) {
            System.out.println("Error saat mengambil data buku: " + e.getMessage());
        }
        
        return bukuList;
    }
    
    // Metode untuk mendapatkan buku berdasarkan ID
    public Buku getBukuById(int id) {
        String query = "SELECT * FROM buku WHERE id_buku=?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Buku buku = new Buku();
                    buku.setIdBuku(rs.getInt("id_buku"));
                    buku.setJudul(rs.getString("judul"));
                    buku.setKategori(rs.getString("kategori"));
                    buku.setPengarang(rs.getString("pengarang"));
                    buku.setTahunTerbit(rs.getInt("tahun_terbit"));
                    
                    return buku;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error saat mengambil data buku: " + e.getMessage());
        }
        
        return null;
    }
    
    // Metode pencarian buku berdasarkan judul
    public List<Buku> searchBukuByJudul(String keyword) {
        List<Buku> bukuList = new ArrayList<>();
        String query = "SELECT * FROM buku WHERE judul LIKE ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + keyword + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Buku buku = new Buku();
                    buku.setIdBuku(rs.getInt("id_buku"));
                    buku.setJudul(rs.getString("judul"));
                    buku.setKategori(rs.getString("kategori"));
                    buku.setPengarang(rs.getString("pengarang"));
                    buku.setTahunTerbit(rs.getInt("tahun_terbit"));
                    
                    bukuList.add(buku);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error saat mencari buku: " + e.getMessage());
        }
        
        return bukuList;
    }
    
    /**
     * Mencari buku berdasarkan ID buku.
     * 
     * @param idBuku ID buku yang akan dicari
     * @return list buku yang cocok (biasanya hanya berisi satu data)
     */
    public List<Buku> searchBukuById(int idBuku) {
        List<Buku> bukuList = new ArrayList<>();
        
        String sql = "SELECT * FROM buku WHERE id_buku = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idBuku);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_buku");
                    String judul = rs.getString("judul");
                    String kategori = rs.getString("kategori");
                    String pengarang = rs.getString("pengarang");
                    int tahunTerbit = rs.getInt("tahun_terbit");
                    
                    Buku buku = new Buku(id, judul, kategori, pengarang, tahunTerbit);
                    bukuList.add(buku);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mencari buku by ID: " + e.getMessage());
        }
        
        return bukuList;
    }
    
    // Metode untuk menutup koneksi
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Koneksi ditutup.");
            }
        } catch (SQLException e) {
            System.out.println("Error saat menutup koneksi: " + e.getMessage());
        }
    }
}