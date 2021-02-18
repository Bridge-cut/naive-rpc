package top.naive_projects.rpc.support.protocol;

import top.naive_projects.rpc.support.Exporter;
import top.naive_projects.rpc.support.Protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/10 下午3:45
 */
public abstract class AbstractProtocol implements Protocol {

    protected final Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<>();

}
