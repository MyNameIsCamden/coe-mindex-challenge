package com.mindex.challenge.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Compensation {
    private BigDecimal salary;
    private LocalDateTime effectiveDate;
    private String employeeId;

    public Compensation() {
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compensation that = (Compensation) o;
        return Objects.equals(getSalary(), that.getSalary())
                && Objects.equals(getEffectiveDate(), that.getEffectiveDate())
                && Objects.equals(getEmployeeId(), that.getEmployeeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSalary(), getEffectiveDate(), getEmployeeId());
    }

    @Override
    public String toString() {
        return "Compensation{" +
                "salary=" + salary +
                ", effectiveDate=" + effectiveDate +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }

}
