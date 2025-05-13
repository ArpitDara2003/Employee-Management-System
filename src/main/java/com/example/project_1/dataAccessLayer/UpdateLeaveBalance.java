package com.example.project_1.dataAccessLayer;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.example.project_1.dataModels.LeaveBalance;
import jakarta.persistence.Tuple;

public class UpdateLeaveBalance {

    public static List<Tuple> getLeaveBalancesFromDB(Session session, Long employeeId, Long companyId) {
        List<Tuple> leaveList = null;

        try {
            Query<Tuple> query = session.createQuery(
                "SELECT l.leaveType, l.balance, l.employee.employeeId, l.company.id FROM LeaveBalance l WHERE l.employee.employeeId = :employeeId AND l.company.id = :companyId",
                Tuple.class
            );
            query.setParameter("employeeId", employeeId);
            query.setParameter("companyId", companyId);
            leaveList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return leaveList != null ? leaveList : List.of();
    }

    public static LeaveBalance getLeaveBalanceFromDB(Session session, Long employeeId, Long companyId) {
        LeaveBalance leaveBalance = null;

        try {
            Query<LeaveBalance> query = session.createQuery(
                "FROM LeaveBalance l WHERE l.employee.employeeId = :employeeId AND l.company.id = :companyId",
                LeaveBalance.class
            );
            query.setParameter("employeeId", employeeId);
            query.setParameter("companyId", companyId);
            leaveBalance = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return leaveBalance;
    }

    public static void updateLeaveBalance(Session session, Long leaveId, LeaveBalance leave) {
        try {
            session.beginTransaction();
            LeaveBalance existingLeave = session.get(LeaveBalance.class, leaveId);
            if (existingLeave != null) {
                existingLeave.setAnnualLeaveTotal(leave.getAnnualLeaveTotal());
                existingLeave.setAnnualLeaveUsed(leave.getAnnualLeaveUsed());
                existingLeave.setSickLeaveTotal(leave.getSickLeaveTotal());
                existingLeave.setSickLeaveUsed(leave.getSickLeaveUsed());
                existingLeave.setCasualLeaveTotal(leave.getCasualLeaveTotal());
                existingLeave.setCasualLeaveUsed(leave.getCasualLeaveUsed());
                existingLeave.setAppliedLeaves(leave.getAppliedLeaves());
                existingLeave.setApprovedLeaves(leave.getApprovedLeaves());
                existingLeave.setCompany(leave.getCompany());
                session.merge(existingLeave);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}