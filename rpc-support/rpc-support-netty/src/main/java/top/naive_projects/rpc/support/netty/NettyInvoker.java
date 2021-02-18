package top.naive_projects.rpc.support.netty;

import top.naive_projects.rpc.support.Invocation;
import top.naive_projects.rpc.support.Result;
import top.naive_projects.rpc.support.protocol.AbstractInvoker;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/10 下午12:23
 */
public class NettyInvoker<T> extends AbstractInvoker<T> {

    public NettyInvoker(Class<T> invokerInterface) {
        super(invokerInterface);
    }

    @Override
    public Result invoke(Invocation invocation) {
        return null;
    }
}
