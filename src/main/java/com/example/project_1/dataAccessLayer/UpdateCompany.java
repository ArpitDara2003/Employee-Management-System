package com.example.project_1.dataAccessLayer;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.project_1.dataModels.Company;
import com.example.project_1.dataModels.Employee;
import jakarta.persistence.Tuple;

public class UpdateCompany {
 
    public static void addCompany(Company company, Employee user, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Persist the company entity
            session.persist(company);

            // Associate the user with the company
            user.setCompany(company);
            session.persist(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public static void addCompanyUser(int companyId, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Fetch the company by ID
            Company company = session.get(Company.class, companyId);
            if (company != null) {
                // Increment the number of users in the company
                int currentNumberOfUsers = company.getNumberOfUsers();
                company.setNumberOfUsers(currentNumberOfUsers + 1);

                // Merge the updated company entity
                session.merge(company);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public static Company getCompanyById(int companyId, Session session) {
        Transaction transaction = null;
        Company company = null;

        try {
            transaction = session.beginTransaction();

            // Fetch the company by ID
            company = session.get(Company.class, companyId);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return company;
    }

    public static void updateCompanyDetails(Company updatedCompany, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Fetch the existing company
            Company existingCompany = session.get(Company.class, updatedCompany.getCompanyId());
            if (existingCompany != null) {
                // Update the company details
                existingCompany.setCompanyName(updatedCompany.getCompanyName());
                existingCompany.setNumberOfUsers(updatedCompany.getNumberOfUsers());
                existingCompany.setUpdatedAt(updatedCompany.getUpdatedAt());

                // Merge the updated company entity
                session.merge(existingCompany);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public static List<Tuple> getCompaniesFromDB(Session session) {
        List<Tuple> companyList = null;

        try {
            Query<Tuple> query = session.createQuery(
                "SELECT c.companyName, c.companyId FROM Company c",
                Tuple.class
            );
            companyList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return companyList != null ? companyList : List.of();
    }

    public static void saveOrUpdateCompany(Company company, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Use merge to update or save the company
            session.merge(company);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public static void incrementCompanyUserCount(int companyId, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Fetch the company by ID
            Company company = session.get(Company.class, companyId);
            if (company != null) {
                // Increment the number of users in the company
                company.setNumberOfUsers(company.getNumberOfUsers() + 1);

                // Merge the updated company entity
                session.merge(company);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
