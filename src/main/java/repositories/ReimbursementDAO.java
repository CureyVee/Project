package repositories;

import models.Reimbursement;
import models.User;

import java.util.List;

public interface  ReimbursementDAO {
    public Boolean createReimbursement (Reimbursement reimbursement); // used to create new reimbursements
    public List<Reimbursement> getReimbursementsForAllUsers(); // manager accessible only
    public List<Reimbursement> getReimbursementsGivenUser(Integer userId); // allows regular users to see their past tickets
    public void changeReimbursementStatus(Integer reimbId, Integer newStatusId); // approving or denying requests
    public List<Reimbursement> getReimbursementsGivenStatusId(Integer statusId);
    public List<Reimbursement> getReimbursementsGivenTypeId(Integer statusId);

}