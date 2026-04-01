package model;

import java.util.ArrayList;
import java.util.List;

public class User {
     private String userId;
     private String name;
     private String email;
     private String password;
     private List<Account> accounts;

    public User(){
        this.accounts = new ArrayList<>();
    }

    public User(String userId, String name, String email, String password){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account){
        this.accounts.add(account);
    }

    public List<Account> getAccounts(){
        return accounts;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
