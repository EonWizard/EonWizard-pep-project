package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;

    // constructors
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    // create a new message
    public Message addMessage(Message message){
        if(message.getMessage_text().isBlank() || message.getMessage_text().length() > 255 || !messageDAO.postedByExists(message.getPosted_by())){
            return null;
        }
        
       Message addMessage = messageDAO.addMessage(message);
       if(addMessage == null){
        System.out.println("Fail to add message");
       }
       else{
        System.out.println("Success");
       }
       return addMessage;
    }

    // get all messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    // get a message by id
    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    // delete a message by id
    public Message deleteMessageById(int id){
        Message message = messageDAO.getMessageById(id);
        if(message != null){
            messageDAO.deleteMessageById(id);
            return message;
        }
        else{
            return null;
        }
    }

    // update a message by id 
    public Message updateMessageById(int id, String messageText){
        if(messageText == null || messageText.isBlank()|| messageText.length() > 255){
            return null;
        }
        boolean updated = messageDAO.updateMessageById(id, messageText);
        return updated ? messageDAO.getMessageById(id) : null;
    }
    
        
        
    
}
