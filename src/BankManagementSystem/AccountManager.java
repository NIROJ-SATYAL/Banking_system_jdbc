package BankManagementSystem;

import java.sql.Connection;
import java.util.Scanner;

public class AccountManager {


    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection conn,Scanner scanner){
        this.connection=conn;
        this.scanner=scanner;
        System.out.println("Create Account Manager class successfully.....");
    }


}
