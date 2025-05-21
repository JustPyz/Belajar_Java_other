package com.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Kelas untuk mengelola koneksi ke database perpustakaan.
 */
public class DatabaseConnector {
    // Konstanta untuk konfigurasi database
    private static final String DB_NAME = "perpustakaan";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String DB_USER = "root";       
    private static final String DB_PASSWORD = "....";       
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection connection = null;
    
    /**
     * Membuat koneksi ke database.
     * 
     * @return objek Connection jika berhasil
     * @throws SQLException jika gagal membuat koneksi
     */
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    // Mendaftarkan driver JDBC
                    Class.forName(DB_DRIVER);
                    
                    // Membuat koneksi
                    connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    System.out.println("Koneksi ke database " + DB_NAME + " berhasil!");
                } catch (ClassNotFoundException e) {
                    System.err.println("Driver JDBC tidak ditemukan: " + e.getMessage());
                    throw new SQLException("Driver JDBC tidak ditemukan", e);
                } catch (SQLException e) {
                    System.err.println("Gagal terhubung ke database: " + e.getMessage());
                    throw e;
                }
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("Error saat memeriksa status koneksi: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Menutup koneksi database.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Koneksi database ditutup.");
                }
            } catch (SQLException e) {
                System.err.println("Error saat menutup koneksi: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
    
    /**
     * Metode pengujian sederhana untuk memeriksa koneksi.
     */
    public static void testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Test koneksi berhasil!");
                System.out.println("Database: " + conn.getCatalog());
                closeConnection();
            }
        } catch (SQLException e) {
            System.err.println("Test koneksi gagal: " + e.getMessage());
        }
    }
    
    /**
     * Metode utama untuk menguji koneksi database.
     */
    public static void main(String[] args) {
        testConnection();
    }
}