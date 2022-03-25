package models;

import java.sql.Timestamp;

public class Reimbursement {
    private Integer reimbId;
    private Integer amount;
    private Timestamp timeSubmitted;
    private Integer authorId;  // foreign key for user id of reimbursement author
    private String authorUsername;
    private Integer statusId;  // 1 -> Pending, 2 -> Approved, 3 -> Denied
    private String status;
    private Integer typeId;    // 1 -> Lodging, 2 -> Food, 3 -> Travel
    private String type;

    public Reimbursement(Integer reimbId, Integer amount, Timestamp timeSubmitted, Integer authorId, String authorUsername, Integer statusId, String status, Integer typeId, String type) {
        this.reimbId = reimbId;
        this.amount = amount;
        this.timeSubmitted = timeSubmitted;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.statusId = statusId;
        this.status = status;
        this.typeId = typeId;
        this.type = type;
    }

    public Reimbursement() {
    }

    // Constructor used to create reimbursements
    public Reimbursement(Integer amount, Integer authorId, Integer typeId) {
        this.amount = amount;
        this.authorId = authorId;
        this.typeId = typeId;
    }

    public Integer getReimbId() {
        return reimbId;
    }

    public void setReimbId(Integer reimbId) {
        this.reimbId = reimbId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Timestamp getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(Timestamp timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbId=" + reimbId +
                ", amount=" + amount +
                ", timeSubmitted=" + timeSubmitted +
                ", authorId=" + authorId +
                ", authorUsername='" + authorUsername + '\'' +
                ", statusId=" + statusId +
                ", status='" + status + '\'' +
                ", typeId=" + typeId +
                ", type='" + type + '\'' +
                '}';
    }
}