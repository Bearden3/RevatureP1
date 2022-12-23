package com.revature.models;

public class User {

    private int user_id;
    private String username;
    private String pass;
    private String first_name;
    private String last_name;

    /*Employees in Java will contain entire Role objects instead of just an int foreign key
    an int FK is less useful to us than an entire Role object
    useful? If we have the entire Role object, we have access to all of that Role's DATA as well. more data :)
     */
    private Role role;

    private Reimbursement reimb;

    private int role_id_fk; //we're creating this variable to make inserts in Postman easier.
    //paired with a constructor, we can make it so that we only need to supply the FK in POST requests
        //as opposed to an entire role object
    private int reimb_id_fk;
    //boilerplate code----------------------

    //no-args constructor
    public User() {
    }

    //all-args
    public User(int user_id, String username, String pass, String first_name, String last_name, Role role) {
        this.user_id = user_id;
        this.username = username;
        this.pass = pass;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
    }
    public User(int user_id, String username, String pass, int role_id_fk, int reimb_id_fk, Role role) {
        this.user_id = user_id;
        this.username = username;
        this.pass = pass;
        this.role_id_fk = role_id_fk;
        this.reimb_id_fk = reimb_id_fk;
        this.role = role;
    }
    public User(int user_id, String username, String pass, String first_name, String last_name, int role_id_fk, int reimb_id_fk, Role role) {
        this.user_id = user_id;
        this.username = username;
        this.pass = pass;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role_id_fk = role_id_fk;
        this.reimb_id_fk = reimb_id_fk;
        this.role = role;
    }
    public User(int user_id, String username, String pass, Role role){
        this.user_id = user_id;
        this.username = username;
        this.pass = pass;
        this.role = role;
    }

    //all-args minus id
    public User(String username, String pass, String first_name, String last_name, Role role) {
        this.username = username;
        this.pass = pass;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
    }

    //constructor with no id, and int FK. To help with POST requests that insert an Employee
    //This gives us the flexibility to create a new employee without specifying and entire Role object
    public User(String first_name, String last_name, int role_id_fk) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.role_id_fk = role_id_fk;
    }

    public int getRole_id_fk() {
        return role_id_fk;
    }

    public void setRole_id_fk(int role_id_fk) {
        this.role_id_fk = role_id_fk;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {return username; }

    public void setUsername(String username) {this.username = username; }

    public String getPass() {return pass; }
    public void setPass(String pass) {this.pass = pass; }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Users{" +
                "users_id=" + user_id +
                ", username ='" + username + '\'' +
                ", password ='" + pass + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", role_id = '" + role_id_fk + '\'' +
                ", role=" + role +
                '}';
    }
}
