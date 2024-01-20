package BankManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Accounts {


    private Connection connection;
    private  Scanner scanner;

    public Accounts(Connection conn,Scanner scanner){
        this.connection=conn;
        this.scanner=scanner;






    }

    public long open_account(int id){

            long account_number;
        String query="insert into user_account(Account_number,balance,Security_pin,userID) values (?,?,?,?)";

        System.out.println("**********Register your bank account*********");
        scanner.nextLine();

        System.out.print("Enter Initial Amount: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        int  security_pin = scanner.nextInt();

        try{
             account_number=generateAccountNumber();
            PreparedStatement pst=connection.prepareStatement(query);
            pst.setLong(1,account_number);
            pst.setDouble(2,balance);
            pst.setInt(3,security_pin);
            pst.setInt(4,id);
            int rowaffected=pst.executeUpdate();
            if (rowaffected > 0) {
                return account_number;
            } else {
                System.out.println("failed to register");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

            return account_number;


    }


    public   boolean account_exist(int id){
        String query="select * from user_account where userID=?";

        try{
            PreparedStatement pst=connection.prepareStatement(query);
            pst.setInt(1,id);
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

    public long generateAccountNumber()
    {
        Random random = new Random();

        // Generate a random long with 10 digits
        long randomNumber = (long) (Math.pow(10, 9) + random.nextDouble() * Math.pow(10, 9));

        return randomNumber;
    }


    public long get_accountnumber(int id) {
        String query = "SELECT Account_number from user_account WHERE userID = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("Account_number");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Account Number Doesn't Exist!");
    }
}
