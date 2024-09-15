package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Getting reportingStructure for employeeId [{}]", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        int totalHeadcount = totalUnderlingCount(employee);

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setNumberOfReports(totalHeadcount);
        reportingStructure.setEmployee(employee);
        return reportingStructure;
    }

    /**
     * Recursively counts the number of employees that fall under the management of a given Employee,
     * whether directly, or through following a chain of direct reports
     *
     * @param employee Employee to count the underlings for
     * @return the total number of employees that report under a given employee
     */
    private int totalUnderlingCount(Employee employee) {
        if (employee.getDirectReports() == null)
            return 0;

        //From the database, the directReports list only contains the Employee's employeeId
        //We need to get the full Employee to check if they have any direct reports of their own
        List<Employee> directReports = employee.getDirectReports()
                .stream()
                .map(employee1 -> employeeRepository.findByEmployeeId(employee1.getEmployeeId()))
                .toList();

        int reportCount = directReports.size();

        //Now count the employees under this employee, and the ones under them, and so on
        for(Employee subEmployee : directReports) {
            reportCount += totalUnderlingCount(subEmployee);
        }

        return reportCount;
    }
}
