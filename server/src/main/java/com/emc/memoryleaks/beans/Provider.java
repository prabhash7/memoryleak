package com.emc.memoryleaks.beans;

import com.emc.edp4vcac.domain.EdpSystem;

/**
 * Created by freemb2 on 8/29/16.
 */
public class Provider extends BaseBean {

    public Provider(String id, String name, String description, String details) {
        super(id, name, description, details);
    }

    public static Provider convert(final EdpSystem edpSystem) {
        if (edpSystem != null) {
            return new Provider(edpSystem.getId(), edpSystem.getDisplayName(), edpSystem.getDescription(), "");
        } else {
            return null;
        }
    }
}
