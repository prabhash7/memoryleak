package com.emc.memoryleaks.controllers;

import com.emc.edp4vcac.domain.EdpBackup;
import com.emc.edp4vcac.domain.EdpClient;
import com.emc.edp4vcac.domain.EdpSystem;
import com.emc.edp4vcac.domain.model.EdpException;
import com.emc.memoryleaks.beans.Backup;
import com.emc.memoryleaks.beans.Client;
import com.emc.memoryleaks.beans.CreateSystemDetails;
import com.emc.memoryleaks.beans.Provider;
import com.emc.memoryleaks.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class ProviderController {

    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    @Autowired
    private RepositoryService repoSvc;

    public ProviderController() {
        logger.debug("Starting ProviderController");
    }

    @RequestMapping("/provider")
    public List<Provider> getProviderList() throws EdpException {
        logger.debug("get /providers");
        return repoSvc.findAllSystems()
                .stream()
                .map(ProviderController::mapEdpSystem)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/provider")
    public Provider createProvider(@RequestBody CreateSystemDetails createSystemDetails) throws EdpException {
        logger.debug("post /provider ");
        return mapEdpSystem(repoSvc.createSystem(createSystemDetails.getUsername(),
                createSystemDetails.getHost(), createSystemDetails.getPassword()));
    }

    @RequestMapping("/provider/{id}")
    public Provider getProviderById(@PathVariable("id") final String id) {
        logger.debug("get providers by Id");
        return mapEdpSystem(repoSvc.findSystemById(id));
    }

    @RequestMapping("/provider/{id}/client")
    public List<Client> getClientList(@PathVariable("id") final String id) {
        return repoSvc.findSystemById(id).findAllClients()
                .stream()
                .map(ProviderController::mapEdpClient)
                .collect(Collectors.toList());
    }

    @RequestMapping("provider/{providerId}/client/{clientId}")
    public Client getClientById(@PathVariable("providerId") final String providerId,
                                @PathVariable("clientId") final String clientId) {
        logger.debug("getClientById({}, {})", providerId, clientId);
        return mapEdpClient(repoSvc.findSystemById(providerId).findClientById(clientId));
    }

    @RequestMapping("provider/{providerId}/client/{clientId}/backup")
    public List<Backup> getBackupList(@PathVariable("providerId") final String providerId,
                                      @PathVariable("clientId") final String clientId,
                                      @RequestParam(value = "count", defaultValue = "10") String count) {
        int countInt = 10;
        try {
            countInt = Integer.parseInt(count);
        } catch (final NumberFormatException e) {
            logger.warn("Invalid backup count given, {}, defaulting to 10", count);
        }
        return repoSvc.findSystemById(providerId).findClientById(clientId).getBackups(countInt)
                .stream()
                .map(ProviderController::mapEdpBackup)
                .collect(Collectors.toList());
    }

    private static Provider mapEdpSystem(final EdpSystem edpSystem) {
        if (edpSystem != null) {
            return new Provider(edpSystem.getId(), edpSystem.getDisplayName(), edpSystem.getDescription());
        } else {
            return null;
        }
    }

    private static Client mapEdpClient(final EdpClient c) {
        if (c != null) {
            return new Client(c.getId(), c.getDisplayName(), c.getDescription());
        } else {
            return null;
        }
    }

    private static Backup mapEdpBackup(final EdpBackup b) {
        if (b != null) {
            return new Backup(b.getId(), b.getDisplayName(), b.getDescription());
        } else {
            return null;
        }
    }
}
