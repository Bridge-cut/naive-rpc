package top.naive_projects.rpc.support.http;

import top.naive_projects.rpc.common.utils.Resources;
import top.naive_projects.rpc.support.Invocation;
import top.naive_projects.rpc.support.Result;
import top.naive_projects.rpc.support.RpcContext;
import top.naive_projects.rpc.support.RpcInvocation;
import top.naive_projects.rpc.support.protocol.AbstractInvoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/10 下午12:23
 */
public class HttpInvoker<T> extends AbstractInvoker<T> {

    public HttpInvoker(Class<T> invokerInterface) {
        super(invokerInterface);
    }

    @Override
    public Result invoke(Invocation invocation) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        RpcInvocation rpcInvocation = (RpcInvocation) invocation;
        String serviceClassName = rpcInvocation.getServiceClassName();
        String methodName = rpcInvocation.getMethodName();
        Class<?>[] parameterTypes = rpcInvocation.getParameterTypes();
        Object[] arguments = rpcInvocation.getArguments();

        Class<?> serviceClass = Resources.classForName(serviceClassName);
        Method method = serviceClass.getMethod(methodName, parameterTypes);

        Object returnValue = method.invoke(RpcContext.getServiceObject(serviceClass), arguments);

        return new Result(returnValue);
    }
}
