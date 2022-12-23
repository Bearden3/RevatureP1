package com.revature.daos;

import com.revature.models.Reimbursement_status;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reimb_StatusDAO implements Reimb_statusDAOInterface {
    @Override
    public Reimbursement_status getStatusById(int id) {

        //use a try-with-resources block to open our connection and host our DB communication
        try(Connection conn = ConnectionUtil.getConnection()){

            /*
             We need a String that lays out the sql query we intend to run on the DB
             This String has a wildcard/parameter/variable for the role_id
             We have to take the user-inputted role id and put it into this statement somehow
              */
            String sql = "select * from reimb_status where reimb_status_id = ?;";

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

                /*We need to use the data from the ResultSet to fill in a Role all-args constructor
                    Basically, we need to make a Role object from the data */
                Reimbursement_status status = new Reimbursement_status(
                        rs.getInt("reimb_status_id"),
                        rs.getString("reimb_statuss")
                );
                //This is just a CONSTRUCTOR that we opened up for the sake of cleaner code

                return status; //return the Role data to the user!!
            }
        } catch(SQLException e){
            e.printStackTrace(); //if something goes wrong, this will display an error message
            //VERY important for debugging, so we know what went wrong and where
        }

        return null; //null will get returned if something goes wrong
    }

    @Override
    public boolean updateStatus(String reimbId, int statusId) {

        //As usual, we need to open a connection
        /*Try with resources refresher
            In this try block, we open a Connection object within parenthesis
            By doing this, our Connection closes right after the try completes
            this is good for code cleanup and preventing memory leaks
          */
        try(Connection conn = ConnectionUtil.getConnection()){

            //create our SQL String (to be filled with values from the method arguments)
            String sql = "update reimb set reimb_status_fk = ? where reimb_description = ?;";

            //Prepared statement so that we can fill appropriate values
            PreparedStatement ps = conn.prepareStatement(sql);

            //using ps.setXZY we can fill the wildcards (?) in our SQL statement
            ps.setString(2, reimbId);
            ps.setInt(1, statusId);
            //execute the update!
            ps.executeUpdate();

            //if we get this far in the try block, we can assume nothing went wrong. return true.
            return true;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }
}
