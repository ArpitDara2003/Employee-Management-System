package com.example.project_1.businessLogicLayer;



import org.hibernate.Session;

import java.util.List;

import jakarta.persistence.Tuple;

import com.example.project_1.dataAccessLayer.UpdateCalendarEvent;
import com.example.project_1.dataAccessLayer.UpdateEmployee;
import com.example.project_1.dataAccessLayer.UpdateLeaveBalance;
import com.example.project_1.dataAccessLayer.UpdateNews;
import com.example.project_1.dataModels.CalendarEvent;
import com.example.project_1.dataModels.Employee;
import com.example.project_1.dataModels.NewsItems;
import com.example.project_1.utils.HibernateUtil;

public class DashboardLogic {

    static Session session = HibernateUtil.getSession();

    

    public static boolean isAdmin(Long employeeId, Long companyId){
        return UpdateEmployee.isEmployeeAdmin(companyId.intValue(), employeeId.intValue(), session);
    }

    public static List<CalendarEvent> getCalendarEvents(Long companyId) {
        return UpdateCalendarEvent.getCalendarEventsFromDB(session, companyId);
    }

    public static List<NewsItems> getNewsFromDB(Long companyId) {
        return UpdateNews.getNewsFromDB(session, companyId);
    }

    public static List<Tuple> getLeaveBalancesFromDB(Long employeeId, Long companyId) {
        return UpdateLeaveBalance.getLeaveBalancesFromDB(session, employeeId, companyId);
    }     
}

