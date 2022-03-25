package services;


import models.Reimbursement;
import models.User;
import repositories.ReimbursementDAO;
import repositories.ReimbursementDAOImpl;

import java.util.Collections;
import java.util.List;

public class ReimbursementService {
    ReimbursementDAO reimbDAO;

    public ReimbursementService() {
        this.reimbDAO = new ReimbursementDAOImpl();
    }

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbDAO = reimbursementDAO;
    }

    // -------- EMPLOYEE SERVICES
    public List<Reimbursement> getAllGivenUser(Integer userId){
        return this.reimbDAO.getReimbursementsGivenUser(userId);
    }

    public Reimbursement createReimbursement (Reimbursement reimbursementToCreate){
        if(this.reimbDAO.createReimbursement(reimbursementToCreate)){
            List<Reimbursement> reimbs = reimbDAO.getReimbursementsGivenUser(reimbursementToCreate.getAuthorId());
            return reimbs.get(reimbs.size()-1);
        }
        return null;
    }

    // --------- MANAGER SERVICES

    public List<Reimbursement> getAllForAllUsers(User currentUser){

        if (currentUser.isManager()){ // if the user is a manager
            return this.reimbDAO.getReimbursementsForAllUsers();
        } else {
            return null;
        }
    }


    public Boolean changeStatus(User currentUser, Integer reimbId, Integer newStatusId){
        if( currentUser == null){
            return false; // to handle null pointer exception
        }
        else if (currentUser.isManager()) {  // if the user is a manager
            this.reimbDAO.changeReimbursementStatus(reimbId, newStatusId);
            return true;
        } else { // user is not a manager and cannot change reimbursement status
            return false;
        }
    }

    public List<Reimbursement> filterByStatusId(User currentUser, Integer statusId){
        if (currentUser.isManager()){  // if the user is a manager
            return this.reimbDAO.getReimbursementsGivenStatusId(statusId);
        } else {
            return null;
        }
    }

    public List<Reimbursement> filterByTypeId(User currentUser, Integer typeId){
        if (currentUser.isManager()){  // if the user is a manager
            return this.reimbDAO.getReimbursementsGivenTypeId(typeId);
        } else {
            return null;
        }
    }

}
