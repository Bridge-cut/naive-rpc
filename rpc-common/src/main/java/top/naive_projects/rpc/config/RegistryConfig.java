package top.naive_projects.rpc.config;

import top.naive_projects.rpc.common.utils.StringUtils;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 上午11:47
 */
public class RegistryConfig {

    private String protocol;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private Class<?> registryClass;

    public RegistryConfig(String address) {
        this.protocol = StringUtils.substring(":", address);
        this.host = StringUtils.substring("://", ":", address);
        this.port = StringUtils.parseInteger(StringUtils.lastSubstring(":", "", address));
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Class<?> getRegistryClass() {
        return registryClass;
    }

    public void setRegistryClass(Class<?> registryClass) {
        this.registryClass = registryClass;
    }
}
