package com.revature.daos;

import com.revature.models.User;
import com.revature.models.Role;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO implements UserDAOInterface {







    @Override
    public ArrayList<User> getUser() {

        //instantiate a Connection object so that we can talk to the DB.
        try(Connection conn = ConnectionUtil.getConnection()){

            //A String that will represent our SQL statement
            String sql = "select * from users;";


            //No variables needed in the query above! no ?s
            //SO instead of a PreparedStatement, we'll use a regular Statement
            //If we don't have wildcards/variables, Statement is fine. Otherwise we need a PreparedStatment

            Statement s = conn.createStatement();
            //Now, execute the query, and save the results into a ResultSet
            ResultSet rs = s.executeQuery(sql);

            //Instantiate an empty ArrayList to hold our incoming Employee data (remember, Java can't read SQL)
            ArrayList<User> userList = new ArrayList();

            //use rs.next() in a while loop to create a new Employee for every incoming employee
            //rs.next() ITERATES through the incoming data. it will return false when there's no more data.
            while(rs.next()){ //rs.next will loop through the ResultSet until there's nothing else to look at

                //For every Employee returned from the DB, we'll make a new Employee object
                User e = new User(
                        rs.getInt("users_id"),
                        rs.getString("username"),
                        rs.getString("pass"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("role_id_fk"),
                        rs.getInt("reimb_id_fk"),
                        null
                        //there is no JDBC method for getRole().. we'll add the Role object below
                );

                //WOAH didn't we just make a method to get Roles by id? Yes!!!!
                int roleFK = rs.getInt("role_id_fk"); //retrieving role id
                int reimbFK = rs.getInt("reimb_id_fk");
                //Instantiate a Role object using the id we gathered
                //We need a RoleDAO object for that
                RoleDAO rDAO = new RoleDAO();
                Role r = rDAO.getRoleById(roleFK);

                //use the setter of our Employee object, and assign it the Role that we made above
                e.setRole(r);
                //Now, thanks to our setter, we have a FULLY INITIALIZED Employee object

                //Now, we can add it to the ArrayList
                userList.add(e);

            } //end of the while loop (we should have a full ArrayList of Employees)

            return userList; //after all that nonsense, return our fully populated ArrayList

        } catch(SQLException e){
            e.printStackTrace(); //VERY helpful if something goes wrong - tells us what and where
        }

        return null; //why are we returning null again? a couple of reasons.
        //It's easy to return when something goes wrong
          //  and often, in conditionals (like a try), we need some return statement
            //...so that the method will always be returning something no matter what

    }

    @Override
    public User insertUser(User emp) {

        //EVERY DAO METHOD needs to open a connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

        //we need to create our SQL string as usual
        String sql = "insert into users (username, pass, first_name, last_name, role_id_fk) values (?, ?, ?, ?, ?);";

        //Instantiate a PreparedStatement to hold our SQL and fill its variables
        PreparedStatement ps = conn.prepareStatement(sql);
        //fill in each wildcard using the Employee object in the arguments
            ps.setString(1, emp.getUsername());
            ps.setString(2, emp.getPass());
        ps.setString(3, emp.getFirst_name());
        ps.setString(4, emp.getLast_name());
        ps.setInt(5, emp.getRole().getRole_id());
        //now that our SQL String is populated, we can execute the update
        ps.executeUpdate();
        //not executeQuery()?? NO!!!! that's for SELECTS. we use executeUpdate for inserts, updates, deletes
        //no ResultSet because we aren't getting any data returned.

        //BUT we do want to tell the user that the data was inserted
        return emp;
        //this is same employee inputted by the user. send it back to them to see if all goes well

        } catch(SQLException e){
            e.printStackTrace(); //tell us what went wrong
        }
        return null;
    }

}