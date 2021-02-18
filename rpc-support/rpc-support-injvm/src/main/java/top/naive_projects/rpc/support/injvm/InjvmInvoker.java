package top.naive_projects.rpc.support.injvm;

import top.naive_projects.rpc.support.Invocation;
import top.naive_projects.rpc.support.Result;
import top.naive_projects.rpc.support.protocol.AbstractInvoker;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/10 下午12:22
 */
public class InjvmInvoker<T> extends AbstractInvoker<T> {

    public InjvmInvoker(Class<T> invokerInterface) {
        super(invokerInterface);
    }

    @Override
    public Result invoke(Invocation invocation) {

        return null;
    }
}