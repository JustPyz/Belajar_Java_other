package com.library;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PeminjamanGUI extends JPanel {
    // Komponen GUI
    private JTable tablePeminjaman;
    private DefaultTableModel tableModel;
    private JTextField txtIdPeminjaman, txtIdBuku, txtNamaPeminjam, txtTanggalPinjam, txtTanggalKembali, txtCari;
    private JButton btnSimpan, btnUpdate, btnHapus, btnClear, btnCari;
    private JComboBox<String> cbJudulBuku;  // Untuk memilih buku berdasarkan judul
    
    // Format tanggal
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    // Data Access Object
    private PeminjamanDAO peminjamanDAO;
    private BukuDAO bukuDAO;  // Untuk mendapatkan data buku
    
    public PeminjamanGUI() {
        peminjamanDAO = new PeminjamanDAO();
        bukuDAO = new BukuDAO();
        initComponents();
        loadData();
        loadBukuData();  // Load data buku untuk combobox
    }
    
    private void initComponents() {
        // Setup panel utama dengan layout BorderLayout
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel form input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Peminjaman"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // ID Peminjaman
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("ID Peminjaman:"), gbc);
        
        gbc.gridx = 1;
        txtIdPeminjaman = new JTextField(20);
        txtIdPeminjaman.setEditable(false);  // ID auto-generated
        inputPanel.add(txtIdPeminjaman, gbc);
        
        // ID Buku & Combo Judul Buku
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Buku:"), gbc);
        
        JPanel bukuPanel = new JPanel(new BorderLayout(5, 0));
        txtIdBuku = new JTextField(5);
        cbJudulBuku = new JComboBox<>();
        
        bukuPanel.add(txtIdBuku, BorderLayout.WEST);
        bukuPanel.add(cbJudulBuku, BorderLayout.CENTER);
        
        gbc.gridx = 1;
        inputPanel.add(bukuPanel, gbc);
        
        // Nama Peminjam
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Nama Peminjam:"), gbc);
        
        gbc.gridx = 1;
        txtNamaPeminjam = new JTextField(20);
        inputPanel.add(txtNamaPeminjam, gbc);
        
        // Tanggal Pinjam
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Tanggal Pinjam (yyyy-MM-dd):"), gbc);
        
        gbc.gridx = 1;
        txtTanggalPinjam = new JTextField(20);
        // Default tanggal hari ini
        txtTanggalPinjam.setText(dateFormat.format(new Date()));
        inputPanel.add(txtTanggalPinjam, gbc);
        
        // Tanggal Kembali
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Tanggal Kembali (yyyy-MM-dd):"), gbc);
        
        gbc.gridx = 1;
        txtTanggalKembali = new JTextField(20);
        inputPanel.add(txtTanggalKembali, gbc);
        
        // Panel button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        btnSimpan = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("kembalikan");
        btnClear = new JButton("Clear");
        
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnClear);
        
        // Panel pencarian
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari Nama Peminjam:"));
        txtCari = new JTextField(20);
        btnCari = new JButton("Cari");
        searchPanel.add(txtCari);
        searchPanel.add(btnCari);
        
        // Panel tabel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Data Peminjaman"));
        
        // Inisialisasi tabel
        String[] columnNames = {"ID Peminjaman", "ID Buku", "Judul Buku", "Nama Peminjam", "Tanggal Pinjam", "Tanggal Kembali"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Sel tidak dapat diedit
            }
        };
        tablePeminjaman = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(tablePeminjaman);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Menambahkan komponen ke panel utama
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(searchPanel, BorderLayout.NORTH);
        bottomPanel.add(tablePanel, BorderLayout.CENTER);
        
        add(bottomPanel, BorderLayout.CENTER);
        
        // Register event handler
        registerEventHandler();
    }
    
    private void registerEventHandler() {
        // Event handler untuk ComboBox Judul Buku
        cbJudulBuku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbJudulBuku.getSelectedIndex() > 0) {
                    String selectedItem = (String) cbJudulBuku.getSelectedItem();
                    // Format: "ID - Judul"
                    String idStr = selectedItem.split(" - ")[0];
                    txtIdBuku.setText(idStr);
                }
            }
        });
        
        // Event handler untuk tombol Simpan
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanPeminjaman();
            }
        });
        
        // Event handler untuk tombol Update
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePeminjaman();
            }
        });
        
        // Event handler untuk tombol Hapus
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusPeminjaman();
            }
        });
        
        // Event handler untuk tombol Clear
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        
        // Event handler untuk tombol Cari
        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cariPeminjaman();
            }
        });
        
        // Event handler untuk klik pada tabel
        tablePeminjaman.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablePeminjaman.getSelectedRow();
                if (row != -1) {
                    displaySelectedData(row);
                }
            }
        });
    }
    
    // Memuat data buku untuk ComboBox
    private void loadBukuData() {
        cbJudulBuku.removeAllItems();
        cbJudulBuku.addItem("-- Pilih Buku --");
        
        List<Buku> bukuList = bukuDAO.getAllBuku();
        for (Buku buku : bukuList) {
            cbJudulBuku.addItem(buku.getIdBuku() + " - " + buku.getJudul());
        }
    }
    
    // Menampilkan data dari tabel ke form
    private void displaySelectedData(int row) {
        String idPeminjaman = tableModel.getValueAt(row, 0).toString();
        String idBuku = tableModel.getValueAt(row, 1).toString();
        String namaPeminjam = tableModel.getValueAt(row, 3).toString();
        String tanggalPinjam = tableModel.getValueAt(row, 4).toString();
        
        txtIdPeminjaman.setText(idPeminjaman);
        txtIdBuku.setText(idBuku);
        txtNamaPeminjam.setText(namaPeminjam);
        txtTanggalPinjam.setText(tanggalPinjam);
        
        // Tanggal kembali mungkin null
        Object tanggalKembaliObj = tableModel.getValueAt(row, 5);
        if (tanggalKembaliObj != null && !tanggalKembaliObj.toString().isEmpty()) {
            txtTanggalKembali.setText(tanggalKembaliObj.toString());
        } else {
            txtTanggalKembali.setText("");
        }
        
        // Set combo box jika ada
        for (int i = 0; i < cbJudulBuku.getItemCount(); i++) {
            String item = cbJudulBuku.getItemAt(i);
            if (item != null && item.startsWith(idBuku + " - ")) {
                cbJudulBuku.setSelectedIndex(i);
                break;
            }
        }
    }
    
    // Memuat data dari database ke tabel
    private void loadData() {
        // Clear tabel
        tableModel.setRowCount(0);
        
        // Mendapatkan data peminjaman dari database
        List<Peminjaman> peminjamanList = peminjamanDAO.getAllPeminjaman();
        
        // Menambahkan data ke tabel
        for (Peminjaman peminjaman : peminjamanList) {
            // Dapatkan judul buku
            String judulBuku = "";
            List<Buku> bukuList = bukuDAO.searchBukuById(peminjaman.getIdBuku());
            if (!bukuList.isEmpty()) {
                judulBuku = bukuList.get(0).getJudul();
            }
            
            Object[] row = {
                peminjaman.getIdPeminjaman(),
                peminjaman.getIdBuku(),
                judulBuku,
                peminjaman.getNamaPeminjam(),
                dateFormat.format(peminjaman.getTanggalPinjam()),
                peminjaman.getTanggalKembali() != null ? dateFormat.format(peminjaman.getTanggalKembali()) : ""
            };
            tableModel.addRow(row);
        }
    }
    
    // Menyimpan peminjaman baru ke database
    private void simpanPeminjaman() {
        try {
            // Validasi ID Buku
            if (txtIdBuku.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Silakan pilih buku terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int idBuku = Integer.parseInt(txtIdBuku.getText().trim());
            String namaPeminjam = txtNamaPeminjam.getText().trim();
            Date tanggalPinjam = dateFormat.parse(txtTanggalPinjam.getText().trim());
            
            // Tanggal kembali bisa null
            Date tanggalKembali = null;
            if (!txtTanggalKembali.getText().trim().isEmpty()) {
                tanggalKembali = dateFormat.parse(txtTanggalKembali.getText().trim());
            }
            
            // Validasi input
            if (namaPeminjam.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama peminjam harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Buat objek Peminjaman
            Peminjaman peminjaman = new Peminjaman(idBuku, namaPeminjam, tanggalPinjam, tanggalKembali);
            
            // Simpan ke database
            boolean success = peminjamanDAO.insertPeminjaman(peminjaman);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Data peminjaman berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data peminjaman!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID Buku harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan format yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Mengupdate peminjaman yang ada
    private void updatePeminjaman() {
        try {
            // Pastikan id peminjaman tidak kosong
            if (txtIdPeminjaman.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih peminjaman yang akan diupdate!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int idPeminjaman = Integer.parseInt(txtIdPeminjaman.getText().trim());
            int idBuku = Integer.parseInt(txtIdBuku.getText().trim());
            String namaPeminjam = txtNamaPeminjam.getText().trim();
            Date tanggalPinjam = dateFormat.parse(txtTanggalPinjam.getText().trim());
            
            // Tanggal kembali bisa null
            Date tanggalKembali = null;
            if (!txtTanggalKembali.getText().trim().isEmpty()) {
                tanggalKembali = dateFormat.parse(txtTanggalKembali.getText().trim());
            }
            
            // Validasi input
            if (namaPeminjam.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama peminjam harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Buat objek Peminjaman
            Peminjaman peminjaman = new Peminjaman(idPeminjaman, idBuku, namaPeminjam, tanggalPinjam, tanggalKembali);
            
            // Update ke database
            boolean success = peminjamanDAO.updatePeminjaman(peminjaman);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Data peminjaman berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate data peminjaman!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan format yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Menghapus peminjaman dari database
    private void hapusPeminjaman() {
        // Pastikan id peminjaman tidak kosong
        if (txtIdPeminjaman.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih peminjaman yang akan dikembalikan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int idPeminjaman = Integer.parseInt(txtIdPeminjaman.getText().trim());
        
        // Konfirmasi penghapusan
        int option = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin mengembalikan data peminjaman ini?", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            // Hapus dari database
            boolean success = peminjamanDAO.deletePeminjaman(idPeminjaman);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Data peminjaman berhasil diterima!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data peminjaman!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Mencari peminjaman berdasarkan nama peminjam
    private void cariPeminjaman() {
        String keyword = txtCari.getText().trim();
        
        if (keyword.isEmpty()) {
            loadData();  // Jika keyword kosong, tampilkan semua data
            return;
        }
        
        // Clear tabel
        tableModel.setRowCount(0);
        
        // Mendapatkan data peminjaman dari database berdasarkan nama
        List<Peminjaman> peminjamanList = peminjamanDAO.searchPeminjamanByNama(keyword);
        
        // Menambahkan data ke tabel
        for (Peminjaman peminjaman : peminjamanList) {
            // Dapatkan judul buku
            String judulBuku = "";
            List<Buku> bukuList = bukuDAO.searchBukuById(peminjaman.getIdBuku());
            if (!bukuList.isEmpty()) {
                judulBuku = bukuList.get(0).getJudul();
            }
            
            Object[] row = {
                peminjaman.getIdPeminjaman(),
                peminjaman.getIdBuku(),
                judulBuku,
                peminjaman.getNamaPeminjam(),
                dateFormat.format(peminjaman.getTanggalPinjam()),
                peminjaman.getTanggalKembali() != null ? dateFormat.format(peminjaman.getTanggalKembali()) : ""
            };
            tableModel.addRow(row);
        }
        
        if (peminjamanList.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Tidak ada peminjaman yang ditemukan dengan nama: " + keyword, 
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Clear form input
    private void clearForm() {
        txtIdPeminjaman.setText("");
        txtIdBuku.setText("");
        txtNamaPeminjam.setText("");
        txtTanggalPinjam.setText(dateFormat.format(new Date()));  // Reset ke tanggal hari ini
        txtTanggalKembali.setText("");
        cbJudulBuku.setSelectedIndex(0);
        txtCari.setText("");
        tablePeminjaman.clearSelection();
    }
    
    // Method untuk memperbarui data (dipanggil oleh panel lain)
    public void refreshData() {
        loadData();
        loadBukuData();
    }
}
