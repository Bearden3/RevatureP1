package com.revature.daos;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.models.Role;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAO {

    //for THIS method in particular, since we don't have username/password, we'll use first_name/last_name
    //change it accordingly for your own application. Users should have username/password in YOUR p1

    public User login(String username, String pass){

       try(Connection conn = ConnectionUtil.getConnection()){

            String sql = "select * from users where username = ? and pass = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

           ps.setString(1, username);
           ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            //since we're only expecting one record, we can just use an if with rs.next() instead of while
            if(rs.next()){

                User e = new User(
                        rs.getInt("users_id"),
                        rs.getString("username"),
                        rs.getString("pass"),
                        rs.getInt("role_id_fk"),
                        rs.getInt("reimb_id_fk"),

                  null
                );

                int roleFk = rs.getInt("role_id_fk");
                int reimbFk = rs.getInt("reimb_id_fk");

                RoleDAO rDAO = new RoleDAO();
                ReimbursementDAO reDAO = new ReimbursementDAO();

                Role r = rDAO.getRoleById(roleFk);
                Reimbursement re = reDAO.getReimbById(reimbFk);

                e.setRole(r);

                //for comments on everything we're doing above, you can check getEmployees in EmployeeDAO

                return e; //returning the Employee with the matching first_name/last_name

            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }



    //presumably you'd put your register method here as well, unless you just use the insert method for that
    //for P1 you can probably just stick with the insert employee method for register user

}
