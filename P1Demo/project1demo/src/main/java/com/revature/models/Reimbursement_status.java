package com.revature.models;

public class Reimbursement_status {

    private int reimb_status_id;
    private String reimb_statuss;

    public Reimbursement_status(){
    }

    public Reimbursement_status(int reimb_status_id_id, String reimb_statuss){
        this.reimb_status_id = reimb_status_id_id;
        this.reimb_statuss = reimb_statuss;
    }

    public Reimbursement_status(String reimb_statuss){
        this.reimb_statuss = reimb_statuss;
    }

    public int getReimb_status_id() {
        return reimb_status_id;
    }
    public void setReimb_status_id(int reimb_status_id) {
        this.reimb_status_id = reimb_status_id;
    }
    public String getReimb_statuss() {
        return reimb_statuss;
    }
    public void setReimb_statuss(String reimb_statuss) {
        this.reimb_statuss = reimb_statuss;
    }
    @Override
    public String toString() {
        return  reimb_statuss + ", id: " +
                reimb_status_id;
    }
}
