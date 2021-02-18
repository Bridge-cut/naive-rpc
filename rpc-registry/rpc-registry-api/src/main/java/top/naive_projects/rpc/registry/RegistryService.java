package top.naive_projects.rpc.registry;

import top.naive_projects.rpc.common.URL;
import top.naive_projects.rpc.config.RegistryConfig;

import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/9 下午8:26
 */
public interface RegistryService {

    void register(RegistryConfig registryConfig, URL url);

    void unregister(URL url);

    List<String> poll(RegistryConfig registryConfig, String interfaceName);
}
