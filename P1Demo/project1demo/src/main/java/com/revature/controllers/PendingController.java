package com.revature.controllers;

import com.google.gson.Gson;
import com.revature.daos.Reimb_StatusDAO;
import com.revature.daos.ReimbursementDAO;
import com.revature.models.Reimbursement;
import com.revature.models.Reimbursement_status;
import com.revature.models.Role;
import io.javalin.http.Handler;

import java.util.ArrayList;

public class PendingController {
    ReimbursementDAO rDAO = new ReimbursementDAO();
    Role role = new Role();
    Reimb_StatusDAO sDAO = new Reimb_StatusDAO();
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

    public Handler changePending = (ctx) -> {
        if(AuthController.ses != null) {
            String st = AuthController.ses.getAttribute("role_id_fk").toString();
            if(st.equals("2")) {

                System.out.println(AuthController.ses.getAttribute("role_id_fk"));
                System.out.println(AuthController.ses.getAttribute("users_id"));

                String descript = ctx.pathParam("reimb_description");
                //int to hold the new Role salary that the user will input in the HTTP Request body
                int reimb_status_id = Integer.parseInt(ctx.body()); //we need to parseInt() here since ctx.body() returns String



                if (sDAO.updateStatus(descript, reimb_status_id)) {
                    ctx.status(202); //202 "accepted"
                } else {
                    ctx.status(406); //406 "not acceptable"
                }
            } else{
                ctx.result("You must be logged in as a manager to access this");
            }

        } else { //if the user is NOT logged in:
            ctx.result("YOU MUST LOG IN As A Manger To Access This");
            ctx.status(401); //401 "unauthorized"
        }

    };
}
