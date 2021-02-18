package top.naive_projects.rpc.common;

import java.io.Serializable;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/9 下午8:26
 */
public class URL implements Serializable {

    private String protocol;
    private String host;
    private Integer port;
    private String serviceInterfaceName;
    private String serviceClassName;

    public URL(String protocol, String host, Integer port,
               String serviceInterfaceName, String serviceClassName) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.serviceInterfaceName = serviceInterfaceName;
        this.serviceClassName = serviceClassName;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getServiceInterfaceName() {
        return serviceInterfaceName;
    }

    public String getServiceClassName() {
        return serviceClassName;
    }

    public String url2string() {
        return protocol + "://" + host + ":" + port + "/" + serviceClassName;
    }
}
