package com.revature.daos;

import com.revature.models.Reimbursement_status;

public interface Reimb_statusDAOInterface {
    Reimbursement_status getStatusById(int id);

    //a method that updates a Role's salary.
    boolean updateStatus(String reimbId, int statusId);
}
