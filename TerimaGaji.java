import java.sql.*;
import com.mysql.cj.protocol.Resultset;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TerimaGaji extends Gaji
{
    Integer totalGaji, jmlAbsen; 
    static Connection conn;
	String link = "jdbc:mysql://localhost:3306/dabes_krywn";  
    Scanner input = new Scanner(System.in);

    @Override
    public void Potongan()
    {
        this.jmlAbsen = 30-jmlHadir;
        this.potongan = jmlAbsen*200000;
    }

    @Override
    public void TotalGaji()
    {
        this.totalGaji = this.gajiPokok - this.potongan;
    }

    @Override
    public void save() throws SQLException 
    {
        try 
        {
            System.out.println("Masukkan data karyawan");
            NoPegawai();
            NamaPegawai();
            Jabatan();
            GajiPokok();
            JumlahHariMasuk();
            Potongan();
            TotalGaji();

            String sql = "INSERT INTO karyawan (nama, nomor, jabatan, jmlHadir, totalGaji) VALUES ('"+nama+"','"+nomor+"','"+jabatan+"','"+jmlHadir+"','"+totalGaji+"')";
            conn = DriverManager.getConnection(link,"root","");
            Statement statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("Berhasil input data!!");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
           
            System.err.println("Error masukkan nomor jabatan dengan benar");
        }

        catch (IndexOutOfBoundsException e)
        {
            
            System.err.println("Masukkan rentang jumlah hadir 1-30");
        }

        catch (InputMismatchException e)
        {
            System.err.println("Input pilihan dengan angka saja");
        }

    }

    @Override
    public void delete() throws SQLException
    {
        view();
        try
        {
            System.out.println("Hapus data karyawan");
            System.out.print("Masukkan nomor pegawai yang akan dihapus : ");
            String noPegawai = input.nextLine();

            String sql = "DELETE FROM karyawan WHERE nomor = "+ noPegawai;

            conn = DriverManager.getConnection(link,"root","");
	        Statement statement = conn.createStatement();

            if(statement.executeUpdate(sql) > 0)
            {
	            System.out.println("Berhasil menghapus data pegawai (Nomor "+noPegawai+")");
	        }
        }

        catch(SQLException e)
        {
	        System.out.println("Terjadi kesalahan dalam menghapus data");
	    }
        catch(Exception e)
        {
            System.out.println("masukan data yang benar");
        }
    }

    @Override
    public void update() throws SQLException
    {
        view();
        try
        {
            System.out.print("Masukkan nomor pegawai hendak diubah: ");
            String noPegawai = input.nextLine();

            String sql = "SELECT * FROM karyawan WHERE nomor = " +noPegawai;
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next())
            {
                System.out.print("Nama baru ["+result.getString("nama")+"]\t: ");
                String namaPegawai = input.nextLine();

                sql = "UPDATE karyawan SET nama='"+namaPegawai+"' WHERE nomor='"+noPegawai+"'";

                if(statement.executeUpdate(sql) > 0)
                {
                    System.out.println("Berhasil memperbaharui data pegawai (Nomor "+noPegawai+")");
                }
            }
            statement.close();
        }

            catch (SQLException e) 
            {
                System.err.println("Terjadi kesalahan dalam mengedit data");
                System.err.println(e.getMessage());
            }

    }



    @Override
    public void search() throws SQLException
    {
        System.out.print("Masukkan Nama Pegawai yang ingin dilihat : ");    
		String keyword = input.nextLine();

		String sql = "SELECT * FROM karyawan WHERE nama LIKE '%"+keyword+"%'";
		conn = DriverManager.getConnection(link,"root","");
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql); 

        while (result.next())
        {
            System.out.print("\nNo pegawai\t\t: ");
            System.out.print(result.getString("nomor"));
            System.out.print("\nNama pegawai\t\t: ");
            System.out.print(result.getString("nama"));
            System.out.print("\nJabatan\t\t\t: ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nKehadiran\t\t: ");
            System.out.print(result.getInt("jmlHadir"));
            System.out.print("\nTotal gaji\t\t: ");
            System.out.println(result.getInt("totalGaji"));
        }

    }

    public void Tampilkan()
    {
        System.out.println("\nInformasi Penerimaan Gaji");
        System.out.println("Nama pegawai    : "+this.nama.toUpperCase()); //method toUpperCase()
        System.out.println("Karakter nama   : "+this.nama.length()+" karakter"); //method lenght()
        System.out.println("Nomor Pegawai   : "+this.nomor);
        System.out.print("Jabatan         : "+this.jabatan);
        //PrintJabatan();
        System.out.println("Jumlah kehadiran: "+this.jmlHadir+" hari");
        System.out.println("Potongan gaji   : Rp"+this.potongan);
        System.out.println("Total gaji      : Rp"+this.totalGaji+"\n");
    }
}