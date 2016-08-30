package com.emc.memoryleaks.beans;

import com.emc.edp4vcac.domain.EdpPolicy;

public class Policy extends BaseBean {
    public Policy(String id, String name, String description, String details) {
        super(id, name, description, details);

    }
    
    public static Policy convert(EdpPolicy edpPolicy) {
    	
    	return new Policy(edpPolicy.getId(), edpPolicy.getDisplayName(), "", "");
    	
    }
}
