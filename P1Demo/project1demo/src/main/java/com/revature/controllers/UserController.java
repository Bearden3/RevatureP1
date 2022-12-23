package com.revature.controllers;

import com.google.gson.Gson;
import com.revature.daos.UserDAO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import io.javalin.http.Handler;

import java.util.ArrayList;

/* The Controller layer is where HTTP Request get sent after Javalin directs them
 It's in this layer that JSON comes in and gets translated to Java and vice verse
 We'll either be getting data from the Service or DAO to get an HTTP Response to the front end (select)
 OR we'll be receiving data from the front end to send to the database (insert, update, delete)*/
public class UserController {

    //We need an EmployeeDAO object to use its methods
    UserDAO eDAO = new UserDAO();

    //This Handler will get the HTTP GET Request for "get all employees"...
    //then sends that Employee data back in an HTTP Response
    public Handler getUserHandler = (ctx) -> {

        if(AuthController.ses != null) {

            System.out.println(AuthController.ses.getAttribute("role_id_fk"));
            System.out.println(AuthController.ses.getAttribute("users_id"));

            //We need an ArrayList of Employees, courtesy of our EmployeeDAO
            ArrayList<User> employees = eDAO.getUser();

            Gson gson = new Gson();

            //use the GSON .toJson() method to turn our Java into a JSON String (JSON is always in String format
            String JSONEmployees = gson.toJson(employees);

            ctx.result(JSONEmployees);

            //we can set status code with ctx.status()
            ctx.status(202); //202 stands for accepted. 200 is default which is also fine

        } else { //if the user is NOT logged in:
            ctx.result("YOU MUST LOG IN TO DO THIS");
            ctx.status(401); //401 "unauthorized"
        }

    }; //semicolon after curly brace? That's lambdas for you.
    //lambda functions with code inside curly braces need to be terminated with semicolons.


    //This Handler will get the HTTP POST Request for inserting a new employee to the DB.
    public Handler insertUser = (ctx) -> {
        if(AuthController.ses != null) {

            //With POST requests, we have JSON data coming in, which we can access with ctx.body();
            //body??? it refers to the BODY (aka the DATA) sent with the HTTP Request (in this case, employee)
            String body = ctx.body(); //we now have a Java String holding a JSON String

            //Instantiate a new GSON object to JSON <-> Java conversions
            Gson gson = new Gson();

            //turn the incoming JSON data (stored in the body String) into an Employee object
            User newEmp = gson.fromJson(body, User.class);

            //we're calling the insert employees method from the EmployeeDAO
            // if it's successful, we'll send the new employee back in the response with a 201 status code
            // if it fails, we'll send an error message and a 406 status code


            if (eDAO.insertUser(newEmp) != null) { //if insert was succesful (which we set to return an Employee)
                ctx.status(201); //201 "created"
                ctx.result(body); //send back the employee
            } else {
                ctx.status(406); //406 "not acceptable"
                ctx.result("Insert employee failed!");
            }
        }else { //if the user is NOT logged in:
            ctx.result("YOU MUST LOG IN TO DO THIS");
            ctx.status(401); //401 "unauthorized"
        }
    };

}
