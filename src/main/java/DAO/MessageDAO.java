package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    // create message
    public Message addMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, message.getPosted_by());
            statement.setString(2, message.getMessage_text());
            statement.setLong(3, message.getTime_posted_epoch());

            statement.executeUpdate();
            ResultSet autokey = statement.getGeneratedKeys();
            if(autokey.next()){
                int generated_message_id = (int) autokey.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //checks is posted_by refers to real user
    public boolean postedByExists(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, message.posted_by);

            ResultSet set = statement.executeQuery();
            return set.next();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;

    }
    
}
