package top.naive_projects.rpc.support.netty;

import top.naive_projects.rpc.common.URL;
import top.naive_projects.rpc.support.Exporter;
import top.naive_projects.rpc.support.Invoker;
import top.naive_projects.rpc.support.RpcContext;
import top.naive_projects.rpc.support.protocol.AbstractProtocol;

import java.io.IOException;
import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/9 下午6:28
 */
public class NettyProtocol extends AbstractProtocol {

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker, URL url) {
        openServer(url);

        String key = url.url2string();
        NettyExporter<T> exporter = new NettyExporter<>(invoker, key, exporterMap);
        exporterMap.put(key, exporter);
        RpcContext.putExporter(key, exporter);

        return exporter;
    }

    private void openServer(URL url) {
        try {
            NettyServer.startServer(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Object> refer(Class<?> interfaceClass) {
        return null;
    }
}
