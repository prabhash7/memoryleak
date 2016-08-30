package com.emc.memoryleaks.controllers;

import com.emc.edp4vcac.domain.EdpBackup;
import com.emc.edp4vcac.domain.EdpClient;
import com.emc.edp4vcac.domain.EdpSystem;
import com.emc.edp4vcac.domain.model.EdpException;
import com.emc.memoryleaks.beans.CreateSystemDetails;
import com.emc.memoryleaks.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<EdpSystem> getProviderList() throws EdpException {
        logger.debug("get /providers");
        //repoSvc.createSystem("MCUser","10.7.103.79","vmware7"); // Uncomment to run with hardcoded user details
        return repoSvc.findAllSystems();
    }

    @RequestMapping(value = "/provider", method = RequestMethod.POST)
    public EdpSystem createProvider(@RequestBody CreateSystemDetails createSystemDetails) {

        logger.debug("post /provider ");
        EdpSystem edpSystem = null;
        try {
            edpSystem = repoSvc.createSystem(createSystemDetails.getUsername(), createSystemDetails.getHost(),
                    createSystemDetails.getPassword());
            //repoSvc.createSystem("MCUser","10.7.103.79","vmware7");
        } catch (EdpException e) {
            e.printStackTrace();
        }
        return edpSystem;
    }

    @RequestMapping("/provider/{id}")
    public EdpSystem getProviderById(@PathVariable("id") final String id) {
        logger.debug("get providers by Id");
        return repoSvc.findSystemById(id);
    }

    @RequestMapping("/provider/{id}/client")
    public List<EdpClient> getClientList(@PathVariable("id") final String id) {
        return repoSvc.findSystemById(id).findAllClients();
    }

    @RequestMapping("provider/{providerId}/client/{clientId}")
    public EdpClient getClientById(@PathVariable("providerId") final String providerId,
                                   @PathVariable("clientId") final String clientId) {
        return repoSvc.findSystemById(providerId).findClientById(clientId);
    }

    @RequestMapping("provider/{providerId}/client/{clientId}/backup")
    public List<EdpBackup> getBackupList(@PathVariable("providerId") final String providerId,
                                         @PathVariable("clientId") final String clientId,
                                         @RequestParam(value="count", defaultValue="10") String count) {
        int countInt = 10;
        try {
            countInt = Integer.parseInt(count);
        } catch (final NumberFormatException e) {
            logger.warn("Invalid backup count given, {}, defaulting to 10", count);
        }
        return repoSvc.findSystemById(providerId).findClientById(clientId).getBackups(countInt);
    }
}
