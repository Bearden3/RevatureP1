package com.revature.controllers;

import com.google.gson.Gson;
import com.revature.daos.ReimbursementDAO;
import com.revature.models.Reimbursement;
import io.javalin.http.Handler;

import java.util.ArrayList;
public class ReimbursementController {
    ReimbursementDAO rDAO = new ReimbursementDAO();
    public Handler getReimbHandler = (ctx) -> {

        if(AuthController.ses != null) {

            //this is just showing how to retrieve saved session attributes
            //realistically, you'd be putting these values into your DAOs
            System.out.println(AuthController.ses.getAttribute("role_id_fk"));
            System.out.println(AuthController.ses.getAttribute("users_id"));

            //We need an ArrayList of Employees, courtesy of our EmployeeDAO
            ArrayList<Reimbursement> employees = rDAO.getReimbursement();

            //PROBLEM: we can't send plain Java in an HTTP Response. We need JSON! This is where GSON comes in

            //instantiate a GSON object so that we can make Java <-> JSON conversions
            Gson gson = new Gson();

            //use the GSON .toJson() method to turn our Java into a JSON String (JSON is always in String format
            String JSONEmployees = gson.toJson(employees);

            //we use ctx.result() to send back an HTTP Response
            //in this case, the user requests all employee data, so that's what we're sending.
            ctx.result(JSONEmployees);

            //we can set status code with ctx.status()
            ctx.status(202); //202 stands for accepted. 200 is default which is also fine

            //we don't actually want to kill the User's session after they get all employees
            //but here's how we would close a Session
            //AuthController.ses.invalidate();

        } else { //if the user is NOT logged in:
            ctx.result("YOU MUST LOG IN TO DO THIS");
            ctx.status(401); //401 "unauthorized"
        }

    }; //semicolon after curly brace? That's lambdas for you.
    //lambda functions with code inside curly braces need to be terminated with semicolons.


    //This Handler will get the HTTP POST Request for inserting a new employee to the DB.
    public Handler insertReimb = (ctx) -> {
        if(AuthController.ses != null) {

            //With POST requests, we have JSON data coming in, which we can access with ctx.body();
            //body??? it refers to the BODY (aka the DATA) sent with the HTTP Request (in this case, employee)
            String body = ctx.body(); //we now have a Java String holding a JSON String

            //Instantiate a new GSON object to JSON <-> Java conversions
            Gson gson = new Gson();

            //turn the incoming JSON data (stored in the body String) into an Employee object
            Reimbursement newEmp = gson.fromJson(body, Reimbursement.class);

            //we're calling the insert employees method from the EmployeeDAO
            // if it's successful, we'll send the new employee back in the response with a 201 status code
            // if it fails, we'll send an error message and a 406 status code


            if (rDAO.addReimbursement(newEmp) != null) { //if insert was succesful (which we set to return an Employee)
                ctx.status(201); //201 "created"
                ctx.result(body); //send back the employee
            } else {
                ctx.status(406); //406 "not acceptable"
                ctx.result("Add Reimbursement failed!");
            }
        }else { //if the user is NOT logged in:
            ctx.result("YOU MUST LOG IN TO DO THIS");
            ctx.status(401); //401 "unauthorized"
        }
    };

    public Handler getPending = (ctx) -> {

        if(AuthController.ses != null) {

            //this is just showing how to retrieve saved session attributes
            //realistically, you'd be putting these values into your DAOs
            System.out.println(AuthController.ses.getAttribute("role_id_fk"));
            System.out.println(AuthController.ses.getAttribute("users_id"));

            //We need an ArrayList of Employees, courtesy of our EmployeeDAO
            ArrayList<Reimbursement> employees = rDAO.getPending();

            //PROBLEM: we can't send plain Java in an HTTP Response. We need JSON! This is where GSON comes in

            //instantiate a GSON object so that we can make Java <-> JSON conversions
            Gson gson = new Gson();

            //use the GSON .toJson() method to turn our Java into a JSON String (JSON is always in String format
            String JSONEmployees = gson.toJson(employees);

            //we use ctx.result() to send back an HTTP Response
            //in this case, the user requests all employee data, so that's what we're sending.
            ctx.result(JSONEmployees);

            //we can set status code with ctx.status()
            ctx.status(202); //202 stands for accepted. 200 is default which is also fine

            //we don't actually want to kill the User's session after they get all employees
            //but here's how we would close a Session
            //AuthController.ses.invalidate();

        } else { //if the user is NOT logged in:
            ctx.result("YOU MUST LOG IN TO DO THIS");
            ctx.status(401); //401 "unauthorized"
        }

    }; //semicolon after curly brace? That's lambdas for you.
    //lambda functions with code inside

}
