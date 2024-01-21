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



    public void debit_money (long account_number){


        System.out.println("Enter Security pin ");
        scanner.nextLine();
        String  pin=scanner.nextLine();


        if(check_pin(pin,account_number)) {


            System.out.println("***********debit money***********");
            String query = "update user_account set balance=balance+? where Account_number=?";

            System.out.println("Enter Deposite Ammount");
            Double amount = scanner.nextDouble();

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


}
