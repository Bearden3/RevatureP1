package com.revature.models;

public class Reimbursement {

    private int reimb_id;
    private int reimb_amount;
    private String reimb_description;
    private int reimb_type_fk;
    private int reimb_status_fk;

    private Reimbursement_status reimbursement_status;
    private Reimbursement_type reimbursement_type;

    public Reimbursement(){
    }
        //All Args
    public Reimbursement(int reimb_id, int reimb_amount, String reimb_description, Reimbursement_type reimb_type_fk, Reimbursement_status reimb_statuss_fk){
        this.reimb_id = reimb_id;
        this.reimb_amount = reimb_amount;
        this.reimb_description = reimb_description;
        this.reimbursement_type = reimb_type_fk;
        this.reimbursement_status = reimb_statuss_fk;

    }

    public Reimbursement(int reimb_id, int reimb_amount, String reimb_description, int reimb_type_fk, int reimb_statuss_fk){
        this.reimb_id = reimb_id;
        this.reimb_amount = reimb_amount;
        this.reimb_description = reimb_description;
        this.reimb_type_fk = reimb_type_fk;
        this.reimb_status_fk = reimb_statuss_fk;

    }
        //No Id Args
    public Reimbursement(int reimb_amount, String reimb_description, Reimbursement_type reimb_type_fk, Reimbursement_status reimb_statuss_fk){
        this.reimb_amount = reimb_amount;
        this.reimb_description = reimb_description;
        this.reimbursement_type = reimb_type_fk;
        this.reimbursement_status = reimb_statuss_fk;

    }

    public Reimbursement(int reimb_amount, String reimb_description, int reimb_type_fk, int reimb_statuss_fk){
        this.reimb_amount = reimb_amount;
        this.reimb_description = reimb_description;
        this.reimb_type_fk = reimb_type_fk;
        this.reimb_status_fk = reimb_statuss_fk;

    }

    public int getReimb_id() {return reimb_id;}
    public void setReimb_id(int reimb_id) {this.reimb_id = reimb_id;}
    public int getReimb_amount() {return reimb_amount;}
    public void setReimb_amount(int reimb_amount) {this.reimb_amount = reimb_amount;}
    public String getReimb_description() {return reimb_description;}
    public void setReimb_description(String reimb_description) {this.reimb_description = reimb_description;}
    public int getReimb_status_fk() {return reimb_status_fk;}
    public void setReimb_status_fk(int reimb_status_fk) {this.reimb_status_fk = reimb_status_fk;}
    public int getReimb_type_fk() {return reimb_type_fk;}
    public void setReimb_type_fk(int reimb_type_fk) {this.reimb_type_fk = reimb_type_fk;}
    public Reimbursement_type getReimbursement_type() {return reimbursement_type;}
    public void setReimbursement_type(Reimbursement_type reimbursement_type) {this.reimbursement_type = reimbursement_type;}
    public Reimbursement_status getReimbursement_status() {return reimbursement_status;}
    public void setReimbursement_status(Reimbursement_status reimbursement_status) {this.reimbursement_status = reimbursement_status;}
    @Override
    public String toString() {
        return "Reimbursements{" +
                "reimb_id = " + reimb_id +
                ", reimb_amount ='" + reimb_amount + '\'' +
                ", reimb_description = '" + reimb_description + '\'' +
                ", status = " + reimbursement_status + '\'' +
                ", type = " + reimbursement_type +
                '}';
    }
}

