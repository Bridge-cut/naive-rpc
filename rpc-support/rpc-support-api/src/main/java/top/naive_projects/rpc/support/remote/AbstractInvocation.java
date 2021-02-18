package top.naive_projects.rpc.support.remote;

import top.naive_projects.rpc.support.Invocation;

import java.util.Properties;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/12 上午11:37
 */
public abstract class AbstractInvocation implements Invocation {

    protected transient Properties properties = new Properties();

    @Override
    public String getProps(String key) {
        return properties.getProperty(key);
    }

    @Override
    public void attachProps(String key, String value) {
        properties.setProperty(key, value);
    }
}
