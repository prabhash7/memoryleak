package com.emc.memoryleaks.beans;

import java.util.ArrayList;

public class Policies {
	private final long id;
	private final ArrayList<Policy> policies;

	public Policies(long id, ArrayList<Policy> policies) {
		this.id = id;
		this.policies = policies;

	}
	
	public long getId() {
		return id;
	}

	public ArrayList<Policy> getPolicies() {
		return policies;
	}

}
