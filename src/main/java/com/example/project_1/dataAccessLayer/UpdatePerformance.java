package com.example.project_1.dataAccessLayer;

import org.hibernate.Session;
import org.hibernate.query.Query;
import com.example.project_1.dataModels.PerformanceMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class UpdatePerformance {

    private static final Logger logger = LoggerFactory.getLogger(UpdatePerformance.class);

    // Save a performance metric to the database
    public static void savePerformanceMetric(Session session, PerformanceMetrics metric) {
        try {
            session.beginTransaction();
            session.persist(metric);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.error("Error saving performance metric", e);
        }
    }

    // Update an existing performance metric
    public static void updatePerformanceMetric(Session session, Long metricId, PerformanceMetrics updatedMetric) {
        try {
            session.beginTransaction();
            PerformanceMetrics existingMetric = session.get(PerformanceMetrics.class, metricId);
            if (existingMetric != null) {
                existingMetric.setTitle(updatedMetric.getTitle());
                existingMetric.setValue(updatedMetric.getValue());
                existingMetric.setMaxValue(updatedMetric.getMaxValue());
                session.merge(existingMetric);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.error("Error updating performance metric", e);
        }
    }

    // Fetch performance metrics by employee and company
    public static List<PerformanceMetrics> getPerformanceMetricsByEmployeeAndCompany(Session session, Long employeeId, Long companyId) {
        List<PerformanceMetrics> metricsList = null;
        try {
            Query<PerformanceMetrics> query = session.createQuery(
                "FROM PerformanceMetrics p WHERE p.employee.employeeId = :employeeId AND p.company.companyId = :companyId",
                PerformanceMetrics.class
            );
            query.setParameter("employeeId", employeeId);
            query.setParameter("companyId", companyId);
            metricsList = query.getResultList();
        } catch (Exception e) {
            logger.error("Error fetching performance metrics", e);
        }
        return metricsList != null ? metricsList : List.of();
    }
}
