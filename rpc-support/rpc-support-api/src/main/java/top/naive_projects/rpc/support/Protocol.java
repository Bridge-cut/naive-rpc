package top.naive_projects.rpc.support;

import top.naive_projects.rpc.common.URL;

import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/9 下午6:23
 */
public interface Protocol {

    <T> Exporter<T> export(Invoker<T> invoker, URL url);

    List<Object> refer(Class<?> interfaceClass);
}
