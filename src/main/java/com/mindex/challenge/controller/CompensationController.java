package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);

        return compensationService.create(compensation);
    }

    @GetMapping("/compensation/{id}")
    public Compensation getCompensation(@PathVariable String id) {
        LOG.debug("Received compensation load request for employeeId [{}]", id);

        return compensationService.read(id);
    }

    @PutMapping("/compensation")
    public Compensation update(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation update request with compensation info [{}]" , compensation);

        return compensationService.update(compensation);
    }
}
