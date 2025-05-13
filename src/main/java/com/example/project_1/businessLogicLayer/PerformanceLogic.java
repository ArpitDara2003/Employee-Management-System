package com.example.project_1.businessLogicLayer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.example.project_1.dataAccessLayer.UpdatePerformance;
import com.example.project_1.dataModels.PerformanceMetrics;
import com.example.project_1.dataModels.UI.TeamMember;
import com.example.project_1.utils.HibernateUtil;

public class PerformanceLogic {
    
    static Session session = HibernateUtil.getSession();

    public static Map<String, Integer> getEfficiencyMetric(Long employeeId, Long companyId) {
        List<PerformanceMetrics> allMetrics = UpdatePerformance.getPerformanceMetricsByEmployeeAndCompany(session, employeeId, companyId);
        Map<String, Integer> teamPerformance = new LinkedHashMap<>();
        for (PerformanceMetrics metric : allMetrics) {
            if ("Efficiency".equals(metric.getTitle())) {
                teamPerformance.put(metric.getTitle(), metric.getValue());
                break;
            }
        }
        return teamPerformance;
    }

    public static Map<String, Integer> getMonthlyMetric(Long employeeId, Long companyId) {
        List<PerformanceMetrics> allMetrics = UpdatePerformance.getPerformanceMetricsByEmployeeAndCompany(session, employeeId, companyId);
        Map<String, Integer> teamPerformance = new LinkedHashMap<>();
        for (PerformanceMetrics metric : allMetrics) {
            if (!"Efficiency".equals(metric.getTitle())) {
                teamPerformance.put(metric.getTitle(), metric.getValue());
            }
        }
        return teamPerformance;
    }

    public static Map<String, Integer> getTeamMetric(Long employeeId, Long companyId) {
        List<TeamMember> teamMembers = EmployeeLogic.getUserTeamMates(employeeId, companyId);
        List<TeamMember> juniorMembers = EmployeeLogic.getUserJuniorMates(companyId, employeeId);

        // Combine team and junior members
        List<TeamMember> allMembers = new ArrayList<>();
        allMembers.addAll(teamMembers);
        allMembers.addAll(juniorMembers);

        Map<String, Integer> teamPerformance = new LinkedHashMap<>();

        for (TeamMember member : allMembers) {
            List<PerformanceMetrics> metrics = UpdatePerformance.getPerformanceMetricsByEmployeeAndCompany(session, Long.parseLong(member.getEmployeeId()), companyId);

            for (PerformanceMetrics metric : metrics) {
                if ("Efficiency".equalsIgnoreCase(metric.getTitle())) {
                    teamPerformance.put(member.getName(), metric.getValue());
                    break;
                }
            }
        }

        return teamPerformance;
    }

}
