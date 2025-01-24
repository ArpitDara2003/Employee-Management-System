package com.example.project_1.businessLogicLayer;

import org.hibernate.Session;

import com.example.project_1.dataModels.News;
import com.example.project_1.dataModels.Employee;
import com.example.project_1.dataAccessLayer.UpdateEmployee;
import com.example.project_1.dataAccessLayer.UpdateNews;
import com.example.project_1.dataAccessLayer.UpdateComapny;
import com.example.project_1.dataAccessLayer.UpdateWork;

public class HomePage {


    public static News[] getNewsFromDB(Session session, Long companyId) {
        retrun getNewsFromDB(Session session, Long companyId);
    }

    public static boolean isAdmin(Long employeeId, Long companyId, Session session){
        return UpdateEmployee.isEmployeeAdmin(companyId.intValue(), employeeId.intValue(), session);
    }

    public static boolean authentication(String username, String password,long companyId, Session session) {
        Employee employee= verifyAndReturnEmployee(username, password, companyId, session);
    }
}
 
                                                          