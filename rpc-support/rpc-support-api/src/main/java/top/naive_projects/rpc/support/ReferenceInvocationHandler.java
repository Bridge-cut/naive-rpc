package top.naive_projects.rpc.support;

import top.naive_projects.rpc.common.utils.ServiceLoader;
import top.naive_projects.rpc.common.utils.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 下午4:16
 */
public class ReferenceInvocationHandler implements InvocationHandler {

    private final String protocol;
    private final String host;
    private final Integer port;
    private final String serviceClassName;

    public ReferenceInvocationHandler(String protocol, String host,
                                      Integer port, String serviceClassName) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.serviceClassName = serviceClassName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Invocation invocation = new RpcInvocation(serviceClassName, method.getName(),
                method.getParameterTypes(), args);
        invocation.attachProps("host", host);
        invocation.attachProps("port", StringUtils.object2string(port));
        ServiceLoader<Client> clients = ServiceLoader.load(Client.class, protocol);

        return clients.get(0).send(invocation);
    }
}
