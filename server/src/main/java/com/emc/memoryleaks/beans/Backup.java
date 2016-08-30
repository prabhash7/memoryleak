package com.emc.memoryleaks.beans;

import com.emc.edp4vcac.domain.EdpBackup;

/**
 * Created by freemb2 on 8/29/16.
 */
public class Backup extends BaseBean {
    public Backup(String id, String name, String description) {
        super(id, name, description);
    }

    public static Backup convert(final EdpBackup b) {
        if (b != null) {
            return new Backup(b.getId(), b.getDisplayName(), b.getDescription());
        } else {
            return null;
        }
    }
}
