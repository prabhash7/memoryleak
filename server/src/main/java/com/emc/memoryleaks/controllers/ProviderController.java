package com.emc.memoryleaks.controllers;

import com.emc.edp4vcac.domain.EdpSystem;
import com.emc.memoryleaks.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProviderController {

    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    @Autowired
    private RepositoryService repoSvc;

    public ProviderController() {
        logger.debug("Starting ProviderController");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/provider")
    public List<EdpSystem> getProviderList() {
        logger.debug("get /providers");
        return repoSvc.findAllSystems();
    }

//    @PostMapping("/provider")
//    public EdpSystem createProvider() {
//        logger.debug("post /provider ");
//        return null;
//    }
}
