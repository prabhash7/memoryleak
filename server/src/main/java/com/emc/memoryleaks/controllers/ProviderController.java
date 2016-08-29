package com.emc.memoryleaks.controllers;

import com.emc.edp4vcac.domain.EdpFeature;
import com.emc.edp4vcac.domain.EdpNotificationHandler;
import com.emc.edp4vcac.domain.EdpRepository;
import com.emc.edp4vcac.domain.EdpSystem;
import com.emc.edp4vcac.domain.spi.DataProtectionProviderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RestController
public class ProviderController {

    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    private final EdpRepository edpRepository;

    ProviderController() {
        logger.debug("Starting ProviderController");

        EdpNotificationHandler notificationHandler = new EdpNotificationHandler() {
            @Override
            public void notifyElementUpdated(EntityType entityType, String s) {

            }

            @Override
            public void notifyElementDeleted(EntityType entityType, String s) {

            }

            @Override
            public void notifyElementInvalidate(EntityType entityType, String s) {

            }
        };

        Map<EdpFeature,Boolean> licensedFeatures = new HashMap<>();

        // TODO: need to determine how to shutdown cleanly
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        edpRepository = new EdpRepository(notificationHandler, licensedFeatures, executorService);

//        DataProtectionProviderConfig dpProviderConfig = new DataProtectionProviderConfig()
//        edpRepository.addSystem("daves-avamar", dpProviderConfig, true);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/provider")
    public List<EdpSystem> getProviderList() {
        logger.debug("/providers request");
        return edpRepository.findAllSystems();
    }

    @PostMapping("/provider")
    public EdpSystem createProvider() {
        return null;
    }
}
