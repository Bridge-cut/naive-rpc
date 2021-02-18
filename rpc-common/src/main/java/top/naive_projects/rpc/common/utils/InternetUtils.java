package top.naive_projects.rpc.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 下午2:40
 */
public class InternetUtils {

    public static String getLocalHostAddress() {
        try {
            InetAddress candidateAddress = null;
            Enumeration<?> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
                Enumeration<?> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = (InetAddress) addresses.nextElement();
                    if (!address.isLoopbackAddress()) {
                        if (address.isSiteLocalAddress()) {
                            return address.getHostAddress();
                        } else candidateAddress = address;
                    }
                }
            }
            if (candidateAddress != null) return candidateAddress.getHostAddress();
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
