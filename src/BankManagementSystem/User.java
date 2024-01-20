package BankManagementSystem;
import java.util.*;
import java.sql.*;

public class User {


    private  Scanner scanner;
    private  Connection connection;

    public User(  Connection conn, Scanner scanner){
        this.connection=conn;
        this.scanner=scanner;

        System.out.println("make User class successfully");
    }
}
