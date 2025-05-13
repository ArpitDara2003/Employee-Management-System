package com.example.project_1.dataModels;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_balance")
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_balance_id")
    private Long leaveBalanceId;

    @Column(name = "annual_leave_total", nullable = false)
    private int annualLeaveTotal;

    @Column(name = "annual_leave_used", nullable = false)
    private int annualLeaveUsed;

    @Column(name = "sick_leave_total", nullable = false)
    private int sickLeaveTotal;

    @Column(name = "sick_leave_used", nullable = false)
    private int sickLeaveUsed;

    @Column(name = "casual_leave_total", nullable = false)
    private int casualLeaveTotal;

    @Column(name = "casual_leave_used", nullable = false)
    private int casualLeaveUsed;

    @Column(name = "applied_leaves", nullable = false)
    private int appliedLeaves;

    @Column(name = "approved_leaves", nullable = false)
    private int approvedLeaves;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public LeaveBalance(int annualLeaveTotal, int annualLeaveUsed, int sickLeaveTotal, int sickLeaveUsed,
            int casualLeaveTotal, int casualLeaveUsed, int appliedLeaves, int approvedLeaves, Employee employee) {
        this.annualLeaveTotal = annualLeaveTotal;
        this.annualLeaveUsed = annualLeaveUsed;
        this.sickLeaveTotal = sickLeaveTotal;
        this.sickLeaveUsed = sickLeaveUsed;
        this.casualLeaveTotal = casualLeaveTotal;
        this.casualLeaveUsed = casualLeaveUsed;
        this.appliedLeaves = appliedLeaves;
        this.approvedLeaves = approvedLeaves;
        this.employee = employee;
    }

    public Long getLeaveBalanceId() {
        return leaveBalanceId;
    }

    public void setLeaveBalanceId(Long leaveBalanceId) {
        this.leaveBalanceId = leaveBalanceId;
    }

    public int getAnnualLeaveTotal() {
        return annualLeaveTotal;
    }

    public void setAnnualLeaveTotal(int annualLeaveTotal) {
        this.annualLeaveTotal = annualLeaveTotal;
    }

    public int getAnnualLeaveUsed() {
        return annualLeaveUsed;
    }

    public void setAnnualLeaveUsed(int annualLeaveUsed) {
        this.annualLeaveUsed = annualLeaveUsed;
    }

    public int getAnnualLeaveRemaining() {
        return annualLeaveTotal - annualLeaveUsed;
    }

    public int getSickLeaveTotal() {
        return sickLeaveTotal;
    }

    public void setSickLeaveTotal(int sickLeaveTotal) {
        this.sickLeaveTotal = sickLeaveTotal;
    }

    public int getSickLeaveUsed() {
        return sickLeaveUsed;
    }

    public void setSickLeaveUsed(int sickLeaveUsed) {
        this.sickLeaveUsed = sickLeaveUsed;
    }

    public int getSickLeaveRemaining() {
        return sickLeaveTotal - sickLeaveUsed;
    }

    public int getCasualLeaveTotal() {
        return casualLeaveTotal;
    }

    public void setCasualLeaveTotal(int casualLeaveTotal) {
        this.casualLeaveTotal = casualLeaveTotal;
    }

    public int getCasualLeaveUsed() {
        return casualLeaveUsed;
    }

    public void setCasualLeaveUsed(int casualLeaveUsed) {
        this.casualLeaveUsed = casualLeaveUsed;
    }

    public int getCasualLeaveRemaining() {
        return casualLeaveTotal - casualLeaveUsed;
    }

    public int getAppliedLeaves() {
        return appliedLeaves;
    }

    public void setAppliedLeaves(int appliedLeaves) {
        this.appliedLeaves = appliedLeaves;
    }

    public int getApprovedLeaves() {
        return approvedLeaves;
    }

    public void setApprovedLeaves(int approvedLeaves) {
        this.approvedLeaves = approvedLeaves;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}