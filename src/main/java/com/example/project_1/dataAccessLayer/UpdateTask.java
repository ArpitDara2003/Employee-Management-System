package com.example.project_1.dataAccessLayer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.example.project_1.dataModels.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class UpdateTask {

    private static final Logger logger = LoggerFactory.getLogger(UpdateTask.class);

    public static void saveTaskToDB(Task task, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error saving task to database", e);
        }
    }

    public static void updateTask(Long taskId, Task updatedTask, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Task existingTask = session.get(Task.class, taskId);
            if (existingTask != null) {
                existingTask.setTitle(updatedTask.getTitle());
                existingTask.setDescription(updatedTask.getDescription());
                existingTask.setComplexity(updatedTask.getComplexity());
                existingTask.setTimeSpent(updatedTask.getTimeSpent());
                existingTask.setEstimatedTimeRequired(updatedTask.getEstimatedTimeRequired());
                existingTask.setStatus(updatedTask.getStatus());
                existingTask.setDueDate(updatedTask.getDueDate());
                existingTask.setEmployee(updatedTask.getEmployee());
                existingTask.setCompany(updatedTask.getCompany());
                session.merge(existingTask);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error updating task", e);
        }
    }

    public static List<Task> getTasksByCompanyAndEmployee(Long companyId, Long employeeId, Session session) {
        List<Task> taskList = null;
        try {
            String hql = "FROM Task t WHERE t.company.companyId = :companyId AND t.employee.id = :employeeId";
            Query<Task> query = session.createQuery(hql, Task.class);
            query.setParameter("companyId", companyId);
            query.setParameter("employeeId", employeeId);
            taskList = query.getResultList();
        } catch (Exception e) {
            logger.error("Error fetching tasks by company and employee", e);
        }
        return taskList;
    }

    public static void deleteTask(Long taskId, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Task task = session.get(Task.class, taskId);
            if (task != null) {
                session.remove(task);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error deleting task", e);
        }
    }
}
