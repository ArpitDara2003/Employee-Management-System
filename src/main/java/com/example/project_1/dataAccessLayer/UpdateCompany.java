package com.example.project_1.dataAccessLayer;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.project_1.dataModels.Company;
import com.example.project_1.dataModels.Employee;
public class UpdateCompany {
 
    public static void addCompany(Company company, Employee user, Session session){
        
        Transaction transaction = null;
    
        try {
            transaction = session.beginTransaction();
    
            // Use persist instead of save
            session.persist(company);
            transaction.commit();
            

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        

    }

    public static void addCompanyUser(int company_id, Session session){
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            
            Company company = session.get(Company.class, company_id);
            if (company != null) {
                int currentNumberOfUsers = company.getNumberOfUsers();
                company.setNumberOfUsers(currentNumberOfUsers + 1);
                session.merge(company); // Use merge instead of update
                transaction.commit();
            }

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
