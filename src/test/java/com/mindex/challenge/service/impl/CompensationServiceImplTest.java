package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationReadUrl;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationReadUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateReadUpdate() {
        LocalDateTime effectiveDate = LocalDateTime.parse("2023-12-25T12:15:30");
        BigDecimal salary = new BigDecimal("12345");

        String unpaidEmployeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        Compensation testCompensation = new Compensation();
        testCompensation.setEffectiveDate(effectiveDate);
        testCompensation.setSalary(salary);
        testCompensation.setEmployeeId(unpaidEmployeeId);

        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        assertEquals(testCompensation, createdCompensation);

        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationReadUrl, Compensation.class, unpaidEmployeeId).getBody();
        assertEquals(createdCompensation, readCompensation);

        // Update checks
        readCompensation.setSalary(new BigDecimal("54321"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation updatedCompensation =
                restTemplate.exchange(compensationUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Compensation>(readCompensation, headers),
                        Compensation.class).getBody();

        assertEquals(readCompensation, updatedCompensation);
    }
}
