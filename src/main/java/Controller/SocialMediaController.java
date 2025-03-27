package Controller;

import org.eclipse.jetty.http.HttpTester.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    // Controller constructor
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService(); 
    }

   
   
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::userRegistrationHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::messagesByAccountHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void userRegistrationHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addAccount = accountService.addAccount(account);
        if(addAccount == null){
            ctx.status(400);    
        }
        else{
            ctx.json(mapper.writeValueAsString(addAccount));
            ctx.status(200);
        }

    }

    private void loginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account authAccount = accountService.loginAccount(account);

        if(authAccount != null){
            ctx.json(authAccount);
            ctx.status(200);
        }
        else{
            ctx.status(401);
        }
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Model.Message message = mapper.readValue(ctx.body(), Model.Message.class);
        Model.Message createMessage = messageService.addMessage(message);

        if(createMessage == null){
            ctx.status(400);
        }
        else{
            ctx.json(mapper.writeValueAsString(createMessage));
            ctx.status(200);
        }
        
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
        ctx.json(messageService.getAllMessages());
        ctx.status(200);
    }

    private void getMessageHandler(Context ctx) throws JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Model.Message message = messageService.getMessageById(id);
        
       if(message == null){
        ctx.status(200);
       } 
       else{
        ctx.json(message);
        ctx.status(200);
       }
        
    }

    private void deleteMessageHandler(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Model.Message deleteMessage = messageService.deleteMessageById(id);

        if(deleteMessage != null){
            ctx.json(deleteMessage);
            
        }
        ctx.status(200);
       
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Model.Message message = mapper.readValue(ctx.body(), Model.Message.class);
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Model.Message updateMessage = messageService.updateMessageById(id, message);
        System.out.println(updateMessage);

        if(updateMessage == null){
            ctx.status(200);
        }
        else{
            ctx.json(mapper.writeValueAsString(updateMessage));
            ctx.status(200);
        }
    }

    private void messagesByAccountHandler(Context ctx){
        
    }


}