package com.emc.memoryleaks.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emc.memoryleaks.beans.Policy;
import com.emc.memoryleaks.service.RepositoryService;

@RestController
public class PoliciesController {
    
    @Autowired
    private RepositoryService repSvc;
    

    @CrossOrigin(origins = "*") 
    @RequestMapping("/policies")
    public List<Policy> greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	
    	return repSvc.findAllSystems().stream().flatMap(system -> {
    		 return  system.findAllPolicies()
    				.stream()
    				.map(Policy::convert);
    		}).collect(Collectors.toList());

    }
    
}
