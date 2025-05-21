package com.library;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BukuGUI extends JPanel {
    // Komponen GUI
    private JTable tableBuku;
    private DefaultTableModel tableModel;
    private JTextField txtIdBuku, txtJudul, txtKategori, txtPengarang, txtTahunTerbit, txtCari;
    private JButton btnSimpan, btnUpdate, btnHapus, btnClear, btnCari;
    
    // Data Access Object
    private BukuDAO bukuDAO;
    
    public BukuGUI() {
        bukuDAO = new BukuDAO();
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        // Setup panel dengan BorderLayout
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel form input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Buku"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // ID Buku
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("ID Buku:"), gbc);
        
        gbc.gridx = 1;
        txtIdBuku = new JTextField(20);
        txtIdBuku.setEditable(false);
        inputPanel.add(txtIdBuku, gbc);
        
        // Judul
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Judul:"), gbc);
        
        gbc.gridx = 1;
        txtJudul = new JTextField(20);
        inputPanel.add(txtJudul, gbc);
        
        // Kategori
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Kategori:"), gbc);
        
        gbc.gridx = 1;
        txtKategori = new JTextField(20);
        inputPanel.add(txtKategori, gbc);
        
        // Pengarang
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Pengarang:"), gbc);
        
        gbc.gridx = 1;
        txtPengarang = new JTextField(20);
        inputPanel.add(txtPengarang, gbc);
        
        // Tahun Terbit
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Tahun Terbit:"), gbc);
        
        gbc.gridx = 1;
        txtTahunTerbit = new JTextField(20);
        inputPanel.add(txtTahunTerbit, gbc);
        
        // Panel button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        btnSimpan = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnClear);
        
        // Panel pencarian
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari Judul:"));
        txtCari = new JTextField(20);
        btnCari = new JButton("Cari");
        searchPanel.add(txtCari);
        searchPanel.add(btnCari);
        
        // Panel tabel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Data Buku"));
        
        // Inisialisasi tabel
        String[] columnNames = {"ID Buku", "Judul", "Kategori", "Pengarang", "Tahun Terbit"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua sel tidak dapat diedit
            }
        };
        tableBuku = new JTable(tableModel);

        // Event handler untuk klik pada tabel
        tableBuku.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableBuku.getSelectedRow();
                if (row != -1) {
                    displaySelectedData(row);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableBuku);
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
        // Event handler untuk tombol Simpan
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanBuku();
            }
        });
        
        // Event handler untuk tombol Update
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBuku();
            }
        });
        
        // Event handler untuk tombol Hapus
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusBuku();
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
                cariBuku();
            }
        });
    }
    
    // Menampilkan data dari tabel ke form
    private void displaySelectedData(int row) {
        String id = tableModel.getValueAt(row, 0).toString();
        String judul = tableModel.getValueAt(row, 1).toString();
        String kategori = tableModel.getValueAt(row, 2).toString();
        String pengarang = tableModel.getValueAt(row, 3).toString();
        String tahunTerbit = tableModel.getValueAt(row, 4).toString();
        
        txtIdBuku.setText(id);
        txtJudul.setText(judul);
        txtKategori.setText(kategori);
        txtPengarang.setText(pengarang);
        txtTahunTerbit.setText(tahunTerbit);
    }
    
    // Memuat data dari database ke tabel
    private void loadData() {
        // Clear tabel
        tableModel.setRowCount(0);
        
        // Mendapatkan data buku dari database
        List<Buku> bukuList = bukuDAO.getAllBuku();
        
        // Menambahkan data ke tabel
        for (Buku buku : bukuList) {
            Object[] row = {
                buku.getIdBuku(),
                buku.getJudul(),
                buku.getKategori(),
                buku.getPengarang(),
                buku.getTahunTerbit()
            };
            tableModel.addRow(row);
        }
    }
    
    // Menyimpan buku baru ke database
    private void simpanBuku() {
        try {
            String judul = txtJudul.getText().trim();
            String kategori = txtKategori.getText().trim();
            String pengarang = txtPengarang.getText().trim();
            int tahunTerbit = Integer.parseInt(txtTahunTerbit.getText().trim());
            
            // Validasi input
            if (judul.isEmpty() || kategori.isEmpty() || pengarang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Buat objek Buku
            Buku buku = new Buku(judul, kategori, pengarang, tahunTerbit);
            
            // Simpan ke database
            boolean success = bukuDAO.insertBuku(buku);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Data buku berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data buku!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tahun terbit harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Mengupdate buku yang ada
    private void updateBuku() {
        try {
            // Pastikan id buku tidak kosong
            if (txtIdBuku.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih buku yang akan diupdate!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int idBuku = Integer.parseInt(txtIdBuku.getText().trim());
            String judul = txtJudul.getText().trim();
            String kategori = txtKategori.getText().trim();
            String pengarang = txtPengarang.getText().trim();
            int tahunTerbit = Integer.parseInt(txtTahunTerbit.getText().trim());
            
            // Validasi input
            if (judul.isEmpty() || kategori.isEmpty() || pengarang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Buat objek Buku
            Buku buku = new Buku(idBuku, judul, kategori, pengarang, tahunTerbit);
            
            // Update ke database
            boolean success = bukuDAO.updateBuku(buku);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Data buku berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate data buku!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tahun terbit harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Menghapus buku dari database
    private void hapusBuku() {
        // Pastikan id buku tidak kosong
        if (txtIdBuku.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int idBuku = Integer.parseInt(txtIdBuku.getText().trim());
        
        // Konfirmasi penghapusan
        int option = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus buku ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            // Hapus dari database
            boolean success = bukuDAO.deleteBuku(idBuku);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Data buku berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data buku!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Mencari buku berdasarkan judul
    private void cariBuku() {
        String keyword = txtCari.getText().trim();
        
        if (keyword.isEmpty()) {
            loadData(); // Jika keyword kosong, tampilkan semua data
            return;
        }
        
        // Clear tabel
        tableModel.setRowCount(0);
        
        // Mendapatkan data buku dari database berdasarkan judul
        List<Buku> bukuList = bukuDAO.searchBukuByJudul(keyword);
        
        // Menambahkan data ke tabel
        for (Buku buku : bukuList) {
            Object[] row = {
                buku.getIdBuku(),
                buku.getJudul(),
                buku.getKategori(),
                buku.getPengarang(),
                buku.getTahunTerbit()
            };
            tableModel.addRow(row);
        }
        
        if (bukuList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada buku yang ditemukan dengan judul: " + keyword, "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Clear form input
    private void clearForm() {
        txtIdBuku.setText("");
        txtJudul.setText("");
        txtKategori.setText("");
        txtPengarang.setText("");
        txtTahunTerbit.setText("");
        txtCari.setText("");
        tableBuku.clearSelection();
    }
    
    // Refresh data (dipanggil dari luar)
    public void refreshData() {
        loadData();
    }
}