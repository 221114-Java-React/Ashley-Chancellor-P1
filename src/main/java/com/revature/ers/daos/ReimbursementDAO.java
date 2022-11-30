package com.revature.ers.daos;

import com.revature.ers.models.Reimbursement;
import com.revature.ers.models.User;
import com.revature.ers.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// purpose: return reimb data from db
public class ReimbursementDAO implements CrudDAO<Reimbursement> {

    @Override
    public void save(Reimbursement obj) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ers_users (reimb_id, amount, submitted, " +
                    "resolved, description, receipt, payment_id, author_id, resolver_id, status_id, type_id) VALUES (" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setDouble(2, obj.getAmount());
            ps.setTimestamp(3, new Timestamp(obj.getSubmitted().getTime()));
            ps.setTimestamp(4, new Timestamp(obj.getResolved().getTime()));
            ps.setString(5, obj.getDescription());
            ps.setString(6, obj.getReceipt());
            ps.setString(7, obj.getPaymentId());
            ps.setString(8, obj.getAuthorId());
            ps.setString(9, obj.getResolverId());
            ps.setString(10, obj.getStatusId());
            ps.setString(11, obj.getTypeId());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Reimbursement obj) {

    }

    @Override
    public void update(Reimbursement obj) {

    }

    @Override
    public Reimbursement findByID() {
        return null;
    }

    @Override
    public List<Reimbursement> findAll() {
        List<Reimbursement> reimbursements = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursements");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Reimbursement currentReimb = new Reimbursement(rs.getString("reimb_id"),
                        rs.getDouble("amount"), rs.getDate("submitted"),
                        rs.getDate("resolved"), rs.getString("description"),
                        rs.getString("receipt"), rs.getString("payment_id"),
                        rs.getString("author_id"), rs.getString("resolver_id"),
                        rs.getString("status_id"), rs.getString("type_id"));
                reimbursements.add(currentReimb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursements;
    }
}
