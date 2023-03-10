package com.revature.models;

/*WHAT is a DTO?? Data Transfer Object. It's meant to model some data that doesn't match a DB table
 In this case, we need a Class that can hold the user-inputted username/password when they login
 The username/password that the user enters will get stored in a LoginDTO object.
 You NEVER store a DTO in the database. It's strictly for data transfer from frontend/backend

 Two main DTO use cases:
 1) When you don't want to send or use an entire object (you just need username/password to login)
 2) When you don't intend to store incoming data in the DB (meant just for the Java logic)
 */
public class LoginDTO {

    //variables that will hold the first name and last name. again, this should be username/password in your P1
    private String username;
    private String pass;

    //realistically, we only need an all-args constructor
    public LoginDTO(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
