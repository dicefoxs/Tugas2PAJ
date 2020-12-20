import java.sql.*;
import java.util.Scanner;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Main {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/db_admin";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    static void create(){
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("masukan Username      : ");
            String username= input.next();
            System.out.println("masukan Password      : ");
            String password= getMd5(input.next());
            System.out.println("masukan nama          : ");
            String nama= input.next();
            System.out.println("masukan Umur          : ");
            int umur= input.nextInt();
            System.out.println("masukan Jenis kelamin : ");
            String jk= input.next();
            System.out.println("masukan No_telp       : ");
            String notlp= input.next();
            System.out.println("masukan Email         : ");
            String email= input.next();

            String sql = "INSERT INTO admin (Username, Password, Nama, Umur, Jenis_Kelamin, No_Telp, Email) VALUE ('%s','%s','%s','%d','%s','%s','%s','%s')";
            sql= String.format(sql,username,password,nama,umur,jk,notlp,email);

            stmt.execute(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void read(){
        String sql = "SELECT * FROM admin";
        try{
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                int id_siswa = rs.getInt("ID");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String nama = rs.getString("Nama");
                int umur = rs.getInt("Umur");
                String jk = rs.getString("Jenis_kelamin");
                String notelp = rs.getString("No_Telp");
                String email = rs.getString("Email");

                System.out.println(String.format("%d. Username : %s, | Password : %s, | Nama : %s, | Umur : %d, | Jenis Kelamin : %s, | No. Telp : %s, | Email : %s",id_siswa,username,password,nama,umur,jk,notelp,email));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void update(){
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("masukan id yang ingin di update :");
            int id = input.nextInt();
            System.out.println("masukan nama  baru  :");
            String nama=input.next();
            System.out.println("masukan email baru :");
            String email=input.next();

            String sql="UPDATE siswa SET Nama='%s', Email='%s' WHERE ID=%d";
            sql = String.format(sql, nama,email,id);

            stmt.execute(sql);
            System.out.println("data telah di update");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void delete(){
        try{
            Scanner input= new Scanner(System.in);

            System.out.println("masukan id yang ingin dihapus :");
            int id=input.nextInt();

            String sql = String.format("DELETE FROM siswa WHERE ID = %d",id);
            stmt.execute(sql);

            System.out.println("data telah dihapus");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Scanner input= new Scanner(System.in);
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER,PASS);
            stmt=conn.createStatement();

            while(!conn.isClosed()){
                System.out.println("1. Create" +
                        "\n2. Read" +
                        "\n3. Update" +
                        "\n4. Delete" +
                        "\n5. exit");
                System.out.println("masukan pilihan :");
                int pilih = input.nextInt();

                if(pilih==1){
                    create();
                }else if(pilih==2){
                    read();
                }else if(pilih==3){
                    update();
                }else if(pilih==4){
                    delete();
                }else{
                    System.exit(0);
                }
            }

            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
