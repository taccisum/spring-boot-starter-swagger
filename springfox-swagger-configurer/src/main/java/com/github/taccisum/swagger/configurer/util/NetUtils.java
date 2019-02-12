package com.github.taccisum.swagger.configurer.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/12
 */
public class NetUtils {
    private static String getHostNameForLinux() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage(); // host = "hostname: hostname"
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "Unknown Host";
        }
    }

    public static String getHostName() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLinux();
        }
    }
}
