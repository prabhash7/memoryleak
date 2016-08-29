package com.emc.memoryleaks.controllers;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emc.edp4vcac.domain.EdpPolicy;
import com.emc.memoryleaks.beans.Policy;
import com.emc.memoryleaks.service.RepositoryService;

@RestController
public class PoliciesController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private RepositoryService repSvc;
//    Policy conver(EdpPolicy edpPolicy) {
//    	
//    }
    @CrossOrigin(origins = "*") 
    @RequestMapping("/policies")
    public ArrayList<Policy> greeting(@RequestParam(value="name", defaultValue="World") String name) {
//    	repSvc.findAllSystems().forEach(system -> {
//    		system.findAllPolicies().stream().map(mapper);
//    	});
    	Policy policy1 = new Policy(counter.incrementAndGet(),
                          String.format(template, name),
                          "ABrandNewDataset");
    	Policy policy2 = new Policy(counter.incrementAndGet(),
                String.format(template, name),
                "ADifferenctDataSet");
    	ArrayList<Policy> policies = new ArrayList<Policy>();
    	policies.add(policy1);
    	policies.add(policy2);
        return policies;
    }
}
