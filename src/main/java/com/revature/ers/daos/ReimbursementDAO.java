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
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ers_reimbursements " +
                    "(reimb_id, amount, submitted, description, payment_id, author_id, status_id, type_id) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setDouble(2, obj.getAmount());
            ps.setTimestamp(3, new Timestamp(obj.getSubmitted().getTime()));
            ps.setString(4, obj.getDescription());
            ps.setString(5, obj.getPaymentId());
            ps.setString(6, obj.getAuthorId());
            ps.setString(7, obj.getStatusId());
            ps.setString(8, obj.getTypeId());
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
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE ers_reimbursements SET resolved = ?, " +
                    "resolver_id = ?, status_id = ? WHERE reimb_id = ?");
            ps.setTimestamp(1, new Timestamp(obj.getResolved().getTime()));
            ps.setString(2, obj.getResolverId());
            ps.setString(3, obj.getStatusId());
            ps.setString(4, obj.getId());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reimbursement findByID(String id) {
        Reimbursement reimbursement = null;

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursements WHERE reimb_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                reimbursement = new Reimbursement(rs.getString("reimb_id"),
                        rs.getDouble("amount"), rs.getDate("submitted"),
                        rs.getDate("resolved"), rs.getString("description"),
                        rs.getString("payment_id"), rs.getString("author_id"),
                        rs.getString("resolver_id"), rs.getString("status_id"),
                        rs.getString("type_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursement;
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
                        rs.getString("payment_id"), rs.getString("author_id"),
                        rs.getString("resolver_id"), rs.getString("status_id"),
                        rs.getString("type_id"));
                reimbursements.add(currentReimb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursements;
    }

    // custom methods
    public List<Reimbursement> findAllByAuthorId(String authorId) {
        List<Reimbursement> reimbursements = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursements WHERE author_id = ?");
            ps.setString(1, authorId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Reimbursement reimbursement = new Reimbursement(rs.getString("reimb_id"),
                        rs.getDouble("amount"), rs.getDate("submitted"),
                        rs.getDate("resolved"), rs.getString("description"),
                        rs.getString("payment_id"), rs.getString("author_id"),
                        rs.getString("resolver_id"), rs.getString("status_id"),
                        rs.getString("type_id"));

                reimbursements.add(reimbursement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursements;
    }

    public List<Reimbursement> findAllByStatusId(String statusId) {
        List<Reimbursement> reimbursements = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursements WHERE status_id = ?");
            ps.setString(1, statusId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Reimbursement reimbursement = new Reimbursement(rs.getString("reimb_id"),
                        rs.getDouble("amount"), rs.getDate("submitted"),
                        rs.getDate("resolved"), rs.getString("description"),
                        rs.getString("payment_id"), rs.getString("author_id"),
                        rs.getString("resolver_id"), rs.getString("status_id"),
                        rs.getString("type_id"));

                reimbursements.add(reimbursement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursements;
    }
}
