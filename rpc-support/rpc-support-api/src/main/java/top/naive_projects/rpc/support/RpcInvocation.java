package top.naive_projects.rpc.support;

import top.naive_projects.rpc.support.remote.AbstractInvocation;

import java.io.Serializable;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 下午12:02
 */
public class RpcInvocation extends AbstractInvocation implements Serializable {

    private final String serviceClassName;
    private final String methodName;
    private final Class<?>[] parameterTypes;
    private final Object[] arguments;

    public RpcInvocation(String serviceClassName, String methodName,
                         Class<?>[] parameterTypes, Object[] arguments) {
        this.serviceClassName = serviceClassName;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }

    public String getServiceClassName() {
        return serviceClassName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public String getServiceKey() {
        return null;
    }
}
