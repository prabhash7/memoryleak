package com.emc.memoryleaks.controllers;

import com.emc.edp4vcac.domain.EdpSystem;
import com.emc.edp4vcac.domain.model.EdpException;
import com.emc.memoryleaks.beans.CreateSystemDetails;
import com.emc.memoryleaks.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

		repoSvc.createSystem("MCUser","10.7.103.79","vmware7"); // Uncomment to run with hardcoded user details
		repoSvc.findAllSystems().forEach(system -> {
			String hostname = system.getHost();
			System.out.println("System -> "+ hostname);
		});
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
	public EdpSystem getProviderById(@PathVariable("id") String id) {
		logger.debug("get providers by Id");
		return repoSvc.findSystemById(id);
	}

}
