package Service;

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
        if(message.getMessage_text().isBlank() || message.getMessage_text().length() > 255 || !messageDAO.postedByExists(message)){
            return null;
        }
        return messageDAO.addMessage(message);
    }
    
}
