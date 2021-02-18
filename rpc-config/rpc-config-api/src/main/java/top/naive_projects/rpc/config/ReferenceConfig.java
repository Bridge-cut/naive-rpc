package top.naive_projects.rpc.config;

import top.naive_projects.rpc.common.utils.Resources;
import top.naive_projects.rpc.registry.client.RegistryProtocol;
import top.naive_projects.rpc.support.RpcContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午5:04
 */
public class ReferenceConfig {

    private transient volatile boolean destroyed;
    private final String id;
    private final String interfaceName;
    private final Class<?> interfaceClass;
    private final RegistryProtocol protocol;
    private final List<RegistryConfig> registries = new ArrayList<>();
    private final List<Object> proxyInstances = new ArrayList<>();

    public ReferenceConfig(String id, String interfaceName) throws ClassNotFoundException {
        this.id = id;
        this.interfaceName = interfaceName;
        this.interfaceClass = Resources.classForName(interfaceName);
        this.protocol = new RegistryProtocol(registries);
    }

    public List<RegistryConfig> getRegistries() {
        return registries;
    }

    public synchronized void get() {
        if (destroyed) {
            throw new IllegalStateException(interfaceName + " 对应的 ReferenceConfig 已经销毁");
        }

        init();
    }
    private void init() {
        List<Object> proxies = protocol.refer(interfaceClass);
        proxyInstances.addAll(proxies);
        proxyInstances.forEach(proxyInstance -> RpcContext.putReference(id, proxyInstance));
    }
}
