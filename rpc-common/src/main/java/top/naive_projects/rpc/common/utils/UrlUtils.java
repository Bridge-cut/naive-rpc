package top.naive_projects.rpc.common.utils;

import top.naive_projects.rpc.common.URL;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/9 下午10:16
 */
public class UrlUtils {

    public static URL composeURL(String protocol, String host, Integer port,
                                 Class<?> serviceInterface, Class<?> serviceClass) {
        return new URL(protocol, host, port, serviceInterface.getName(), serviceClass.getName());
    }
}
