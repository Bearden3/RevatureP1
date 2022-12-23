package com.revature.daos;

import com.revature.models.Reimbursement;
import com.revature.models.Reimbursement_status;
import com.revature.models.Reimbursement_type;
import com.revature.models.Role;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

public class ReimbursementDAO implements ReimbursementDAOInterface{
    @Override
    public ArrayList<Reimbursement> getReimbursement() {

        //instantiate a Connection object so that we can talk to the DB.
        try(Connection conn = ConnectionUtil.getConnection()){

            //A String that will represent our SQL statement
            String sql = "select * from reimb;";

            /*
            No variables needed in the query above! no ?s
            SO instead of a PreparedStatement, we'll use a regular Statement
            //If we don't have wildcards/variables, Statement is fine. Otherwise we need a PreparedStatment
            */
            Statement s = conn.createStatement();

            //Now, execute the query, and save the results into a ResultSet
            ResultSet rs = s.executeQuery(sql);

            //Instantiate an empty ArrayList to hold our incoming Employee data (remember, Java can't read SQL)
            ArrayList<Reimbursement> reimbList = new ArrayList();

            //use rs.next() in a while loop to create a new Employee for every incoming employee
            //rs.next() ITERATES through the incoming data. it will return false when there's no more data.
            while(rs.next()){ //rs.next will loop through the ResultSet until there's nothing else to look at

                //For every Employee returned from the DB, we'll make a new Employee object
                Reimbursement e = new Reimbursement(
                        rs.getInt("reimb_id"),
                        rs.getInt("reimb_amount"),
                        rs.getString("reimb_description"),
                        rs.getInt("reimb_type_fk"),
                        rs.getInt("reimb_status_fk")
                );

                //WOAH didn't we just make a method to get Roles by id? Yes!!!!
                int reimb_type_FK = rs.getInt("reimb_type_fk"); //retrieving role id
                int reimb_status_FK = rs.getInt("reimb_status_fk"); //retrieving role id

                //Instantiate a Role object using the id we gathered
                //We need a RoleDAO object for that
                Reimb_TypeDAO rDAO = new Reimb_TypeDAO();
                Reimbursement_type r = rDAO.getTypeById(reimb_type_FK);

                //use the setter of our Employee object, and assign it the Role that we made above
                e.setReimbursement_type(r);

                Reimb_StatusDAO sDAO = new Reimb_StatusDAO();
                Reimbursement_status st = sDAO.getStatusById(reimb_status_FK);

                //use the setter of our Employee object, and assign it the Role that we made above
                e.setReimbursement_status(st);
                //Now, thanks to our setter, we have a FULLY INITIALIZED Employee object

                //Now, we can add it to the ArrayList
                reimbList.add(e);

            } //end of the while loop (we should have a full ArrayList of Employees)

            return reimbList; //after all that nonsense, return our fully populated ArrayList

        } catch(SQLException e){
            e.printStackTrace(); //VERY helpful if something goes wrong - tells us what and where
        }

        return null; //why are we returning null again? a couple of reasons.
    }

    @Override
    public ArrayList<Reimbursement> getPending() {

        //instantiate a Connection object so that we can talk to the DB.
        try(Connection conn = ConnectionUtil.getConnection()){

            //A String that will represent our SQL statement
            String sql = "select * from reimb where reimb_status_fk = ?;";

            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(1, 1);

            //Now, execute the query, and save the results into a ResultSet
            ResultSet rs = s.executeQuery();

            //Instantiate an empty ArrayList to hold our incoming Employee data (remember, Java can't read SQL)
            ArrayList<Reimbursement> reimbList = new ArrayList();

            while(rs.next()){ //rs.next will loop through the ResultSet until there's nothing else to look at

                //For every Employee returned from the DB, we'll make a new Employee object
                Reimbursement e = new Reimbursement(
                        rs.getInt("reimb_id"),
                        rs.getInt("reimb_amount"),
                        rs.getString("reimb_description"),
                        rs.getInt("reimb_type_fk"),
                        rs.getInt("reimb_status_fk")
                );

                //WOAH didn't we just make a method to get Roles by id? Yes!!!!
                int reimb_type_FK = rs.getInt("reimb_type_fk"); //retrieving role id
                int reimb_status_FK = rs.getInt("reimb_status_fk"); //retrieving role id

                Reimb_TypeDAO rDAO = new Reimb_TypeDAO();
                Reimbursement_type r = rDAO.getTypeById(reimb_type_FK);

                //use the setter of our Employee object, and assign it the Role that we made above
                e.setReimbursement_type(r);

                Reimb_StatusDAO sDAO = new Reimb_StatusDAO();
                Reimbursement_status st = sDAO.getStatusById(reimb_status_FK);

                e.setReimbursement_status(st);
                reimbList.add(e);

            } //end of the while loop (we should have a full ArrayList of Employees)

            return reimbList; //after all that nonsense, return our fully populated ArrayList

        } catch(SQLException e){
            e.printStackTrace(); //VERY helpful if something goes wrong - tells us what and where
        }

        return null; //why are we returning null again? a couple of reasons.
    }

    @Override
    public Reimbursement addReimbursement(Reimbursement emp) {

        //EVERY DAO METHOD needs to open a connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            //we need to create our SQL string as usual
            String sql = "insert into reimb (reimb_amount, reimb_description, reimb_type_fk, reimb_status_fk) values (?, ?, ?, ?);";

            //Instantiate a PreparedStatement to hold our SQL and fill its variables
            PreparedStatement ps = conn.prepareStatement(sql);

            //fill in each wildcard using the Employee object in the arguments
            ps.setInt(1, emp.getReimb_amount());
            ps.setString(2, emp.getReimb_description());
            ps.setInt(3, emp.getReimbursement_type().getReimb_type_id());
            ps.setInt(4, emp.getReimbursement_status().getReimb_status_id());

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

    @Override
    public Reimbursement getReimbById(int id) {

        //use a try-with-resources block to open our connection and host our DB communication
        try(Connection conn = ConnectionUtil.getConnection()){

            /*
             We need a String that lays out the sql query we intend to run on the DB
             This String has a wildcard/parameter/variable for the role_id
             We have to take the user-inputted role id and put it into this statement somehow
              */
            String sql = "select * from reimb where reimb_id = ?;";

            //we need a PreparedStatement object to fill the variable in
            //PreparedStatements "prepare" a query to get sent to the DB
            PreparedStatement ps = conn.prepareStatement(sql);

            //now, we can insert a value for the ? above
            ps.setInt(1, id); //"the first wildcard will be equal to the id variable"

            /*
            Here, we're running the SQL statement stored in the PreparedStatement
            The results of the SQL statement will get stored in the ResultSet object
             */
            ResultSet rs = ps.executeQuery();

            /*While loop to extract the resultset data
            WHILE there are results in the ResultNext (.next())...
            Make a new Role object.
             */
            while(rs.next()){

                Reimbursement role = new Reimbursement(
                        rs.getInt("reimb_id"),
                        rs.getInt("reimb_amount"),
                        rs.getString("reimb_description"),
                        null, null
                );
                return role; //return the Role data to the user!!
            }
        } catch(SQLException e){
            e.printStackTrace(); //if something goes wrong, this will display an error message

        }
        return null;
    }
}
