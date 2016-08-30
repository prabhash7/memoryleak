package com.emc.memoryleaks.beans;

import com.emc.edp4vcac.domain.EdpClient;

/**
 * Created by freemb2 on 8/30/16.
 */
public class Client extends BaseBean {

    public Client(String id, String name, String description, String details) {
        super(id, name, description, details);
    }

    public static Client convert(final EdpClient c) {
        if (c != null) {
            final String details = "host=" + c.getInfo().getHost() +
                    ", domain=" + c.getInfo().getDescription() +
                    ", esxiHost=" + c.getInfo().getHost() +
                    ", vmxPath=" + c.getInfo().getVmxPath() +
                    ", vCenter=" + c.getInfo().getVcenter().getHostname() +
                    ", dataCenter=" + c.getInfo().getDataCenter();

            return new Client(c.getId(), c.getDisplayName(), c.getInfo().getDescription(), details);
        } else {
            return null;
        }
    }
}
