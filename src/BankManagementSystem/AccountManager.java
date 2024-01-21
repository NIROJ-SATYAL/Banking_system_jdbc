package BankManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {


    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection conn,Scanner scanner){
        this.connection=conn;
        this.scanner=scanner;

    }



    public void debit_money (long account_number) throws SQLException {


        System.out.println("Enter Security pin ");
        scanner.nextLine();
        String  pin=scanner.nextLine();


        if(check_pin(pin,account_number)) {


            System.out.println("***********debit money***********");
            String query = "update user_account set balance=balance-? where Account_number=?";

            System.out.println("Enter  Ammount");
            Double amount = scanner.nextDouble();
            if(check_ammount(amount,account_number)) {
                try {
                    connection.setAutoCommit(false);
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setDouble(1,amount);
                    pst.setLong(2,account_number);
                    int rowAffected=pst.executeUpdate();
                    if (rowAffected > 0) {
                        System.out.println("Rs."+amount+" debited Successfully");
                        connection.commit();
                        connection.setAutoCommit(true);
                        return;
                    } else {
                        System.out.println("Transaction Failed!");
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            else {
                System.out.println("insufficient ammount....");
            }

        }
        else {
            System.out.println("wrong pin........");
        }



    }






    public void credit_money (long account_number) throws SQLException {


        System.out.println("Enter Security pin ");
        scanner.nextLine();
        String  pin=scanner.nextLine();


        if(check_pin(pin,account_number)) {


            System.out.println("***********credit money***********");
            String query = "update user_account set balance=balance+? where Account_number=?";

            System.out.println("Enter Deposite Ammount");
            Double amount = scanner.nextDouble();

                try {
                    connection.setAutoCommit(false);
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setDouble(1,amount);
                    pst.setLong(2,account_number);
                    int rowAffected=pst.executeUpdate();
                    if (rowAffected > 0) {
                        System.out.println("Rs."+amount+" credited Successfully");
                        connection.commit();
                        connection.setAutoCommit(true);
                        return;
                    } else {
                        System.out.println("Transaction Failed!");
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }



        }
        else {
            System.out.println("wrong pin........");
        }



    }


    public boolean check_pin(String  pin,long account_number){

        String query="select Security_pin from user_account where Account_number=?";
        String fetch_pin = null;

        try
        {
            PreparedStatement pst=connection.prepareStatement(query);
            pst.setLong(1,account_number);
            ResultSet result=pst.executeQuery();
            if(result.next())
            {
                 fetch_pin=result.getString("Security_pin");

            }
            else {
                System.out.println("error occur during fetching");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(fetch_pin.isEmpty())
        {
            return false;
        }
        else {

            if(fetch_pin.equals(pin))
            {
                return true;
            }
            else {
                return false;
            }
        }



    }


    public boolean check_ammount( Double ammount,Long account_number)throws SQLException
    {
        String query="select balance from user_account where Account_number=?";
        try{
            PreparedStatement pst=connection.prepareStatement(query);
            pst.setLong(1,account_number);
            ResultSet result=pst.executeQuery();

            if(result.next())
            {
                return ammount <= result.getDouble("balance");
            }
            else {
                return false;
            }

        }
        catch(SQLException e)
        {
           throw new  RuntimeException("Error",e);
        }
    }


    public void check_balance(Long account_number)
    {
        System.out.println("**********check balance*********");
        System.out.println("enter the pin:");
        scanner.nextLine();
        String pin=scanner.nextLine();
        if(check_pin(pin,account_number))
        {
            String query= "Select balance from user_account where Account_number=? and Security_pin=?";
            try {
                PreparedStatement pst= connection.prepareStatement(query);
                pst.setLong(1,account_number);
                pst.setString(2,pin);
                ResultSet result=pst.executeQuery();
                if(result.next()){
                    Double total_ammount= result.getDouble("balance");
                    System.out.println("your total ammount is:" + total_ammount + "and your account_number is:" + account_number + ":");
                }
                else {
                    System.out.println("failed to fetch ammount");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("wrong pin number");
        }


    }
    public void show_details(Long account_number){
        System.out.println("**********User Details*********");
        System.out.println("enter the pin:");
        scanner.nextLine();
        String pin=scanner.nextLine();
        if(check_pin(pin,account_number)){

            int userID= fetch_userID(account_number,pin);

            String query="SELECT users.full_name, users.email, user_account.Account_number, user_account.balance, user_account.Security_pin\n" +
                    "FROM users\n" +
                    "INNER JOIN user_account ON users.user_id = user_account.userID\n" +
                    "WHERE users.user_id = ?;";


            try{
                PreparedStatement pst=connection.prepareStatement(query);
                pst.setInt(1,userID);
                ResultSet result=pst.executeQuery();
                if(result.next()){
                    String name=result.getString("full_name");
                    String email=result.getString("email");
                    Long account_num=result.getLong("Account_number");
                    Double balance=result.getDouble("balance");
                    String user_pin =result.getString("Security_pin");
                    System.out.println(name);
                    System.out.println(email);
                    System.out.println(account_num);
                    System.out.println(balance);
                    System.out.println(user_pin);




                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
        else {
            System.out.println("wrong pin.......");
        }
    }


    public int fetch_userID(Long account_number,String pin){

        String query="Select userID from user_account where Account_number=? and Security_pin=?";

        try{
            PreparedStatement pst=connection.prepareStatement(query);
            pst.setLong(1,account_number);
            pst.setString(2,pin);
            ResultSet result=pst.executeQuery();
            if(result.next())
            {
                return result.getInt("userID");
            }
             else {
                // Handle case where no result is found (return a default value or throw an exception)
                throw new RuntimeException("No user found with the given account number and pin");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Transaction_money(Long sender_account_number) throws SQLException {
        System.out.println("*************Money Transcation********");
        System.out.println("Enter pin:");
        scanner.nextLine();
        String pin=scanner.nextLine();
        connection.setAutoCommit(false);

        if(check_pin(pin,sender_account_number)){

            System.out.print("Enter Receiver Account Number: ");
            long receiver_account_number = scanner.nextLong();
            System.out.print("Enter Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            if(check_ammount(amount,sender_account_number))
            {
                    String query="Select * from user_account where Account_number=?";
                    PreparedStatement pst=connection.prepareStatement(query);
                    pst.setLong(1,receiver_account_number);
                    ResultSet result=pst.executeQuery();
                    if(result.next())
                    {
                      String sendQuery="update user_account set balance=balance - ? where Account_number=?";
                      String receiveQuery="update user_account set balance=balance+? where Account_number=?";
                      PreparedStatement sendpst=connection.prepareStatement(sendQuery);
                      PreparedStatement receivepst=connection.prepareStatement(receiveQuery);
                      sendpst.setDouble(1,amount);
                      sendpst.setLong(2,sender_account_number);
                      receivepst.setDouble(1,amount);
                      receivepst.setLong(2,receiver_account_number);
                      int sendrowaffected=sendpst.executeUpdate();
                      int receiverowaffected=receivepst.executeUpdate();
                      if(sendrowaffected>0 && receiverowaffected>0)
                      {
                          System.out.println("Transaction Successful!");
                          System.out.println("Rs."+amount+" Transferred Successfully");
                          connection.commit();
                          connection.setAutoCommit(true);
                      }
                      else {
                          System.out.println("Transaction Failed");
                          connection.rollback();
                          connection.setAutoCommit(true);
                      }


                    }
                    else {
                        System.out.println("Doesn't find  Account.....");
                    }

            }
            else {
                System.out.println("insufficient amount......");
            }
        }
        else{
            System.out.println("wrong pin........");
        }
    }






}
