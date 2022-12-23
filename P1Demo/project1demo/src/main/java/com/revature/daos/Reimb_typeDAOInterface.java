package com.revature.daos;

import com.revature.models.Reimbursement_type;

public interface Reimb_typeDAOInterface {
    Reimbursement_type getTypeById(int id);
    boolean updateReimbType(String title, int salary);
}
