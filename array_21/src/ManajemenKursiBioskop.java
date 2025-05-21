import java.util.Scanner;

public class ManajemenKursiBioskop {
    // Status kursi: false = tersedia, true = terpesan
    private boolean[][] kursi;
    private int jumlahBaris;
    private int jumlahKolom;
    private Scanner scanner;
    
    public ManajemenKursiBioskop(int baris, int kolom) {
        this.jumlahBaris = baris;
        this.jumlahKolom = kolom;
        this.kursi = new boolean[baris][kolom];
        this.scanner = new Scanner(System.in);
        
        // Inisialisasi semua kursi sebagai tersedia (false)
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                kursi[i][j] = false;
            }
        }
    }
    
    // Menampilkan denah kursi
    public void tampilkanDenah() {
        System.out.println("\n===== DENAH KURSI BIOSKOP =====");
        System.out.print("    ");
        
        // Tampilkan header nomor kolom
        for (int j = 0; j < jumlahKolom; j++) {
            System.out.printf("%3d ", j + 1);
        }
        System.out.println("\n");
        
        // Tampilkan denah kursi
        for (int i = 0; i < jumlahBaris; i++) {
            System.out.printf("%2d | ", i + 1);
            
            for (int j = 0; j < jumlahKolom; j++) {
                if (kursi[i][j]) {
                    System.out.print(" X  "); // Kursi terpesan
                } else {
                    System.out.print(" O  "); // Kursi tersedia
                }
            }
            System.out.println();
        }
        
        System.out.println("\nKeterangan: O = Tersedia, X = Terpesan");
    }
    
    // Memesan kursi
    public void pesanKursi() {
        tampilkanDenah();
        
        System.out.println("\n===== PEMESANAN KURSI =====");
        System.out.print("Masukkan baris kursi (1-" + jumlahBaris + "): ");
        int baris = scanner.nextInt() - 1; // Mengurangi 1 karena indeks array dimulai dari 0
        
        System.out.print("Masukkan kolom kursi (1-" + jumlahKolom + "): ");
        int kolom = scanner.nextInt() - 1; // Mengurangi 1 karena indeks array dimulai dari 0
        
        // Validasi input
        if (baris < 0 || baris >= jumlahBaris || kolom < 0 || kolom >= jumlahKolom) {
            System.out.println("Input tidak valid! Posisi kursi tidak tersedia.");
            return;
        }
        
        // Cek ketersediaan kursi
        if (kursi[baris][kolom]) {
            System.out.println("Maaf, kursi pada baris " + (baris + 1) + " kolom " + (kolom + 1) + " sudah terpesan.");
        } else {
            kursi[baris][kolom] = true;
            System.out.println("Kursi pada baris " + (baris + 1) + " kolom " + (kolom + 1) + " berhasil dipesan!");
        }
    }
    
    // Menghitung jumlah kursi yang masih tersedia
    public int hitungKursiTersedia() {
        int count = 0;
        
        for (int i = 0; i < jumlahBaris; i++) {
            for (int j = 0; j < jumlahKolom; j++) {
                if (!kursi[i][j]) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    // Menampilkan statistik pengisian kursi per baris
    public void statistikPengisian() {
        System.out.println("\n===== STATISTIK PENGISIAN KURSI =====");
        System.out.println("Baris\tTerpesan\tTersedia\tPersentase Terisi");
        
        for (int i = 0; i < jumlahBaris; i++) {
            int terpesan = 0;
            
            for (int j = 0; j < jumlahKolom; j++) {
                if (kursi[i][j]) {
                    terpesan++;
                }
            }
            
            int tersedia = jumlahKolom - terpesan;
            double persentase = (double) terpesan / jumlahKolom * 100;
            
            System.out.printf("%d\t%d\t\t%d\t\t%.2f%%\n", i + 1, terpesan, tersedia, persentase);
        }
        
        // Statistik keseluruhan
        int totalKursi = jumlahBaris * jumlahKolom;
        int totalTersedia = hitungKursiTersedia();
        int totalTerpesan = totalKursi - totalTersedia;
        double persentaseTotal = (double) totalTerpesan / totalKursi * 100;
        
        System.out.println("\nSTATISTIK TOTAL:");
        System.out.printf("Total kursi: %d\n", totalKursi);
        System.out.printf("Total terpesan: %d\n", totalTerpesan);
        System.out.printf("Total tersedia: %d\n", totalTersedia);
        System.out.printf("Persentase terisi: %.2f%%\n", persentaseTotal);
    }
    
    public void menu() {
        int pilihan;
        
        do {
            System.out.println("\n===== SISTEM MANAJEMEN KURSI BIOSKOP =====");
            System.out.println("1. Tampilkan Denah Kursi");
            System.out.println("2. Pesan Kursi");
            System.out.println("3. Lihat Statistik Pengisian");
            System.out.println("4. Keluar");
            System.out.print("Pilihan Anda: ");
            pilihan = scanner.nextInt();
            
            switch (pilihan) {
                case 1:
                    tampilkanDenah();
                    break;
                case 2:
                    pesanKursi();
                    break;
                case 3:
                    statistikPengisian();
                    break;
                case 4:
                    System.out.println("Terima kasih telah menggunakan sistem kami!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 4);
    }
    
    public static void main(String[] args) {
        Scanner inputAwal = new Scanner(System.in);
        
        System.out.println("INISIALISASI BIOSKOP");
        System.out.print("Masukkan jumlah baris: ");
        int baris = inputAwal.nextInt();
        
        System.out.print("Masukkan jumlah kolom: ");
        int kolom = inputAwal.nextInt();
        
        ManajemenKursiBioskop bioskop = new ManajemenKursiBioskop(baris, kolom);
        bioskop.menu();
        
        inputAwal.close();
    }
}