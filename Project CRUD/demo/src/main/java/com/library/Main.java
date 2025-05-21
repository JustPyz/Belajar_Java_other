package com.library;

public class Main {
    public static void main(String[] args) {
        // Menjalankan Main GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }
}