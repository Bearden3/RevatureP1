package com.revature.daos;

import com.revature.models.Reimbursement;

import java.util.ArrayList;

public interface ReimbursementDAOInterface {
    ArrayList<Reimbursement> getReimbursement();
    public ArrayList<Reimbursement> getPending();
    Reimbursement addReimbursement(Reimbursement emp);

    Reimbursement getReimbById(int id);

}
