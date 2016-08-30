package com.emc.memoryleaks.beans;

import com.emc.edp4vcac.domain.EdpPolicy;

public class Policy {
	private final String id;
	private final String name;
	
	private final String content;
	private final String dataset;

	public Policy(String id, String content, String dataset, String name) {
		this.id = id;
		this.content = content;
		this.dataset = dataset;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getContent () {
		return content;
	}
	
	public String getDataset() {
		return dataset;
	}

	public String getName() {
		return name;
	}
	
    public static Policy convert(EdpPolicy edpPolicy) {
    	
    	return new Policy(edpPolicy.getId(), null, null, edpPolicy.getDisplayName());
    	
    }
}
