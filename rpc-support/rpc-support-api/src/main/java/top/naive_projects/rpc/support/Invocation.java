package top.naive_projects.rpc.support;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 上午8:46
 */
public interface Invocation {

    String getServiceKey();

    String getProps(String key);

    void attachProps(String key, String value);
}
