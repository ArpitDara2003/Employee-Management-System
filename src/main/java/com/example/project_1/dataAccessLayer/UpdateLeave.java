package com.example.project_1.dataAccessLayer;

import org.hibernate.Session;
import org.hibernate.query.Query;
import com.example.project_1.dataModels.Leave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class UpdateLeave {

    private static final Logger logger = LoggerFactory.getLogger(UpdateLeave.class);

    // Save a leave record to the database
    public static void saveLeave(Session session, Leave leave) {
        try {
            session.beginTransaction();
            session.persist(leave);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.error("Error saving leave record", e);
        }
    }

    // Update an existing leave record
    public static void updateLeave(Session session, Long leaveId, Leave updatedLeave) {
        try {
            session.beginTransaction();
            Leave existingLeave = session.get(Leave.class, leaveId);
            if (existingLeave != null) {
                existingLeave.setLeaveType(updatedLeave.getLeaveType());
                existingLeave.setStartDate(updatedLeave.getStartDate());
                existingLeave.setEndDate(updatedLeave.getEndDate());
                existingLeave.setStatus(updatedLeave.getStatus());
                existingLeave.setReason(updatedLeave.getReason());
                session.merge(existingLeave);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.error("Error updating leave record", e);
        }
    }

    // Fetch leave records by employee and company
    public static List<Leave> getLeavesByEmployeeAndCompany(Session session, Long employeeId, Long companyId) {
        List<Leave> leaveList = null;
        try {
            Query<Leave> query = session.createQuery(
                "FROM Leave l WHERE l.employee.employeeId = :employeeId AND l.company.companyId = :companyId",
                Leave.class
            );
            query.setParameter("employeeId", employeeId);
            query.setParameter("companyId", companyId);
            leaveList = query.getResultList();
        } catch (Exception e) {
            logger.error("Error fetching leave records", e);
        }
        return leaveList != null ? leaveList : List.of();
    }
}
