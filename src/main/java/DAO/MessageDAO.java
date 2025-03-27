package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    // create message
    public Message addMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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
    public boolean postedByExists(int posted_by){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT COUNT(*) FROM account WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, posted_by);

            ResultSet set = statement.executeQuery();
            if(set.next()){
                int count = set.getInt(1);
                return count > 0;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;

    }

    // get all messages
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet set = statement.executeQuery();

            while(set.next()){
                Message message = new Message(set.getInt("message_id"),set.getInt("posted_by"), set.getString("message_text"), set.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    
    // get a message by its id
    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            ResultSet set = statement.executeQuery();

            while(set.next()){
                Message message = new Message(set.getInt("message_id"), set.getInt("posted_by"), set.getString("message_text"), set.getLong("time_posted_epoch"));
                return message;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // delete message by id 
    public void deleteMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            statement.executeQuery();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // update message by id
    public boolean updateMessageById(int id, String message_text){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET  message_text = ? WHERE message_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, message_text);
            statement.setInt(2, id);
            

            int updated = statement.executeUpdate();
            return updated > 0;

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
