package top.naive_projects.rpc.support.injvm;

import top.naive_projects.rpc.common.URL;
import top.naive_projects.rpc.support.Exporter;
import top.naive_projects.rpc.support.Invoker;
import top.naive_projects.rpc.support.protocol.AbstractProtocol;

import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/9 下午6:25
 */
public class InjvmProtocol extends AbstractProtocol {

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker, URL url) {
        return new InjvmExporter<>(invoker, url.url2string(), exporterMap);
    }

    @Override
    public List<Object> refer(Class<?> interfaceClass) {
        return null;
    }
}
