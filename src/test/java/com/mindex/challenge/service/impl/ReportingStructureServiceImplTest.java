package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingStructureUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;
    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reporting/{id}";
    }

    @Test
    public void testNoDirectReports() {
        String employeeId = "b7839309-3348-463b-a7e3-5de1c168beb3";
        Employee employee = employeeService.read(employeeId);
        int expectedCount = 0;

        ReportingStructure expectedReportingStructure = new ReportingStructure();
        expectedReportingStructure.setNumberOfReports(expectedCount);
        expectedReportingStructure.setEmployee(employee);

        ReportingStructure actualReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeId).getBody();

        assertReportingStructureEquivalence(expectedReportingStructure, actualReportingStructure);
    }

    @Test
    public void testOneLayerDirectReports() {
        String employeeId = "03aa1462-ffa9-4978-901b-7c001562cf6f";
        Employee employee = employeeService.read(employeeId);
        int expectedCount = 2;

        ReportingStructure expectedReportingStructure = new ReportingStructure();
        expectedReportingStructure.setNumberOfReports(expectedCount);
        expectedReportingStructure.setEmployee(employee);

        ReportingStructure actualReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeId).getBody();

        assertReportingStructureEquivalence(expectedReportingStructure, actualReportingStructure);
    }

    @Test
    public void testMultiLayerDirectReports() {
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        Employee employee = employeeService.read(employeeId);
        int expectedCount = 4;

        ReportingStructure expectedReportingStructure = new ReportingStructure();
        expectedReportingStructure.setNumberOfReports(expectedCount);
        expectedReportingStructure.setEmployee(employee);

        ReportingStructure actualReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeId).getBody();

        assertReportingStructureEquivalence(expectedReportingStructure, actualReportingStructure);
    }

    private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
        assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
        assertEquals(expected.getEmployee(), actual.getEmployee());
    }
}
