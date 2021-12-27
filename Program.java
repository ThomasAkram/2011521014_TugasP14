import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.*;
import com.mysql.cj.protocol.Resultset;

public class Program 
{
    static Connection conn;
    public static void main(String[] args) throws Exception 
    {

        DateFormat formatTanggal = new SimpleDateFormat("dd MMMM yyyy");
        DateFormat formatJam = new SimpleDateFormat("HH:mm:ss");
        Date tanggal = new Date();

        Scanner input = new Scanner(System.in);
        int pilihan;
        boolean balikMenu = true;
        String link = "jdbc:mysql://localhost:3306/dabes_krywn";

        String salamSapa = "Selamat Pagi, Tetap Semangat yaa dan selalu tersenyum :D";
        String sapa = salamSapa.replace("Selamat Pagi", "Hello"); //method replace()

        System.out.println(sapa.toLowerCase()); //method toLowerCase()
        System.out.println("Program menghitung gaji karyawan Perusahaan PTABC");

        while (balikMenu)
        {
            System.out.println("\n=====================");
			System.out.println("Database Pegawai");
			System.out.println("=====================");
			System.out.println("1.\tLihat Data Pegawai");
			System.out.println("2.\tTambah Data Pegawai");
			System.out.println("3.\tUbah Data Pegawai");
			System.out.println("4.\tHapus Data Pegawai");
			System.out.println("5.\tCari Data Pegawai");
            System.out.println("6.\tKeluar");
			System.out.print("\n(Pilih Angka): ");
			pilihan = input.nextInt();

            Gaji gaji = new Gaji();
            TerimaGaji terimaGaji = new TerimaGaji();
            switch (pilihan)
            {
                case 1:
                    gaji.view();
                break;

                case 2:
                    terimaGaji.save();
                break;

                case 3:
                    terimaGaji.update();
                break;

                case 4:
                    terimaGaji.delete();
                break;

                case 5:
                    terimaGaji.search();
                break;

                case 6:
                    balikMenu = false;
                break;

                default:
                    System.err.println("\nYang anda Inputkan tidak ditemukan\nSilakan pilih  Angka [1-6]");
                break;
            }
        }
                System.out.println("====================================");
                System.out.println("=Dibuat pada     : "+formatTanggal.format(tanggal)+"=");
                System.out.println("=Diupdate pada   : "+formatJam.format(tanggal)+" WIB    =");
                System.out.println("====================================");
                System.out.println("\nSelesai\n");

    }
}
