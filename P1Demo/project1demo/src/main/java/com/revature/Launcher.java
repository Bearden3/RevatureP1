package com.revature;

import com.revature.controllers.*;
import com.revature.daos.*;
import com.revature.models.Reimbursement;
import com.revature.models.Reimbursement_type;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;
import io.javalin.Javalin;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Launcher {

    public static void main(String[] args) {

        /*
        This is a try with resources block
        How they work, is a resource is opened within parenthesis
        If successful, the rest of the try block runs
        Another benefit is that the resource will close for us when the block completes
        This is helpful for code cleanup and preventing memory leaks
         */
        try(Connection conn = ConnectionUtil.getConnection()){
            System.out.println("CONNECTION SUCCESSFUL :)");
        }
        catch(SQLException e){
            System.out.println("connection failed :(");
        }

        //System.out.println(System.getenv("url"));
        //Typical Javalin object creation syntax
         Javalin app = Javalin.create(

            //This config lambda lets us specify certain configurations for our Javalin object
            // ->? "For this config object, do the following things"
                //ANYONE USING JAVALIN 5 SHOULD LEAVE THIS OUT
            //config -> {
              //  config.enableCorsForAllOrigins(); //This lets us process HTTP Requests from anywhere
            //}

        ).start(3000); //we need .start() to start our Java server on port 3000
        //You can do any port, I chose 3000 because probably nothing is using it.
        //a port is like a parking space for an application, where messages etc. can find it

        //ENDPOINT HANDLERS BELOW--------------------

        /*This is where we will specify different paths to different functionalities
         When requests come in, they must match one these paths in order to execute some specific behavior
         Handlers - they "handle" http requests */

        //instantiating Controllers so that we can access their Handlers
        UserController ec = new UserController();
        RoleController rc = new RoleController();
        AuthController ac = new AuthController();
        PendingController pe = new PendingController();
        ReimbursementController re = new ReimbursementController();

        // app.get() is the Javalin method that takes in GET requests.
       // In this case, it's calling to the getAllEmployeesHandler in the EmployeeController
       // SO, when we send a request to localhost:3000/employees, the getEmployeesHandler will execute
       app.get("/users", ec.getUserHandler);
       app.get("/reimb", re.getReimbHandler);
       app.get("/pending", pe.getPending);

        //app.post() is the Javalin method that takes in POST requests
        //why are we allowed to have two handlers that both take requests ending in /employees
        app.post("/users", ec.insertUser);
        app.post("/reimb", re.insertReimb);

        //app.patch() is the Javalin method that takes in PATCH requests
        //{title}?? This is a PATH PARAMETER. The value that the user inputs after /roles/ will be stored.
        app.patch("/pending/{reimb_description}", pe.changePending);

        //this is the endpoint handler for login
        app.post("/login", ac.loginHandler);

        //TEMPORARY - we'll be accessing the DAO using HTTP Requests later


        //Instantiate a RoleDAO and EmployeeDAO so that we can their methods
        RoleDAO rDAO = new RoleDAO();
        UserDAO eDAO = new UserDAO();
        ReimbursementDAO reDAO = new ReimbursementDAO();
        Reimb_TypeDAO tyDAO = new Reimb_TypeDAO();
        Reimb_StatusDAO stDAO = new Reimb_StatusDAO();

        //getting role by id
        //System.out.println(rDAO.getRoleById(2));

        //getting all employees
        ArrayList<User> users = eDAO.getUser();
        ArrayList<Reimbursement> reimbs = reDAO.getReimbursement();
        //ArrayList<Reimbursement> pending = reDAO.getPending();

        //Insert New User
        //User newuser = new User("ryan", "bewl","Reid", "Schroder", rDAO.getRoleById(1));
        //eDAO.insertUser(newuser);
        //users = eDAO.getUser();

        //Insert New Reimbursment
        //Reimbursement newreimb = new Reimbursement(2003, "Store", tyDAO.getTypeById(1), stDAO.getStatusById(2));
        //reDAO.addReimbursement(newreimb);
        ArrayList<Reimbursement> pending = reDAO.getPending();
        reimbs = reDAO.getReimbursement();
        //pending.add(newreimb);
        //update role
        //System.out.println(rDAO.updateRole("Manager"));

        //for(User e : users){
          //  System.out.println(e);
       // }

        //for(Reimbursement e : pending){
          //  System.out.println(e);
        //}

        //reassigning the employee list to the new data, and printing it out
        //users = eDAO.getUser();
        //for(User e : users){
          //  System.out.println(e);
        //}





    } //end of main method

}
