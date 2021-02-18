package top.naive_projects.rpc.support.protocol;

import top.naive_projects.rpc.support.Exporter;
import top.naive_projects.rpc.support.Invoker;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/10 下午3:34
 */
public abstract class AbstractExporter<T> implements Exporter<T> {

    protected final Invoker<T> invoker;
    protected volatile boolean unExported = false;

    public AbstractExporter(Invoker<T> invoker) {
        if (invoker == null) throw new IllegalStateException("service invoker == null");
        if (invoker.getInterface() == null) throw new IllegalStateException("service type == null");

        this.invoker = invoker;
    }

    @Override
    public Invoker<T> getInvoker() {
        return invoker;
    }

    @Override
    public void unExport() {
        if (unExported) return;

        unExported = true;
        getInvoker().destroy();
    }
}
