package top.naive_projects.rpc.registry.client;

import top.naive_projects.rpc.common.URL;
import top.naive_projects.rpc.common.reflection.ObjectFactory;
import top.naive_projects.rpc.common.utils.InternetUtils;
import top.naive_projects.rpc.common.utils.StringUtils;
import top.naive_projects.rpc.config.RegistryConfig;
import top.naive_projects.rpc.registry.RegistryService;
import top.naive_projects.rpc.support.Exporter;
import top.naive_projects.rpc.support.Invoker;
import top.naive_projects.rpc.support.Protocol;
import top.naive_projects.rpc.support.ReferenceInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/9 下午6:46
 */
public class RegistryProtocol implements Protocol {

    private Protocol protocol;
    private final List<RegistryConfig> registries;

    public RegistryProtocol(List<RegistryConfig> registries) {
        this.registries = registries;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public <T> Exporter<T> export(final Invoker<T> originInvoker, URL url) {
        Exporter<T> exporter = protocol.export(originInvoker, url);
        for (RegistryConfig registry : registries) {
            ((RegistryService) ObjectFactory.createObject(registry.getRegistryClass()))
                    .register(registry, url);
        }

        return exporter;
    }

    @Override
    public List<Object> refer(Class<?> interfaceClass) {
        Set<String> serviceUrls = new HashSet<>();
        List<Object> proxyInstances = new ArrayList<>();
        for (RegistryConfig registry : registries) {
            List<String> urls = ((RegistryService) ObjectFactory.createObject(registry.getRegistryClass()))
                    .poll(registry, interfaceClass.getName());
            serviceUrls.addAll(filterUselessUrls(urls));
        }

        for (String url : serviceUrls) {
            String protocol = StringUtils.substring(":", url);
            String host = StringUtils.substring("://", ":", url);
            Integer port = StringUtils.parseInteger(StringUtils.lastSubstring(":", "/", url));
            String serviceClassName = StringUtils.lastSubstring("/", "", url);

            ReferenceInvocationHandler invocationHandler =
                    new ReferenceInvocationHandler(protocol, host, port, serviceClassName);
            proxyInstances.add(Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                    new Class[]{ interfaceClass }, invocationHandler));
        }

        return proxyInstances;
    }

    private List<String> filterUselessUrls(List<String> urls) {
        if (urls == null || urls.size() == 0) return urls;

        String host = InternetUtils.getLocalHostAddress();

        return urls.stream()
                .filter(string -> !string.contains("injvm") || (string.contains(host)))
                .collect(Collectors.toList());
    }
}
