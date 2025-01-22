import java.util.Scanner;

public class datapasien {
    public static void main(String[] args) {
        
        int ID_Pasien;
        String Nama_Pasien, tanggal_kedatangan, alamat, keluhan;

        Scanner input = new Scanner(System.in);
        
        //Membuat judul dan input data
        System.out.println("DATA PASIEN");
        System.out.println("------------");

        System.out.print("ID Pasien : ");
        ID_Pasien = input.nextInt();

        System.out.print("Nama Pasien : ");
        Nama_Pasien = input.next();
        input.nextLine(); 

        System.out.print("tanggal kedatangan : ");
        tanggal_kedatangan = input.next();
        input.nextLine(); 
        
        System.out.print("Alamat : ");
        alamat = input.next();

        System.out.print("Keluhan : ");
        keluhan = input.next();
        //menampilkan data pasien

        System.out.println("DATA PASIEN");
        System.out.println("----------");
        System.out.println("ID Pasien :  " + ID_Pasien);
        System.out.println("Nama Pasien : " + Nama_Pasien);
        System.out.println("Tanggal Kedatangan : " + tanggal_kedatangan);
        System.out.println("Alamat : " + alamat);
        System.out.println("Keluhan : " + keluhan);



    }
}
