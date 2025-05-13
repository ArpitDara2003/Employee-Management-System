package com.example.project_1.businessLogicLayer;

import com.example.project_1.dataAccessLayer.UpdateTask;
import com.example.project_1.dataModels.Task;
import org.hibernate.Session;
import com.example.project_1.utils.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class TasksLogic {

    private static final Logger logger = LoggerFactory.getLogger(TasksLogic.class);
    private static final Session session = HibernateUtil.getSession();

    public static List<Task> getTasks(Long companyId, Long employeeId) {
        try {
            return UpdateTask.getTasksByCompanyAndEmployee(companyId, employeeId, session);
        } catch (Exception e) {
            logger.error("Error fetching tasks", e);
            return List.of(); // Return an empty list in case of an error
        }
    }
}
