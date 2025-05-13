package com.example.project_1.businessLogicLayer;

import com.example.project_1.dataAccessLayer.UpdateCalendarEvent;
import com.example.project_1.dataModels.CalendarEvent;
import jakarta.persistence.Tuple;
import org.hibernate.Session;

import java.util.List;

public class CalendarEventLogic {

    public static List<Tuple> getCalendarEvents(Session session, Long companyId) {
        // Add business logic validations if needed
        return UpdateCalendarEvent.getCalendarEventsFromDB(session, companyId);
    }

    public static void updateCalendarEvent(Long eventId, CalendarEvent event) {
        // Validate event details
        if (event.getDate() == null || event.getTitle() == null || event.getStartTime() == null) {
            throw new IllegalArgumentException("Invalid event details");
        }

        // Call data access layer to update the event
        UpdateCalendarEvent.updateCalendarEvent(eventId, event);
    }
}