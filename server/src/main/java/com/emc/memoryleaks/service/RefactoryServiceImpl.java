package com.emc.memoryleaks.service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.emc.edp4vcac.domain.EdpNotificationHandler;
import com.emc.edp4vcac.domain.EdpPolicy;
import com.emc.edp4vcac.domain.EdpRepository;
import com.emc.edp4vcac.domain.EdpSystem;
import com.emc.edp4vcac.domain.model.EdpException;
import com.emc.edp4vcac.domain.spi.DataProtectionProviderConfig;
import com.emc.edp4vcac.domain.spi.DataProtectionProviderConfig.DeploymentConfigurationType;
import com.emc.edp4vcac.domain.spi.DataProtectionProviderConfig.Type;

@Service
public class RefactoryServiceImpl implements RepositoryService {

	private EdpNotificationHandler handler = new EdpNotificationHandler() {
		
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
	private EdpRepository edpRepo= new EdpRepository(handler, new HashMap(), Executors.newSingleThreadScheduledExecutor());
	
	@Override
	public List<EdpSystem> findAllSystems() {
		try {
			createSystem("MCUser","10.7.103.79","vmware7");
		} catch (EdpException e) {
			e.printStackTrace();
		}
		return edpRepo.findAllSystems();		
	
	}

	@Override
	public EdpSystem createSystem( String username, String host, String password) throws EdpException {
		
	DataProtectionProviderConfig providerConfig= new DataProtectionProviderConfig(Type.valueOf("Avamar"), true, host, username, password, "", "vsphere.local_vrealize.mcqueen.local", DeploymentConfigurationType.STANDALONE, "default", "memory leaks", "1.0.0", null);
		return edpRepo.createSystem(providerConfig);
	}

	@Override
	public boolean removeSystem(String id) {
		return false;
	}

	@Override
	public EdpSystem findSystemById(String id) {
		return null;
	}

	@Override
	public EdpPolicy findPolicyById(String policyId) {
		return null;
	}



}
