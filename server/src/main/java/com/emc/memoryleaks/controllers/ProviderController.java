package com.emc.memoryleaks.controllers;

import com.emc.edp4vcac.domain.EdpClient;
import com.emc.edp4vcac.domain.EdpPolicy;
import com.emc.edp4vcac.domain.EdpSystem;
import com.emc.edp4vcac.domain.model.EdpException;
import com.emc.memoryleaks.beans.*;
import com.emc.memoryleaks.service.RepositoryService;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.emc.memoryleaks.beans.Client.convert;
import static com.emc.memoryleaks.beans.Provider.convert;


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
                .map(Provider::convert)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/provider")
    public Provider createProvider(@RequestBody CreateSystemDetails createSystemDetails) throws EdpException {
        logger.debug("post /provider ");
        return convert(repoSvc.createSystem(createSystemDetails.getUsername(),
                createSystemDetails.getHost(), createSystemDetails.getPassword()));
    }

    @RequestMapping("/provider/{id}")
    public Provider getProviderById(@PathVariable("id") final String id) {
        logger.debug("get providers by Id");
        return convert(repoSvc.findSystemById(id));
    }

    @RequestMapping("/provider/{id}/client")
    public List<Client> getClientList(@PathVariable("id") final String id) {
        return repoSvc.findSystemById(id).findAllClients()
                .stream()
                .map(Client::convert)
                .collect(Collectors.toList());
    }

    @RequestMapping("provider/{providerId}/client/{clientId}")
    public Client getClientById(@PathVariable("providerId") final String providerId,
                                @PathVariable("clientId") final String clientId) {
        logger.debug("getClientById({}, {})", providerId, clientId);
        EdpSystem system = repoSvc.findSystemById(providerId);
        Preconditions.checkNotNull(system, "unable to find provider");
        return convert(system.findClientById(filterClientId(clientId)));
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
        EdpSystem system = repoSvc.findSystemById(providerId);
        Preconditions.checkNotNull(system, "unable to find provider");
        EdpClient client = system.findClientById(filterClientId(clientId));
        Preconditions.checkNotNull(client, "unable to find client");
        return client.getBackups(countInt)
                .stream()
                .map(Backup::convert)
                .collect(Collectors.toList());
    }

    @RequestMapping("provider/{providerId}/client/{clientId}/policy")
    public List<Policy> getClientPolicyList(@PathVariable("providerId") final String providerId,
                                            @PathVariable("clientId") final String clientId) {
        EdpSystem system = repoSvc.findSystemById(providerId);
        Preconditions.checkNotNull(system, "unable to find provider");
        EdpClient client = system.findClientById(filterClientId(clientId));
        Preconditions.checkNotNull(client, "unable to find client");
        return client.getPolicies()
                .stream()
                .map(Policy::convert)
                .collect(Collectors.toList());
    }

    @RequestMapping("provider/{providerId}/client/{clientId}/policy/{policyId}/adhock")
    public String runClientBackupForPolicy(@PathVariable("providerId") final String providerId,
                                           @PathVariable("clientId") final String clientId,
                                           @PathVariable("policyId") final String policyId) {
        EdpSystem system = repoSvc.findSystemById(providerId);
        Preconditions.checkNotNull(system, "unable to find provider");
        EdpClient client = system.findClientById(filterClientId(clientId));
        Preconditions.checkNotNull(client, "unable to find client");
        Optional<EdpPolicy> first = client.getPolicies()
                .stream()
                .filter(p -> p.getId().equals(policyId))
                .findFirst();

        if (first.isPresent()) {
            String callbackId = UUID.randomUUID().toString();
            first.get().adhocRunByClient(client, callbackId, "MemoryLeak App is awesome!");
            return "callbackId= " + callbackId;
        } else {
            return "Client not found";
        }
    }

    @RequestMapping("provider/{providerId}/client/{clientId}/policy/{policyId}")
    public Policy getClientPolicy(@PathVariable("providerId") final String providerId,
                                  @PathVariable("clientId") final String clientId,
                                  @PathVariable("policyId") final String policyId) {
        EdpSystem system = repoSvc.findSystemById(providerId);
        Preconditions.checkNotNull(system, "unable to find provider");
        EdpClient client = system.findClientById(filterClientId(clientId));
        Preconditions.checkNotNull(client, "unable to find client");

        return Policy.convert(client.getPolicies()
                .stream()
                .filter(p -> p.getId().equals(policyId))
                .findFirst().orElse(null));
    }

    @RequestMapping("provider/{providerId}/client/{clientId}/adhock")
    public String runClientBackup(@PathVariable("providerId") final String providerId,
                                  @PathVariable("clientId") final String clientId) {
        EdpSystem system = repoSvc.findSystemById(providerId);
        Preconditions.checkNotNull(system, "unable to find provider");
        EdpClient client = system.findClientById(filterClientId(clientId));
        Preconditions.checkNotNull(client, "unable to find client");

        Optional<EdpPolicy> first = client.getPolicies().stream().findFirst();
        if (first.isPresent()) {
            String callbackId = UUID.randomUUID().toString();
            first.get().adhocRunByClient(client, callbackId, "MemoryLeak App is awesome!");
            return "callbackId= " + callbackId;
        } else {
            return "Client not found";
        }
    }

    @RequestMapping("provider/{providerId}/policy")
    public List<Policy> getPolicyList(@PathVariable("providerId") final String providerId) {
        return repoSvc.findSystemById(providerId).findAllPolicies()
                .stream()
                .map(Policy::convert)
                .collect(Collectors.toList());
    }

    @RequestMapping("provider/{providerId}/policy/{policyId}")
    public Policy getPolicy(@PathVariable("providerId") final String providerId,
                            @PathVariable("policyId") final String policyId) {
        return Policy.convert(repoSvc.findSystemById(providerId).findPolicyById(policyId));
    }

    /**
     * Removed the provider id from the front of the clientId
     *
     * @param clientId
     * @return
     */
    private static String filterClientId(String clientId) {
        String result = clientId;
        if (clientId.contains("-")) {
            int lastIndex = clientId.lastIndexOf("-");
            result = clientId.substring(lastIndex + 1);
        }
        return result;
    }
}
