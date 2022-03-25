package repositories;

import models.Reimbursement;
import models.User;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAOImpl implements ReimbursementDAO{

    @Override
    public Boolean createReimbursement(Reimbursement reimbursement) { //DML
        try (Connection conn = ConnectionFactory.getConnection()){

            String sql = "insert into ers_reimbursement (reimb_amount, reimb_author_fk, reimb_type_id_fk) values (?,?,?);";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reimbursement.getAmount());
            ps.setInt(2, reimbursement.getAuthorId());
            ps.setInt(3, reimbursement.getTypeId()); // 1 -> Lodging, 2 -> Food, 3 -> Travel

            return ps.executeUpdate() != 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;

    }

    @Override
    public List<Reimbursement> getReimbursementsForAllUsers() { //DQL
        List<Reimbursement> reimbursementList = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "select * from readable_reimbursements_view order by reimb_id;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimbursementList.add(new Reimbursement(rs.getInt(1), // Reimbursement ID
                        rs.getInt(2),            // Reimbursement amount
                        rs.getTimestamp(3),      // Time Submitted
                        rs.getInt(4),            // User ID of the Author
                        rs.getString(5),         // Username of the Author
                        rs.getInt(6),            // Status ID
                        rs.getString(7),         // Status
                        rs.getInt(8),            // Type ID
                        rs.getString(9)          // Type
                ));
            }


        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return reimbursementList;
    }

    @Override
    public List<Reimbursement> getReimbursementsGivenUser(Integer userId) {
        List<Reimbursement> reimbursementList = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "select * from ers_reimbursement where reimb_author = ? order by reimb_id;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimbursementList.add(new Reimbursement(rs.getInt(1), // Reimbursement ID
                        rs.getInt(2),            // Reimbursement amount
                        rs.getTimestamp(3),      // Time Submitted
                        rs.getInt(4),            // User ID of the Author
                        rs.getString(5),         // Username of the Author
                        rs.getInt(6),            // Status ID
                        rs.getString(7),         // Status
                        rs.getInt(8),            // Type ID
                        rs.getString(9)          // Type
                ));
            }


        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return reimbursementList;

    }

    @Override
    public void changeReimbursementStatus(Integer reimbId, Integer newStatusId ) {
        try(Connection conn = ConnectionFactory.getConnection()){

            String sql = "update ers_reimbursement set reimb_status_id = ? where reimb_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newStatusId);
            ps.setInt(2, reimbId);

            ps.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    @Override
    public List<Reimbursement> getReimbursementsGivenStatusId(Integer statusId) {
        List<Reimbursement> reimbursementList = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "select * from ers_reimbursement where reimb_status_id = ? order by reimb_id;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, statusId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimbursementList.add(new Reimbursement(rs.getInt(1), // Reimbursement ID
                        rs.getInt(2),            // Reimbursement amount
                        rs.getTimestamp(3),      // Time Submitted
                        rs.getInt(4),            // User ID of the Author
                        rs.getString(5),         // Username of the Author
                        rs.getInt(6),            // Status ID
                        rs.getString(7),         // Status
                        rs.getInt(8),            // Type ID
                        rs.getString(9)          // Type
                ));
            }


        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return reimbursementList;
    }

    @Override
    public List<Reimbursement> getReimbursementsGivenTypeId(Integer statusId) {
        List<Reimbursement> reimbursementList = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()){
            String sql = "select * from ers_reimbursement where reimb_type_id = ? order by reimb_id;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, statusId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimbursementList.add(new Reimbursement(rs.getInt(1), // Reimbursement ID
                        rs.getInt(2),            // Reimbursement amount
                        rs.getTimestamp(3),      // Time Submitted
                        rs.getInt(4),            // User ID of the Author
                        rs.getString(5),         // Username of the Author
                        rs.getInt(6),            // Status ID
                        rs.getString(7),         // Status
                        rs.getInt(8),            // Type ID
                        rs.getString(9)          // Type
                ));
            }


        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return reimbursementList;
    }
}