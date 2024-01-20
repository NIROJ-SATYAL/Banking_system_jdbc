package BankManagementSystem;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.*;
import java.util.Properties;

public class Main {
    private static String db_url ;
    private static String db_username;
    private static  String db_password;


    static {
        loadDatabaseProperties();
    }

    private static void loadDatabaseProperties() {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("BankManagementSystem/env.properties")) {
            Properties properties = new Properties();
            if (input != null) {
                properties.load(input);
                db_url = properties.getProperty("db_url");
                db_username = properties.getProperty("db_username");
                db_password = properties.getProperty("db_password");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws SQLException ,ClassNotFoundException {
        System.out.println(db_password);
        System.out.println(db_username);
        System.out.println(db_url);

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
            Connection conn= DriverManager.getConnection(db_url,db_username,db_password);
            Scanner scanner=new Scanner(System.in);
            System.out.println("connection Established successfully....");

            User user=new User(conn,scanner);
            AccountManager accountManager=new AccountManager(conn,scanner);
            Accounts accounts=new Accounts(conn,scanner);

            


        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());

        }




    }
}