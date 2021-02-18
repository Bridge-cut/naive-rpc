package top.naive_projects.rpc.config;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/7 下午8:35
 */
public class ProtocolConfig {

    private final String name;
    private final String host;
    private final Integer port;
    private final Class<?> protocolClass;
    private final Class<?> invokerClass;

    public ProtocolConfig(String name, String host, Integer port,
                          Class<?> protocolClass, Class<?> invokerClass) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.protocolClass = protocolClass;
        this.invokerClass = invokerClass;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public Class<?> getProtocolClass() {
        return protocolClass;
    }

    public Class<?> getInvokerClass() {
        return invokerClass;
    }
}
