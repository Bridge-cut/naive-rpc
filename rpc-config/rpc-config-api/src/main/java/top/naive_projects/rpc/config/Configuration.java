package top.naive_projects.rpc.config;

import top.naive_projects.rpc.registry.zookeeper.ZookeeperRegistry;
import top.naive_projects.rpc.support.ReferenceCacheKey;
import top.naive_projects.rpc.support.RpcContext;
import top.naive_projects.rpc.support.ServiceCacheKey;
import top.naive_projects.rpc.support.http.HttpClient;
import top.naive_projects.rpc.support.http.HttpInvoker;
import top.naive_projects.rpc.support.http.HttpProtocol;
import top.naive_projects.rpc.support.injvm.InjvmInvoker;
import top.naive_projects.rpc.support.injvm.InjvmProtocol;
import top.naive_projects.rpc.support.netty.NettyInvoker;
import top.naive_projects.rpc.support.netty.NettyProtocol;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午7:15
 */
public class Configuration {

    private final ApplicationConfig application;
    private final Map<ServiceCacheKey, ServiceConfig> serviceConfigMap;
    private final Map<ReferenceCacheKey, ReferenceConfig> referenceConfigMap;
    private final Map<String, Class<?>> registryAliases = new HashMap<>();
    private final Map<String, Class<?>> clientAliases = new HashMap<>();
    private final Map<String, Class<?>> protocolAliases = new HashMap<>();
    private final Map<String, Class<?>> invokerAliases = new HashMap<>();

    public Configuration() {
        this.application = new ApplicationConfig();
        this.serviceConfigMap = new ConcurrentHashMap<>();
        this.referenceConfigMap = new ConcurrentHashMap<>();

        this.registryAliases.put("zookeeper", ZookeeperRegistry.class);

        this.clientAliases.put("http", HttpClient.class);

        this.protocolAliases.put("netty", NettyProtocol.class);
        this.protocolAliases.put("http", HttpProtocol.class);
        this.protocolAliases.put("injvm", InjvmProtocol.class);

        this.invokerAliases.put("netty", NettyInvoker.class);
        this.invokerAliases.put("http", HttpInvoker.class);
        this.invokerAliases.put("injvm", InjvmInvoker.class);
    }

    public ApplicationConfig getApplication() {
        return application;
    }

    public void addServiceConfigMap(ServiceCacheKey serviceCacheKey, ServiceConfig serviceConfig) {
        serviceConfigMap.put(serviceCacheKey, serviceConfig);
    }

    public void addReferenceConfigMap(ReferenceCacheKey referenceCacheKey, ReferenceConfig referenceConfig) {
        referenceConfigMap.put(referenceCacheKey, referenceConfig);
    }

    public Class<?> getRegistryAlias(String alias) {
        return registryAliases.get(alias);
    }

    public Class<?> getProtocolAlias(String alias) {
        return protocolAliases.get(alias);
    }

    public Class<?> getInvokerAlias(String alias) {
        return invokerAliases.get(alias);
    }

    public Object getBean(String name) {
        return RpcContext.getReference(name);
    }

    public void start() {
        serviceConfigMap.values().forEach(ServiceConfig::export);
        referenceConfigMap.values().forEach(ReferenceConfig::get);
    }
}
