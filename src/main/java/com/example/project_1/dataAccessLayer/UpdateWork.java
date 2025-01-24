package com.example.project_1.dataAccessLayer;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.project_1.dataModels.Work;

public class UpdateWork {
    public static void addWork(Work work, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Use persist instead of save
            session.persist(work);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public static void updateWork(long workID, Work newWork, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Remove the existing work with the provided workID
            Work existingWork = session.get(Work.class, workID);
            if (existingWork != null) {
                session.remove(existingWork);
            }

            // Add the new work with the same workID
            newWork.setId(workID); // Set the ID of the new work to the old work's ID
            session.persist(newWork);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public static void deleteWork(long workID, Session session) {
        Work work = session.get(Work.class, workID);
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Use delete to remove the work
            session.remove(work);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }
    
    
}
