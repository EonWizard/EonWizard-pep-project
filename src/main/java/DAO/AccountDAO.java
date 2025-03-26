package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    // create a new account
    public Account addAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());

            statement.executeUpdate();
            ResultSet autokey = statement.getGeneratedKeys();
            if(autokey.next()){
                int generated_account_id = (int) autokey.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }
        catch(SQLException e){
            System.out.println();
        }
        return null;
    }

    // login for account
    public Account accountLogin(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet set = statement.executeQuery();

            while(set.next()){
                Account accountLogin = new Account(set.getInt("account_id"), set.getString("username"), set.getString("password"));
                return accountLogin;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // gets the name of account to check for existence in database
    public boolean accountExist(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);

            ResultSet set = statement.executeQuery();
            return set.next();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
