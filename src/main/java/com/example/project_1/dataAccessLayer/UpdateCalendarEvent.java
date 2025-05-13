package com.example.project_1.dataAccessLayer;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.example.project_1.dataModels.CalendarEvent;

import java.util.List;

@Repository
public class UpdateCalendarEvent {

    // Method to get events using the session passed as an argument
    public static List<CalendarEvent> getCalendarEventsFromDB(Session session, Long companyId) {
        List<CalendarEvent> eventList = null;

        try {
            Query<CalendarEvent> query = session.createQuery(
                "SELECT c.title, c.date, c.company.companyId FROM CalendarEvent c WHERE c.company.companyId = :companyId",
                CalendarEvent.class
            );
            query.setParameter("companyId", companyId);
            eventList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return eventList != null ? eventList : List.of();
    }

    // Method to update calendar event using session passed as an argument
    public void updateCalendarEvent(Session session, CalendarEvent event) {
        if (event == null || event.getEmployee() == null || event.getCompany() == null || event.getDate() == null) {
            throw new IllegalArgumentException("Invalid event or missing required fields");
        }

        try {
            // Start transaction
            session.beginTransaction();

            // Use session's update method for Hibernate to manage the update
            session.update(event);

            // Commit the transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to update calendar event", e);
        }
    }
}
