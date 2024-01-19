package BankManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static String url = "jdbc:mysql://localhost:3306/banking_system";
    private static String db_name ="root";
    private static  String db_password="jorin!@#1";
    public static void main(String[] args) throws SQLException ,ClassNotFoundException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            System.out.println("Driver load successfully.....");

        }
        catch( ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            Connection conn= DriverManager.getConnection(url,db_name,db_password);
            Scanner scanner=new Scanner(System.in);
            System.out.println("connection Established successfully....");


        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }




    }
}