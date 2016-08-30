package com.emc.memoryleaks.service;

import com.emc.edp4vcac.domain.EdpNotificationHandler;
import com.emc.edp4vcac.domain.EdpPolicy;
import com.emc.edp4vcac.domain.EdpRepository;
import com.emc.edp4vcac.domain.EdpSystem;
import com.emc.edp4vcac.domain.model.EdpException;
import com.emc.edp4vcac.domain.spi.DataProtectionProviderConfig;
import com.emc.edp4vcac.domain.spi.DataProtectionProviderConfig.DeploymentConfigurationType;
import com.emc.edp4vcac.domain.spi.DataProtectionProviderConfig.Type;
import com.emc.memoryleaks.beans.CreateSystemDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;

@Service
public class RefactoryServiceImpl implements RepositoryService {
    private static final Logger logger = LoggerFactory.getLogger(RefactoryServiceImpl.class);

    private final EdpNotificationHandler handler = new EdpNotificationHandler() {
        @Override
        public void notifyElementUpdated(EntityType arg0, String arg1) {
        }

        @Override
        public void notifyElementInvalidate(EntityType arg0, String arg1) {
        }

        @Override
        public void notifyElementDeleted(EntityType arg0, String arg1) {
        }
    };

    private final EdpRepository edpRepo = new EdpRepository(handler, new HashMap(), Executors.newSingleThreadScheduledExecutor());

    public RefactoryServiceImpl() {
        loadSavedEdpSystems();
    }

    private void loadSavedEdpSystems() {
        Properties savedSystems = new Properties();
        try (final InputStream is = getClass().getClassLoader().getResourceAsStream("saved-systems.properties")) {
            try (final InputStreamReader inputStreamReader = new InputStreamReader(is)) {
                savedSystems.load(inputStreamReader);

                String systemJson = savedSystems.getProperty("systems");
                if (!Strings.isNullOrEmpty(systemJson)) {
                    ObjectMapper mapper = new ObjectMapper();
                    CreateSystemDetails[] sysDetails = mapper.readValue(systemJson, CreateSystemDetails[].class);

                    for (CreateSystemDetails csd : sysDetails) {
                        try {
                            createSystem(csd.getUsername(), csd.getHost(), csd.getPassword());
                        } catch (final EdpException e) {
                            logger.warn("Failed to load system, " + csd.getHost(), e);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.warn("Failed to open saved-systems.properties", e);
        }

    }

    @Override
    public List<EdpSystem> findAllSystems() {
        return edpRepo.findAllSystems();
    }

    @Override
    public EdpSystem createSystem(String username, String host, String password) throws EdpException {

        DataProtectionProviderConfig providerConfig = new DataProtectionProviderConfig(Type.valueOf("Avamar"), true, host,
                username, password, "", "vsphere.local_vrealize.mcqueen.local", DeploymentConfigurationType.STANDALONE,
                "default", "memory leaks", "1.0.0", null);
        return edpRepo.createSystem(providerConfig);
    }

    @Override
    public boolean removeSystem(String id) {
        return edpRepo.removeSystem(id);
    }

    @Override
    public EdpSystem findSystemById(String id) {
        return edpRepo.findSystemById(id);
    }

    @Override
    public EdpPolicy findPolicyById(String policyId) {
        return edpRepo.findPolicyById(policyId);
    }
}
