package top.naive_projects.rpc.support;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午7:35
 */
public interface Exporter<T> {

    Invoker<T> getInvoker();

    void unExport();
}
