package top.naive_projects.rpc.support;

import top.naive_projects.rpc.common.reflection.ObjectFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/7 下午7:38
 */
public class RpcContext {

    private static final Random RANDOM = new Random(37);
    // <key=url.url2string(), value=exporter>
    private static final Map<String, Exporter<?>> EXPORTER_MAP = new ConcurrentHashMap<>();
    // <key=<bean> 标签对应的 id 值, value=bean 对应的代理对象的集合>
    private static final Map<String, List<Object>> REFERENCE_MAP = new ConcurrentHashMap<>();
    // <key=服务提供者对应的 class, value=服务提供者对应的 object>
    private static final Map<Class<?>, Object> SERVICES = new ConcurrentHashMap<>();

    public static void putExporter(String key, Exporter<?> exporter) {
        EXPORTER_MAP.put(key, exporter);
    }

    public static Exporter<?> getExporter(String serviceKey) {
        return EXPORTER_MAP.get(serviceKey);
    }

    public static void putReference(String name, Object proxyInstance) {
        if (!REFERENCE_MAP.containsKey(name)) REFERENCE_MAP.put(name, new ArrayList<>());
        REFERENCE_MAP.get(name).add(proxyInstance);
    }

    public static Object getReference(String name) {
        return REFERENCE_MAP.get(name).get(RANDOM.nextInt(REFERENCE_MAP.get(name).size()));
    }

    public static Object getServiceObject(Class<?> serviceClass) {
        if (!SERVICES.containsKey(serviceClass)) {
            SERVICES.put(serviceClass, ObjectFactory.createObject(serviceClass));
        }

        return SERVICES.get(serviceClass);
    }

    public static RpcContext getContext() {
        return null;
    }
}
