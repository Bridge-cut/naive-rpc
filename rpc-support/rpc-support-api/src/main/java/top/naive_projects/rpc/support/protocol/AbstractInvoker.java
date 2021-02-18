package top.naive_projects.rpc.support.protocol;

import top.naive_projects.rpc.support.Invoker;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 上午11:32
 */
public abstract class AbstractInvoker<T> implements Invoker<T> {

    protected final Class<T> invokerInterface;
    private volatile boolean available = true;

    protected AbstractInvoker(Class<T> invokerInterface) {
        this.invokerInterface = invokerInterface;
    }

    @Override
    public Class<T> getInterface() {
        return invokerInterface;
    }

    @Override
    public void destroy() {
        available = false;
    }
}
