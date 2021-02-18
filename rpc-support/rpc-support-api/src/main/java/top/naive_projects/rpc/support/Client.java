package top.naive_projects.rpc.support;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 下午9:44
 */
public interface Client {

    Object send(Invocation invocation);
}
