package BankManagementSystem;
import java.util.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class User {


    private  Scanner scanner;
    private  Connection connection;

    public User(  Connection conn, Scanner scanner){
        this.connection=conn;
        this.scanner=scanner;


    }


    public void register() throws SQLException{
        scanner.nextLine();
        System.out.print("Full Name: ");
        String full_name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
       String encrypt_Password= encrypt_password(password);
        if(!is_exist(email))
        {
            String query="insert into users(full_name ,email,password) values (?,?,?)";
            try {
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1,full_name);
                pst.setString(2,email);
                pst.setString(3,encrypt_Password);
                int roweffected=pst.executeUpdate();
                if(roweffected>0){
                    System.out.println("registered successfully.....");
                }else{
                    System.out.println("failed to register");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
        else {
            System.out.println("user already exist....");
        }


    }



    public boolean is_exist(String email){
        String query="select * from users where email=?";

        try {
            PreparedStatement pst= connection.prepareStatement(query);
            pst.setString(1,email);
            ResultSet result=pst.executeQuery();
            if(result.next())
            {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  String encrypt_password(String password)
    {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return hashedPassword;
    }
}
