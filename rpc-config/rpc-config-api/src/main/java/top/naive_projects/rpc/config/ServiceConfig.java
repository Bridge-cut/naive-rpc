package top.naive_projects.rpc.config;

import top.naive_projects.rpc.common.URL;
import top.naive_projects.rpc.common.reflection.ObjectFactory;
import top.naive_projects.rpc.common.utils.Resources;
import top.naive_projects.rpc.common.utils.UrlUtils;
import top.naive_projects.rpc.registry.client.RegistryProtocol;
import top.naive_projects.rpc.support.Exporter;
import top.naive_projects.rpc.support.Invoker;
import top.naive_projects.rpc.support.Protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午5:03
 */
public class ServiceConfig {

    private transient volatile boolean exported;
    private transient volatile boolean unExported;
    private final String interfaceName;
    private final Class<?> interfaceClass;
    private final Class<?> targetType;
    private final RegistryProtocol protocol;
    private final List<RegistryConfig> registries = new ArrayList<>();
    private final List<ProtocolConfig> protocols = new ArrayList<>();
    private final List<Exporter<?>> exporters = new ArrayList<>();

    public ServiceConfig(String interfaceName, Class<?> targetType) throws ClassNotFoundException {
        this.interfaceName = interfaceName;
        this.interfaceClass = Resources.classForName(interfaceName);
        this.targetType = targetType;
        this.protocol = new RegistryProtocol(registries);
    }

    public List<RegistryConfig> getRegistries() {
        return registries;
    }

    public List<ProtocolConfig> getProtocols() {
        return protocols;
    }

    public synchronized void export() {
        if (unExported) {
            throw new RuntimeException(interfaceName + " 已设置不允许暴露该服务");
        }
        if (exported) return;

        exported = true;
        doExport();
    }
    private void doExport() {
        for (ProtocolConfig protocolConfig : protocols) {
            protocol.setProtocol((Protocol) ObjectFactory.createObject(protocolConfig.getProtocolClass()));
            doExportUrlsForProtocol(protocolConfig);
        }
    }
    private void doExportUrlsForProtocol(ProtocolConfig protocolConfig) {
        Invoker<?> invoker = (Invoker<?>) ObjectFactory.createObject(protocolConfig.getInvokerClass(),
                new Object[]{ interfaceClass });
        URL url = UrlUtils.composeURL(protocolConfig.getName(), protocolConfig.getHost(),
                protocolConfig.getPort(), interfaceClass, targetType);
        Exporter<?> exporter = protocol.export(invoker, url);
        exporters.add(exporter);
    }
}
