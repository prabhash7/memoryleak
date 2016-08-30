package com.emc.memoryleaks.beans;

import com.emc.edp4vcac.domain.EdpClient;

/**
 * Created by freemb2 on 8/30/16.
 */
public class Client extends BaseBean {

    public Client(String id, String name, String description) {
        super(id, name, description);
    }

    public static Client convert(final EdpClient c) {
        if (c != null) {
            return new Client(c.getId(), c.getDisplayName(), c.getDescription());
        } else {
            return null;
        }
    }
}
