package com.example.project_1.businessLogicLayer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.example.project_1.dataAccessLayer.UpdateLeaveBalance;
import com.example.project_1.dataModels.LeaveBalance;
import com.example.project_1.dataModels.UI.StatCard;
import com.example.project_1.utils.HibernateUtil;

public class StatCardsLogic {
    
    static Session session = HibernateUtil.getSession();

    public static List<StatCard> getDashboardStats(Long employeeId, Long companyId) {
        List<StatCard> statCards = new ArrayList<>();

        // Fetching leave balance stats for "Annual Leave Remaining"
        getLeaveBalanceStats(employeeId, companyId).stream()
            .filter(statCard -> "Annual Leave Remaining".equals(statCard.getTitle()))
            .forEach(statCards::add);

        // Fetching calendar events stats
        statCards.add(new StatCard("fas fa-calendar-alt", "blue", "Upcoming Events", String.valueOf(DashboardLogic.getCalendarEvents(companyId).size())));

        // Fetching news items stats
        statCards.add(new StatCard("fas fa-newspaper", "green", "News Updates", String.valueOf(DashboardLogic.getNewsFromDB(companyId).size())));

        return statCards;
    }
    
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
}
