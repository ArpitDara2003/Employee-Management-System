package com.example.project_1.businessLogicLayer;

import com.example.project_1.dataAccessLayer.UpdateLeaveBalance;
import com.example.project_1.dataModels.LeaveBalance;
import com.example.project_1.dataModels.UI.StatCard;
import com.example.project_1.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

public class LeaveBalanceLogic {

    static Session session = HibernateUtil.getSession();
    
    public static List<StatCard> getLeaveBalanceStats(Long employeeId, Long companyId) {
        LeaveBalance leaveBalance = UpdateLeaveBalance.getLeaveBalanceFromDB(session, employeeId, companyId);
        if (leaveBalance == null) {
            throw new IllegalArgumentException("Leave balance not found for the given employee and company ID");
        }

        List<StatCard> statCards = new ArrayList<>();

        statCards.add(new StatCard("fas fa-calendar-check", "green", "Annual Leave Remaining", String.valueOf(leaveBalance.getAnnualLeaveRemaining())));
        statCards.add(new StatCard("fas fa-heartbeat", "blue", "Sick Leave Remaining", String.valueOf(leaveBalance.getSickLeaveRemaining())));
        statCards.add(new StatCard("fas fa-coffee", "orange", "Casual Leave Remaining", String.valueOf(leaveBalance.getCasualLeaveRemaining())));
        statCards.add(new StatCard("fas fa-file-alt", "purple", "Leaves Applied", String.valueOf(leaveBalance.getAppliedLeaves())));
        statCards.add(new StatCard("fas fa-check-circle", "teal", "Leaves Approved", String.valueOf(leaveBalance.getApprovedLeaves())));

        return statCards;
    }


    public static void updateLeaveBalance(Session session, Long leaveId, LeaveBalance leave) {
        // Validate leave balance details
        if (leave.getAnnualLeaveTotal() < 0 || leave.getSickLeaveTotal() < 0 || leave.getCasualLeaveTotal() < 0) {
            throw new IllegalArgumentException("Invalid leave balance details");
        }

        if (leave.getCompany() == null) {
            throw new IllegalArgumentException("Company cannot be null");
        }

        // Call data access layer to update the leave balance
        UpdateLeaveBalance.updateLeaveBalance(session, leaveId, leave);
    }
}