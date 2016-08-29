package com.emc.memoryleaks.service;

import com.emc.edp4vcac.domain.EdpPolicy;
import com.emc.edp4vcac.domain.EdpSystem;
import com.emc.edp4vcac.domain.model.EdpException;
import com.emc.edp4vcac.domain.spi.DataProtectionProviderConfig;

import java.util.List;

/**
 */
public interface RepositoryService {

    EdpSystem createSystem(DataProtectionProviderConfig dpProviderConfig) throws EdpException;

    boolean removeSystem(String id);

    EdpSystem findSystemById(String id);

    EdpPolicy findPolicyById(String policyId);

    List<EdpSystem> findAllSystems();

}
