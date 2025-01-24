package com.example.project_1.dataAccessLayer;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.project_1.dataModels.Employee;

public class UpdateEmployee {

    public static void saveEmployeeToDB(Employee employee, Session session) {
        
        Transaction transaction = null;
    
        try {
            transaction = session.beginTransaction();

            session.persist(employee);
            transaction.commit();
    
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            
        }
    }    

    public static boolean isEmployeeAdmin(int company_id, int employee_id, Session session) {
        Employee employee = null;
    
        try {
            session.beginTransaction();
    
            String hql = "FROM lu_employee WHERE fk_company_id = :companyId AND pk_employee_id = :employeeId AND is_admin = 1";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            query.setParameter("companyId", company_id);
            query.setParameter("employeeId", employee_id);
    
            // Get the single result (or null if no match found)
            employee = query.uniqueResult();
    
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return employee != null;
    }

    public static Employee[] getEmployeesFromDB(int companyId, Session session) {
        List<Employee> employeeList = null;

        try {
            Query<Employee> query = session.createQuery("FROM lu_employee WHERE fk_company_id = :companyId", Employee.class);
            query.setParameter("companyId", companyId);
            employeeList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeeList != null ? employeeList.toArray(new Employee[0]) : new Employee[0];
    }


    public static Employee verifyAndReturnEmployee(int company_id,int employee_id, String password, Session session) {
        Employee employee = null;
    
        try {
            session.beginTransaction();
    
            String hql = "FROM lu_employee WHERE fk_company_id = :companyId AND pk_employee_id = :employeeId AND password = :password";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            query.setParameter("companyId", company_id);
            query.setParameter("employeeId", employee_id);
            query.setParameter("password", password); 
    
            // Get the single result (or null if no match found)
            employee = query.uniqueResult();
    
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return employee;
    }
    
    
}
    

