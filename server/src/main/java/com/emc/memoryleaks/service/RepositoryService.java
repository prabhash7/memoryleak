package com.emc.memoryleaks.service;

import com.emc.edp4vcac.domain.EdpPolicy;
import com.emc.edp4vcac.domain.EdpSystem;
import com.emc.edp4vcac.domain.model.EdpException;

import java.util.List;

/**
 */
public interface RepositoryService {

    EdpSystem createSystem(String username, String host, String password) throws EdpException;

    boolean removeSystem(String id);

    EdpSystem findSystemById(String id);

    EdpPolicy findPolicyById(String policyId);

    List<EdpSystem> findAllSystems();
}
