package com.example.project_1.businessLogicLayer;

import com.example.project_1.dataAccessLayer.UpdateCompany;
import com.example.project_1.dataModels.Company;
import com.example.project_1.dataModels.Employee;
import com.example.project_1.utils.HibernateUtil;
import jakarta.persistence.Tuple;

import java.util.List;

import org.hibernate.Session;

public class CompanyLogic {

    static Session session = HibernateUtil.getSession();

    public static void addCompany(Company company, Employee user) {
        // Validate company details
        if (company.getCompanyName() == null || user == null) {
            throw new IllegalArgumentException("Invalid company or user details");
        }

        // Call data access layer to add the company
        UpdateCompany.addCompany(company, user, session);
    }

    public static void addCompanyUser(int companyId) {
        // Call data access layer to add a user to the company
        UpdateCompany.addCompanyUser(companyId, session);
    }

    public static Company getCompanyById(int companyId) {
        // Call data access layer to fetch the company by ID
        return UpdateCompany.getCompanyById(companyId, session);
    }

    public static void updateCompanyDetails(Company updatedCompany) {
        // Validate updated company details
        if (updatedCompany.getCompanyName() == null) {
            throw new IllegalArgumentException("Invalid company details");
        }

        // Call data access layer to update the company details
        UpdateCompany.updateCompanyDetails(updatedCompany, session);
    }

    public static List<Tuple> getCompanies() {
        // Call data access layer to fetch all companies
        return UpdateCompany.getCompaniesFromDB(session);
    }

    public static void saveOrUpdateCompany(Company company) {
        // Validate company details
        if (company.getCompanyName() == null) {
            throw new IllegalArgumentException("Invalid company details");
        }

        // Call data access layer to save or update the company
        UpdateCompany.saveOrUpdateCompany(company, session);
    }
}
