package top.naive_projects.rpc.support;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午7:31
 */
public class ServiceCacheKey extends CacheKey {

    private static final String TYPE = "service";

    public ServiceCacheKey() {
        super();
    }

    public ServiceCacheKey(Object[] objects) {
        super(objects);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
