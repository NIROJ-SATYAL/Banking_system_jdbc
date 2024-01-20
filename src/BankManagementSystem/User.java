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


    public int Login(){
            int id = 0;
        System.out.println("*********Login*********");
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        boolean iscorrect =check_password(password,email);
        if(iscorrect)
        {
            System.out.println("loging successfully");
            String query ="select user_id from users where email=?";
            try{
                PreparedStatement pst=connection.prepareStatement(query);
                pst.setString(1,email);
                ResultSet result=pst.executeQuery();
                if(result.next()){
                     id=result.getInt("user_id");
                    return id;
                }
                else{
                    System.out.println("invalid email");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else {
            System.out.println("wrong password;");
        }
        return id;



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

    public boolean check_password(String password ,String email)
    {
        String query ="Select password from users where email=?";
        try{
            PreparedStatement pst= connection.prepareStatement(query);
            pst.setString(1,email);
            ResultSet result=pst.executeQuery();
            if(result.next())
            {
                String hashpassword=result.getString("password");
                if(BCrypt.checkpw(password, hashpassword)){
                    return true;

                }
                else{
                    return false;
                }

            }
            else{
                return false;
            }


        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
