package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        if(account.getUsername().isEmpty() || account.getPassword().length() < 4 || accountDAO.accountName(account.getUsername()).equals(account.getUsername())){
            return null;
        }
        else{
            return accountDAO.accountLogin(account);
        }
    }
}
