package top.naive_projects.rpc.config.builders;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import top.naive_projects.rpc.common.utils.InternetUtils;
import top.naive_projects.rpc.common.utils.Resources;
import top.naive_projects.rpc.common.utils.StringUtils;
import top.naive_projects.rpc.config.*;
import top.naive_projects.rpc.support.ReferenceCacheKey;
import top.naive_projects.rpc.support.ServiceCacheKey;

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午4:57
 */
public class RpcConfigBuilder {

    private boolean parsed;
    private final Configuration configuration;
    private final InputStream inputStream;
    private final SAXReader saxReader;
    private final RpcConfigParser rpcConfigParser;

    public RpcConfigBuilder(InputStream inputStream) {
        this.inputStream = inputStream;

        this.configuration = new Configuration();
        this.saxReader = new SAXReader();
        this.rpcConfigParser = new RpcConfigParser();
    }

    public Configuration parse() {
        if (parsed) throw new RuntimeException("rpc 配置文件重复解析");

        parsed = true;
        parseConfiguration();
        return configuration;
    }

    /**
     * 按标签顺序逐个解析 rpc 配置文件
     */
    private void parseConfiguration() {
        System.err.println("开始按标签顺序逐个解析 rpc 配置文件");
        try {
            Document document = saxReader.read(inputStream);
            Element root = document.getRootElement();
            applicationElement(root.element("application"));
            rpcConfigParser.parseServiceConfig(root);
            rpcConfigParser.parseReferenceConfig(root);
        } catch (DocumentException | ClassNotFoundException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
        System.err.println("rpc 配置文件解析完毕");
    }

    private void applicationElement(Element appElement) {
        if (appElement == null) {
            throw new RuntimeException("rpc 配置文件必须声明 <application>");
        }

        configuration.getApplication().setName(appElement.attributeValue("name"));
    }

    private class RpcConfigParser {

        private void parseServiceConfig(Element root) throws ClassNotFoundException, UnknownHostException {
            List<ProtocolConfig> protocols = protocolElement(root.elements("protocol"));
            List<RegistryConfig> registries = registryElement(root.elements("registry"));

            Map<String, Class<?>> beanMap = new HashMap<>();
            for (Element beanElement: root.elements("bean")) {
                String id = beanElement.attributeValue("id");
                String className = beanElement.attributeValue("class");

                if (beanMap.containsKey(beanElement.attributeValue("id"))) {
                    throw new RuntimeException("rpc 配置文件中 <bean> 标签存在相同的 id 值");
                }
                if (StringUtils.isEmpty(className)) {
                    throw new RuntimeException("rpc 配置文件中 <bean> 标签必须存在 class 值");
                }

                try {
                    beanMap.put(id, Resources.classForName(className));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("rpc 配置文件中 <bean> 标签中 class 值对应的 .class 文件不存在");
                }
            }

            for (Element serviceElement : root.elements("service")) {
                String interfaceName = serviceElement.attributeValue("interface");
                String reference = serviceElement.attributeValue("ref");

                if (! beanMap.containsKey(reference)) {
                    throw new RuntimeException("rpc 配置文件不存在 id=" + reference + " 的 <bean> 标签");
                }

                ServiceConfig serviceConfig = new ServiceConfig(interfaceName, beanMap.get(reference));
                serviceConfig.getProtocols().addAll(protocols);
                serviceConfig.getRegistries().addAll(registries);
                ServiceCacheKey serviceCacheKey = new ServiceCacheKey(new Object[]{reference,
                        interfaceName, beanMap.get(reference)});
                configuration.addServiceConfigMap(serviceCacheKey, serviceConfig);
            }
        }

        private void parseReferenceConfig(Element root) throws ClassNotFoundException {
            List<RegistryConfig> registries = registryElement(root.elements("registry"));

            for (Element referenceElement : root.elements("reference")) {
                String id = referenceElement.attributeValue("id");
                String interfaceName = referenceElement.attributeValue("interface");

                if (StringUtils.isEmpty(id) || StringUtils.isEmpty(interfaceName)) {
                    throw new RuntimeException("rpc 配置文件 <reference> 标签必须存在 id 和 interface 属性");
                }

                ReferenceConfig referenceConfig = new ReferenceConfig(id, interfaceName);
                referenceConfig.getRegistries().addAll(registries);
                ReferenceCacheKey referenceCacheKey = new ReferenceCacheKey(new Object[]{id, interfaceName});
                configuration.addReferenceConfigMap(referenceCacheKey, referenceConfig);

            }
        }

        private List<RegistryConfig> registryElement(List<Element> registryElements) {
            List<RegistryConfig> registries = new ArrayList<>();
            if (registryElements != null) {
                for (Element registryElement : registryElements) {
                    String username = registryElement.attributeValue("username");
                    String password = registryElement.attributeValue("password");
                    String address = registryElement.attributeValue("address");

                    RegistryConfig registryConfig = new RegistryConfig(address);
                    registryConfig.setUsername(username);
                    registryConfig.setPassword(password);
                    registryConfig.setRegistryClass(configuration.getRegistryAlias(registryConfig.getProtocol()));

                    registries.add(registryConfig);
                }
            }

            return registries;
        }

        private List<ProtocolConfig> protocolElement(List<Element> protocolElements) {
            List<ProtocolConfig> protocols = new ArrayList<>();
            if (protocolElements != null) {
                for (Element protocolElement : protocolElements) {
                    String name = protocolElement.attributeValue("name");
                    String host = InternetUtils.getLocalHostAddress();
                    Integer port = StringUtils.parseInteger(protocolElement.attributeValue("port"));

                    protocols.add(new ProtocolConfig(name, host, port,
                            configuration.getProtocolAlias(name), configuration.getInvokerAlias(name)));
                }
            }

            return protocols;
        }
    }
}
