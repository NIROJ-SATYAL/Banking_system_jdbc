package BankManagementSystem;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;
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

            while(true){
                System.out.println("******************WELCOME TO BANKING SYSTEM*********");
                System.out.println("enter your choice....");
                System.out.println("1:Register");
                System.out.println("2:Login");
                System.out.println("3:Exist");
                int choice=scanner.nextInt();
                switch (choice)
                {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        System.out.println("Login page......");
                        break;
                    case 3:

                        System.out.println("Thank for visit...");
                        return;
                    default:
                        System.out.println("wrong input...");
                }
            }

            


        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());

        }




    }
}