package com.emc.memoryleaks.service;

import com.emc.edp4vcac.domain.EdpPolicy;
import com.emc.edp4vcac.domain.EdpSystem;
import com.emc.edp4vcac.domain.model.EdpException;
import com.emc.edp4vcac.domain.spi.DataProtectionProviderConfig;

import java.util.List;

/**
 */
public interface RepositoryService {

	public EdpSystem createSystem( String username, String host, String password) throws EdpException;

	public boolean removeSystem(String id);

	public EdpSystem findSystemById(String id);

	public  EdpPolicy findPolicyById(String policyId);

	public List<EdpSystem> findAllSystems();

}
