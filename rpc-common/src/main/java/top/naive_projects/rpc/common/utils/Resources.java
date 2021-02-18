package top.naive_projects.rpc.common.utils;

import top.naive_projects.rpc.common.ClassLoaderWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午4:19
 */
public class Resources {

    private static final ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

    /**
     * 返回类路径上的资源作为 InputStream 对象
     *
     * @param resource 待寻找的资源
     * @return 资源对应的 InputStream 对象
     * @throws IOException 如果找不到或无法读取资源
     */
    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream(null, resource);
    }

    /**
     * 返回类路径上的资源作为 InputStream 对象
     *
     * @param loader   用于获取资源的类加载器
     * @param resource 待寻找的资源
     * @return 资源对应的 InputStream 对象
     * @throws IOException 如果找不到或无法读取资源
     */
    public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
        InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
        if (in == null) {
            throw new IOException("无法找到资源 " + resource);
        }
        return in;
    }

    /**
     * 加载类
     *
     * @param className - 要获取的类
     * @return 加载的类
     * @throws ClassNotFoundException 如果找不到或无法读取资源
     */
    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return classLoaderWrapper.classForName(className);
    }
}
