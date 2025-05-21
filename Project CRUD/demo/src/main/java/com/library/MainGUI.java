package com.library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main GUI yang menampilkan tab buku dan peminjaman.
 */
public class MainGUI extends JFrame {
    private BukuGUI bukuGUI;
    private PeminjamanGUI peminjamanGUI;
    
    public MainGUI() {
        initComponents();
    }
    
    private void initComponents() {
        // Setting JFrame
        setTitle("Aplikasi Perpustakaan");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Membuat panel tab
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Inisialisasi panel buku dan peminjaman
        bukuGUI = new BukuGUI();
        peminjamanGUI = new PeminjamanGUI();
        
        // Membungkus BukuGUI dalam panel untuk mendukung tata letak
        JPanel bukuPanel = new JPanel(new BorderLayout());
        bukuPanel.add(bukuGUI, BorderLayout.CENTER);
        
        // Menambahkan tab
        tabbedPane.addTab("Manajemen Buku", bukuPanel);
        tabbedPane.addTab("Peminjaman Buku", peminjamanGUI);
        
        // Event listener untuk pergantian tab
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex == 1) {
                // Refresh data peminjaman saat tab dibuka
                peminjamanGUI.refreshData();
            }
        });
        
        // Menambahkan panel tab ke frame
        getContentPane().add(tabbedPane);
        
        // Tambahkan event listener untuk menutup koneksi DB
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Menutup koneksi database saat aplikasi ditutup
                DatabaseConnector.closeConnection();
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }
}
