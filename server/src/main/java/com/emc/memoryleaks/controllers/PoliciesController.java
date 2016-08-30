package com.emc.memoryleaks.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    

    @CrossOrigin(origins = "*") 
    @RequestMapping("/policies")
    public List<Policy> greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	
    	return repSvc.findAllSystems().stream().flatMap(system -> {
    		 return  system.findAllPolicies()
    				.stream()
    				.map(PoliciesController::convert);
    		}).collect(Collectors.toList());

    }
    
    public static Policy convert(EdpPolicy edpPolicy) {
    	
    	return new Policy(edpPolicy.getId(), null, null, edpPolicy.getDisplayName());
    	
    }
}
