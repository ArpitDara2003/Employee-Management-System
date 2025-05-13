package com.example.project_1.businessLogicLayer;

import java.util.List;

import org.hibernate.Session;

import com.example.project_1.dataAccessLayer.UpdateEmployee;
import com.example.project_1.dataModels.Employee;
import com.example.project_1.dataModels.UI.TeamMember;
import com.example.project_1.utils.HibernateUtil;


public class EmployeeLogic {

    static Session session = HibernateUtil.getSession();

    public static void saveEmployee(Employee employee, Session session) {
        // Validate employee details
        if (employee.getName() == null || employee.getCompany() == null || employee.getManagerEmployeeId() == null) {
            throw new IllegalArgumentException("Invalid employee details");
        }

        // Call data access layer to save the employee
        UpdateEmployee.saveEmployeeToDB(employee, session);
    }

    public static List<Employee> getEmployeesFromDB(Long companyId) {
        return UpdateEmployee.getEmployeesFromDB(companyId.intValue(), session);
    }

    public static TeamMember getTeamMemberFromDB(Long companyId, Long employeeId) {
        Employee teamMember = getEmployeeFromDB(companyId, employeeId);
        return new TeamMember(
            String.valueOf(teamMember.getId()),
            teamMember.getName(),
            teamMember.getPosition(),
            teamMember.getEmail(),
            teamMember.getPhoneNumber(),
            teamMember.getProfileImage(),
            teamMember.getStatus());
    }

    public static Employee getEmployeeFromDB(Long companyId, Long employeeId) {
        List<Employee> allEmployees = getEmployeesFromDB(companyId);
        Employee user = allEmployees.stream()
            .filter(employee -> employee.getId() == employeeId)
            .findFirst()
            .orElse(null);
        return user;
    }

    public static TeamMember getMangerFromDB(Long companyId, Long employeeId) {
        Employee user = getEmployeeFromDB(companyId, employeeId);
        Employee manager = getEmployeeFromDB(companyId, user.getManagerEmployeeId());

        return new TeamMember(
            String.valueOf(manager.getId()),
            manager.getName(),
            manager.getPosition(),
            manager.getEmail(),
            manager.getPhoneNumber(),
            manager.getProfileImage(),
            manager.getStatus());
    }

    public static List<TeamMember> getUserTeamMates(Long companyId, Long employeeId) {
       
        Employee user = getEmployeeFromDB(companyId, employeeId);
        List<Employee> teamMates = getEmployeesFromDB(companyId).stream()
            .filter(employee -> employee.getManagerEmployeeId() != null && employee.getManagerEmployeeId().equals(user.getManagerEmployeeId()))
            .toList();

        List<TeamMember> teamMatesList = teamMates.stream()
            .map(employee -> new TeamMember(
                String.valueOf(employee.getId()),
                employee.getName(),
                employee.getPosition(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getProfileImage(),
                employee.getStatus()))
            .toList();
        return teamMatesList;
    }

    public static List<TeamMember> getUserJuniorMates(Long companyId, Long employeeId) {
        List<Employee> juniorMates = getEmployeesFromDB(companyId).stream()
            .filter(employee -> employee.getManagerEmployeeId() != null && employee.getManagerEmployeeId().equals(employeeId))
            .toList();

        List<TeamMember> teamMatesList = juniorMates.stream()
            .map(employee -> new TeamMember(
                String.valueOf(employee.getId()),
                employee.getName(),
                employee.getPosition(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getProfileImage(),
                employee.getStatus()))
            .toList();
        return teamMatesList;
    }
}