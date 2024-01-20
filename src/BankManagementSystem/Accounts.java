package BankManagementSystem;

import java.sql.Connection;
import java.util.Scanner;

public class Accounts {


    private Connection connection;
    private  Scanner scanner;

    public Accounts(Connection conn,Scanner scanner){
        this.connection=conn;
        this.scanner=scanner;

    }
}
