package top.naive_projects.rpc.support.netty;

import top.naive_projects.rpc.support.Exporter;
import top.naive_projects.rpc.support.Invoker;
import top.naive_projects.rpc.support.protocol.AbstractExporter;

import java.util.Map;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/10 下午3:55
 */
public class NettyExporter<T> extends AbstractExporter<T> {

    private final String key;
    private final Map<String, Exporter<?>> exporterMap;

    public NettyExporter(Invoker<T> invoker, String key, Map<String, Exporter<?>> exporterMap) {
        super(invoker);
        this.key = key;
        this.exporterMap = exporterMap;
    }

    @Override
    public void unExport() {
        super.unExport();
        exporterMap.remove(key);
    }
}
