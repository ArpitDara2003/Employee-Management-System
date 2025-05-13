package com.example.project_1.dataAccessLayer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.project_1.dataModels.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UpdateEmployee {

    private static final Logger logger = LoggerFactory.getLogger(UpdateEmployee.class);

    public static void saveEmployeeToDB(Employee employee, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error saving employee to database", e);
        }
    }

    public static boolean isEmployeeAdmin(long companyId, long employeeId, Session session) {
        Employee employee = null;

        try {
            session.beginTransaction();
            String hql = "FROM Employee e WHERE e.company.companyId = :companyId AND e.id = :employeeId AND e.position = 'Admin'";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            query.setParameter("companyId", companyId);
            query.setParameter("employeeId", employeeId);

            employee = query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Error checking if employee is admin", e);
        }

        return employee != null;
    }

    public static List<Employee> getEmployeesFromDB(long companyId, Session session) {
        List<Employee> employeeList = null;

        try {
            String hql = "FROM Employee e WHERE e.company.companyId = :companyId";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            query.setParameter("companyId", companyId);
            employeeList = query.getResultList();
        } catch (Exception e) {
            logger.error("Error fetching employees from database", e);
        }

        return employeeList;
    }

    public static void updateEmployee(long employeeId, Employee employee, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Employee existingEmployee = session.get(Employee.class, employeeId);
            if (existingEmployee != null) {
                existingEmployee.setName(employee.getName());
                existingEmployee.setLastName(employee.getLastName());
                existingEmployee.setCompany(employee.getCompany());
                existingEmployee.setManagerEmployeeId(employee.getManagerEmployeeId());
                session.merge(existingEmployee);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error updating employee", e);
        }
    }

    public static Employee verifyAndReturnEmployee(long companyId, long employeeId, String password, Session session) {
        Employee employee = null;

        try {
            session.beginTransaction();
            String hql = "FROM Employee e WHERE e.company.companyId = :companyId AND e.id = :employeeId AND e.password = :password";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            query.setParameter("companyId", companyId);
            query.setParameter("employeeId", employeeId);
            query.setParameter("password", password);

            employee = query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Error verifying employee", e);
        }

        return employee;
    }
}


