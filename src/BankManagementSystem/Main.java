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
                        int user_id= user.Login();
                        if(user_id!=0)
                        {
                            if(!accounts.account_exist(user_id)){
                                System.out.println();
                                System.out.println("1. Open a new Bank Account");
                                System.out.println("2. Exit");
                                if(scanner.nextInt() == 1) {
                                   long account_number = accounts.open_account(user_id);

                                    System.out.println("Account Created Successfully");
                                    System.out.println("Your Account Number is: " + account_number);
                                }else{
                                    break;
                                }
                            }


                                long account_number=accounts.get_accountnumber(user_id);
                                int choice2 = 0;
                                while (choice2 != 5) {
                                    System.out.println();
                                    System.out.println("1. Debit Money");
                                    System.out.println("2. Credit Money");
                                    System.out.println("3. Transfer Money");
                                    System.out.println("4. Check Balance");
                                    System.out.println("5.Show details");
                                    System.out.println("6. Log Out");
                                    System.out.println("Enter your choice: ");
                                    choice2 = scanner.nextInt();

                                    switch(choice2)
                                    {
                                        case 1:
                                            accountManager.debit_money(account_number);
                                            break;
                                        case 2:
                                            accountManager.credit_money(account_number);
                                            break;
                                        case 3:
                                            System.out.println("Transfer money");
                                            break;
                                        case 4:
                                            accountManager.check_balance(account_number);
                                            break;
                                        case 5:
                                            System.out.println("user details");
                                            break;
                                        case 6:
                                            System.out.println("logout");
                                            break;
                                        default:
                                            System.out.println("Enter Valid Choice!");
                                            break;

                                    }
                                }



                        }
                        else {
                            return;
                        }
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