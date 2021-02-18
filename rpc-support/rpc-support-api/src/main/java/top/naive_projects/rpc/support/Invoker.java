package top.naive_projects.rpc.support;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 上午8:45
 */
public interface Invoker<T> {

    Class<T> getInterface();

    Result invoke(Invocation invocation) throws Exception;

    void destroy();
}