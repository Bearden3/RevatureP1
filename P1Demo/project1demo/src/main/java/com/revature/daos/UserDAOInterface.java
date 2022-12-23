package com.revature.daos;

import com.revature.models.User;

import java.util.ArrayList;

//check RoleDAOInterface to review why we make Interfaces in JDBC
public interface UserDAOInterface {

    //here, we'll lay out a bunch of functionalities that EmployeeDAO will implement

    //A method to select all employees
    ArrayList<User> getUser();
    /*Why ArrayList? Get all will return multiple employees.
    So we need something that can store multiple Employee objects at once*/

    //A method to insert a new employee
    User insertUser(User users);
    /* if we're sending an Employee, why return one back?
    just so the User can see what they've inserted. Think of it as a confirmation. */


    //A method to delete employees (SELF STUDY PRACTICE?? probably next week OR friday)

}
