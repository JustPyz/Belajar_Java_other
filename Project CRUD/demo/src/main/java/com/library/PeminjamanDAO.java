package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object untuk akses data peminjaman buku.
 */
public class PeminjamanDAO {
    private Connection connection;
    
    public PeminjamanDAO() {
        try {
            // Mendapatkan koneksi database
            connection = DatabaseConnector.getConnection();
            
            // Inisialisasi tabel jika belum ada
            initTable();
            
        } catch (SQLException e) {
            System.err.println("Error saat inisialisasi PeminjamanDAO: " + e.getMessage());
        }
    }
    
    /**
     * Inisialisasi tabel peminjaman jika belum ada.
     */
    private void initTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS peminjaman ("
                + "id_peminjaman INT AUTO_INCREMENT PRIMARY KEY,"
                + "id_buku INT NOT NULL,"
                + "nama_peminjam VARCHAR(100) NOT NULL,"
                + "tanggal_pinjam DATE NOT NULL,"
                + "tanggal_kembali DATE,"
                + "FOREIGN KEY (id_buku) REFERENCES buku(id_buku)"
                + ")";
                
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Tabel peminjaman berhasil diinisialisasi");
        } catch (SQLException e) {
            System.err.println("Error saat membuat tabel peminjaman: " + e.getMessage());
        }
    }
    
    /**
     * Mendapatkan semua data peminjaman dari database.
     * 
     * @return list peminjaman
     */
    public List<Peminjaman> getAllPeminjaman() {
        List<Peminjaman> peminjamanList = new ArrayList<>();
        
        String sql = "SELECT * FROM peminjaman";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int idPeminjaman = rs.getInt("id_peminjaman");
                int idBuku = rs.getInt("id_buku");
                String namaPeminjam = rs.getString("nama_peminjam");
                java.util.Date tanggalPinjam = new java.util.Date(rs.getDate("tanggal_pinjam").getTime());
                
                java.util.Date tanggalKembali = null;
                if (rs.getDate("tanggal_kembali") != null) {
                    tanggalKembali = new java.util.Date(rs.getDate("tanggal_kembali").getTime());
                }
                
                Peminjaman peminjaman = new Peminjaman(idPeminjaman, idBuku, namaPeminjam, tanggalPinjam, tanggalKembali);
                peminjamanList.add(peminjaman);
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data peminjaman: " + e.getMessage());
        }
        
        return peminjamanList;
    }
    
    /**
     * Menambahkan data peminjaman baru ke database.
     * 
     * @param peminjaman objek peminjaman yang akan ditambahkan
     * @return true jika berhasil, false jika gagal
     */
    public boolean insertPeminjaman(Peminjaman peminjaman) {
        String sql = "INSERT INTO peminjaman (id_buku, nama_peminjam, tanggal_pinjam, tanggal_kembali) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, peminjaman.getIdBuku());
            pstmt.setString(2, peminjaman.getNamaPeminjam());
            pstmt.setDate(3, new Date(peminjaman.getTanggalPinjam().getTime()));
            
            if (peminjaman.getTanggalKembali() != null) {
                pstmt.setDate(4, new Date(peminjaman.getTanggalKembali().getTime()));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                // Mendapatkan ID yang dibuat
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        peminjaman.setIdPeminjaman(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan peminjaman: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Mengupdate data peminjaman yang sudah ada.
     * 
     * @param peminjaman objek peminjaman yang akan diupdate
     * @return true jika berhasil, false jika gagal
     */
    public boolean updatePeminjaman(Peminjaman peminjaman) {
        String sql = "UPDATE peminjaman SET id_buku = ?, nama_peminjam = ?, tanggal_pinjam = ?, tanggal_kembali = ? WHERE id_peminjaman = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, peminjaman.getIdBuku());
            pstmt.setString(2, peminjaman.getNamaPeminjam());
            pstmt.setDate(3, new Date(peminjaman.getTanggalPinjam().getTime()));
            
            if (peminjaman.getTanggalKembali() != null) {
                pstmt.setDate(4, new Date(peminjaman.getTanggalKembali().getTime()));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }
            
            pstmt.setInt(5, peminjaman.getIdPeminjaman());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saat mengupdate peminjaman: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Menghapus data peminjaman berdasarkan ID.
     * 
     * @param idPeminjaman ID peminjaman yang akan dihapus
     * @return true jika berhasil, false jika gagal
     */
    public boolean deletePeminjaman(int idPeminjaman) {
        String sql = "DELETE FROM peminjaman WHERE id_peminjaman = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idPeminjaman);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saat menghapus peminjaman: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Mencari peminjaman berdasarkan nama peminjam.
     * 
     * @param nama nama peminjam yang akan dicari
     * @return list peminjaman yang cocok
     */
    public List<Peminjaman> searchPeminjamanByNama(String nama) {
        List<Peminjaman> peminjamanList = new ArrayList<>();
        
        String sql = "SELECT * FROM peminjaman WHERE nama_peminjam LIKE ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + nama + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int idPeminjaman = rs.getInt("id_peminjaman");
                    int idBuku = rs.getInt("id_buku");
                    String namaPeminjam = rs.getString("nama_peminjam");
                    java.util.Date tanggalPinjam = new java.util.Date(rs.getDate("tanggal_pinjam").getTime());
                    
                    java.util.Date tanggalKembali = null;
                    if (rs.getDate("tanggal_kembali") != null) {
                        tanggalKembali = new java.util.Date(rs.getDate("tanggal_kembali").getTime());
                    }
                    
                    Peminjaman peminjaman = new Peminjaman(idPeminjaman, idBuku, namaPeminjam, tanggalPinjam, tanggalKembali);
                    peminjamanList.add(peminjaman);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mencari peminjaman: " + e.getMessage());
        }
        
        return peminjamanList;
    }
    
    /**
     * Mencari peminjaman berdasarkan ID buku.
     * 
     * @param idBuku ID buku yang akan dicari
     * @return list peminjaman yang cocok
     */
    public List<Peminjaman> searchPeminjamanByIdBuku(int idBuku) {
        List<Peminjaman> peminjamanList = new ArrayList<>();
        
        String sql = "SELECT * FROM peminjaman WHERE id_buku = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idBuku);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int idPeminjaman = rs.getInt("id_peminjaman");
                    String namaPeminjam = rs.getString("nama_peminjam");
                    java.util.Date tanggalPinjam = new java.util.Date(rs.getDate("tanggal_pinjam").getTime());
                    
                    java.util.Date tanggalKembali = null;
                    if (rs.getDate("tanggal_kembali") != null) {
                        tanggalKembali = new java.util.Date(rs.getDate("tanggal_kembali").getTime());
                    }
                    
                    Peminjaman peminjaman = new Peminjaman(idPeminjaman, idBuku, namaPeminjam, tanggalPinjam, tanggalKembali);
                    peminjamanList.add(peminjaman);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mencari peminjaman: " + e.getMessage());
        }
        
        return peminjamanList;
    }
    
    /**
     * Menutup koneksi database
     */
    public void closeConnection() {
        // Tidak perlu menutup koneksi di sini karena koneksi dikelola oleh DatabaseConnector
    }
}
