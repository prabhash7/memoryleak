package com.emc.memoryleaks.beans;

public class Policy {
	private final long id;
	private final String content;
	private final String dataset;

	public Policy(long id, String content, String dataset) {
		this.id = id;
		this.content = content;
		this.dataset = dataset;
	}
	
	public long getId() {
		return id;
	}
	
	public String getContent () {
		return content;
	}
	
	public String getDataset() {
		return dataset;
	}
}
